package com.elinxer.cloud.library.server.assist.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.dbn.cloud.platform.cache.redis.CacheService;
import com.elinxer.cloud.library.server.assist.domain.DeviceStatus;
import com.elinxer.cloud.library.server.assist.domain.DeviceStatusSummary;
import com.elinxer.cloud.library.server.assist.domain.entity.Device;
import com.elinxer.cloud.library.server.assist.domain.enums.DeviceStatusEnum;
import com.elinxer.cloud.library.server.assist.service.IDeviceService;
import com.elinxer.cloud.library.server.assist.service.IDeviceStatusService;
import com.elinxer.cloud.library.server.assist.service.IMqttService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class DeviceStatusServiceImpl implements IDeviceStatusService {

    private static final String DEVICE_STATUS = "vns:device:status:";

    private static final String DEVICE_STATUS_SUMMARY = "vns:device:summary:status";

    private static final String DEVICE_STATUS_SUMMARY_LIST = "vns:device:summary:list";

    private static final String DEVICE_STATUS_SUMMARY_LIVE = "vns:device:summary:live";

    @Resource
    CacheService cacheService;

    @Resource
    IDeviceService iDeviceService;

    @Resource
    IMqttService iMqttService;


    public String getDeviceStatusKey(String deviceNo) {
        return DEVICE_STATUS + deviceNo;
    }

    public DeviceStatusSummary getDeviceStatusSummary() {
        List<Object> stateSummary = cacheService.getSrt().opsForHash().values(DEVICE_STATUS_SUMMARY_LIST);
        int online = 0;
        for (Object state : stateSummary) {
            if ("1".equals(String.valueOf(state))) {
                online += 1;
            }
        }
        return DeviceStatusSummary.builder().online(online).build();
    }

    @Override
    public Map<String, Integer> matchDeviceStatus(Set<String> devices) {
        Map<Object, Object> stateSummary = cacheService.getSrt().opsForHash().entries(DEVICE_STATUS_SUMMARY_LIST);
        Map<String, Integer> stringIntegerMap = new HashMap<>();
        if (devices == null) {
            return stringIntegerMap;
        }
        for (Map.Entry<Object, Object> entry : stateSummary.entrySet()) {
            if (devices.contains(String.valueOf(entry.getKey()))) {
                stringIntegerMap.put(String.valueOf(entry.getKey()), Integer.valueOf(String.valueOf(entry.getValue())));
            }
        }
        return stringIntegerMap;
    }


    @Override
    public Integer getDeviceOnlineNum(Set<String> devices) {
        Map<Object, Object> stateSummary = cacheService.getSrt().opsForHash().entries(DEVICE_STATUS_SUMMARY_LIST);
        Integer number = 0;
        if (devices == null) {
            return number;
        }
        for (Map.Entry<Object, Object> entry : stateSummary.entrySet()) {
            if (devices.contains(String.valueOf(entry.getKey()))
                    && Integer.parseInt(String.valueOf(entry.getValue())) == 1) {
                number++;
            }
        }
        return number;
    }

    @Override
    public void refreshDeviceLive(String deviceNO) {
        // 刷新缓存
        cacheService.getSrt().opsForHash().put(DEVICE_STATUS_SUMMARY_LIVE, deviceNO, String.valueOf(System.currentTimeMillis()));
        // 刷新数据库
        Device device = new Device();
        device.setSerialNo(deviceNO);
        device.setState(1);
        device.setLiveAt(DateUtil.now());
        iDeviceService.updateDeviceStatus(device);
    }

    @Override
    public Map<Object, Object> getDeviceLiveAllCache() {
        // 刷新缓存
        return cacheService.getSrt().opsForHash().entries(DEVICE_STATUS_SUMMARY_LIVE);
    }

    @Override
    public void setStatus(DeviceStatus deviceStatus) {
        cacheService.setCache(getDeviceStatusKey(deviceStatus.getDeviceId()), JSON.toJSONString(deviceStatus), 86400 * 7);

        // 刷新设备活跃时间
        refreshDeviceLive(deviceStatus.getDeviceId());

        String summaryResult = cacheService.getCache(DEVICE_STATUS_SUMMARY);
        DeviceStatusSummary statusSummary = DeviceStatusSummary.builder().total(0).online(0).build();
        if (summaryResult != null && !"".equals(summaryResult)) {
            statusSummary = JSON.parseObject(summaryResult, DeviceStatusSummary.class);
        }

        cacheService.getSrt().opsForHash().put(DEVICE_STATUS_SUMMARY_LIST, deviceStatus.getDeviceId(), String.valueOf(deviceStatus.getStatus().getValue()));

        switch (deviceStatus.getStatus()) {
            case ONLINE:
                statusSummary.setOnline(statusSummary.getOnline() + 1);

                // TODO 更新数据库
                Device device = new Device();
                device.setSerialNo(deviceStatus.getDeviceId());
                device.setState(1);
                device.setStateAt(DateUtil.now());
                device.setLiveAt(DateUtil.now());
                iDeviceService.updateDeviceStatus(device);

                if (deviceStatus.getClientId() != null) {
                    iMqttService.subscribe(deviceStatus.getClientId());
                }
                break;
            case OFFLINE:

                statusSummary.setOnline(statusSummary.getOnline() - 1);
                if (statusSummary.getOnline() <= 0) {
                    statusSummary.setOnline(0);
                }

                // TODO 更新数据库
                Device device2 = new Device();
                device2.setSerialNo(deviceStatus.getDeviceId());
                device2.setState(0);
                device2.setStateAt(DateUtil.now());
                device2.setLiveAt(DateUtil.now());
                iDeviceService.updateDeviceStatus(device2);

                if (deviceStatus.getClientId() != null) {
                    iMqttService.unsubscribe(deviceStatus.getClientId());
                }
                break;
            default:
                break;
        }

        cacheService.setCache(DEVICE_STATUS_SUMMARY, JSON.toJSONString(statusSummary));
    }

    @Override
    public DeviceStatus getStatus(String deviceNo) {
        String result = cacheService.getCache(getDeviceStatusKey(deviceNo));
        if (result == null) {
            return DeviceStatus.builder().timestamp(-1L).status(DeviceStatusEnum.OFFLINE).build();
        }
        return JSON.parseObject(result, DeviceStatus.class);
    }

    @Override
    public DeviceStatusEnum getStatusEnum(String deviceNo) {
        return getStatus(deviceNo).getStatus();
    }


}
