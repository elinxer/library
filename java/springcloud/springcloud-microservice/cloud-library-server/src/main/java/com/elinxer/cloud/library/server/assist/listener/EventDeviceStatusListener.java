package com.elinxer.cloud.library.server.assist.listener;


import com.dbn.cloud.platform.mqtt.config.MqttClientProperties;
import com.elinxer.cloud.library.server.assist.domain.DeviceStatus;
import com.elinxer.cloud.library.server.assist.domain.entity.Device;
import com.elinxer.cloud.library.server.assist.domain.enums.DeviceStatusEnum;
import com.elinxer.cloud.library.server.assist.domain.event.DeviceStatusEventParam;
import com.elinxer.cloud.library.server.assist.events.DeviceStatusEvent;
import com.elinxer.cloud.library.server.assist.service.IDeviceService;
import com.elinxer.cloud.library.server.assist.service.IDeviceStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**
 * @author elinx
 */
@Slf4j
@Component
@EnableConfigurationProperties(value = MqttClientProperties.class)
public class EventDeviceStatusListener {

    /**
     * 时间周期
     */
    private static final Long TIME_PERIOD = 5000L;

    private Map<String, Long> deviceLiveMap = new HashMap<>();

    @Resource
    IDeviceStatusService iDeviceStatusService;

    @Resource
    IDeviceService iDeviceService;


    /**
     * 设备故障处理事件
     *
     * @param event MachineErrorEvent
     */
    @EventListener
    public void status(DeviceStatusEvent event) {
        DeviceStatusEventParam param = event.getEventParam();
        if (param != null && param.getDeviceId() != null) {
            eventCenter(param);
        }
    }

    public void eventCenter(DeviceStatusEventParam eventParam) {
        try {

            if (eventParam.getDeviceId() == null) {
                return;
            }

            Long timestamp = System.currentTimeMillis();
            deviceLiveMap.put(eventParam.getDeviceId(), timestamp);

            // 刷新设备活跃时间
            iDeviceStatusService.refreshDeviceLive(eventParam.getDeviceId());

        } catch (Exception e) {
            log.error("===machineCenter.error={}", (Object) e.getStackTrace());
        }
    }

    public void online(DeviceStatus deviceStatus) {
        //iDeviceStatusService.setStatus(deviceStatus);
    }

    public Map<String, Long> getDeviceLiveMap() {
        return deviceLiveMap;
    }


    @Scheduled(fixedDelay = 5000)
    public void offline() {
        try {
            Map<Object, Object> deviceLiveMap = iDeviceStatusService.getDeviceLiveAllCache();
            if (deviceLiveMap == null) {
                return;
            }
            for (Map.Entry<Object, Object> entry : deviceLiveMap.entrySet()) {
                String deviceId = String.valueOf(entry.getKey());
                long liveTime = Long.parseLong(String.valueOf(entry.getValue()));
                if ((System.currentTimeMillis() - liveTime) >= TIME_PERIOD) {
                    Device device = iDeviceService.getDeviceByNo(deviceId);
                    if (device == null) {
                        continue;
                    }
                    DeviceStatus deviceStatus = DeviceStatus.builder()
                            .clientId(device.getClientId())
                            .deviceId(deviceId)
                            .timestamp(liveTime)
                            .status(DeviceStatusEnum.OFFLINE).build();
                    //iDeviceStatusService.setStatus(deviceStatus);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
