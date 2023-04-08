package com.elinxer.cloud.library.server.assist.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dbn.cloud.platform.cache.redis.CacheService;
import com.dbn.cloud.platform.database.mybatis.impl.BaseServiceImpl;
import com.elinxer.cloud.library.server.assist.domain.dao.DeviceDao;
import com.elinxer.cloud.library.server.assist.domain.dao.UserDeviceDao;
import com.elinxer.cloud.library.server.assist.domain.entity.Device;
import com.elinxer.cloud.library.server.assist.domain.vo.device.DeviceUserVo;
import com.elinxer.cloud.library.server.assist.service.IDeviceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DeviceServiceImpl extends BaseServiceImpl<DeviceDao, Device> implements IDeviceService {

    @Resource
    CacheService cacheService;

    @Resource
    UserDeviceDao userDeviceDao;

    @Override
    public boolean updateDeviceStatus(Device device) {
        return this.update(device, new LambdaQueryWrapper<Device>()
                .eq(Device::getSerialNo, device.getSerialNo()).last("limit 1"));
    }

    @Override
    public Device getDeviceByClientId(String clientId) {
        Device device = null;
        String result = cacheService.getCache("vns:client:device:" + clientId);
        if (result != null && !"".equals(result)) {
            device = JSON.parseObject(result, Device.class);
        } else {
            device = (Device) this.getOne(new LambdaQueryWrapper<Device>().eq(Device::getClientId, clientId).last("limit 1"));
            cacheService.setCache("vns:client:device:" + clientId, JSON.toJSONString(device), 60);
        }
        return device;
    }

    @Override
    public Device getDeviceByNo(String deviceNo) {
        Device device = null;
        String result = cacheService.getCache("vns:device:info:" + deviceNo);
        if (result != null && !"".equals(result)) {
            device = JSON.parseObject(result, Device.class);
        } else {
            device = (Device) this.getOne(new LambdaQueryWrapper<Device>().eq(Device::getSerialNo, deviceNo).last("limit 1"));
            cacheService.setCache("vns:device:info:" + deviceNo, JSON.toJSONString(device), 60);
        }
        return device;
    }

    @Override
    public DeviceUserVo getDeviceDetailByNo(String deviceNo, Long userId) {

        DeviceUserVo deviceUserVo = userDeviceDao.getUserDeviceDetail(deviceNo, userId);

        return deviceUserVo;
    }

}
