package com.elinxer.cloud.library.server.assist.service;

import com.dbn.cloud.platform.database.mybatis.impl.BaseService;
import com.elinxer.cloud.library.server.assist.domain.entity.Device;
import com.elinxer.cloud.library.server.assist.domain.vo.device.DeviceUserVo;

public interface IDeviceService extends BaseService<Device> {


    boolean updateDeviceStatus(Device device);


    Device getDeviceByClientId(String clientId);

    Device getDeviceByNo(String deviceNo);


    DeviceUserVo getDeviceDetailByNo(String deviceNo, Long userId);

}
