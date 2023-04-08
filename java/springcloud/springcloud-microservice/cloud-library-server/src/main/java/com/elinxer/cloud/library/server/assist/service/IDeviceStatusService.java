package com.elinxer.cloud.library.server.assist.service;

import com.elinxer.cloud.library.server.assist.domain.DeviceStatus;
import com.elinxer.cloud.library.server.assist.domain.enums.DeviceStatusEnum;

import java.util.Map;
import java.util.Set;

public interface IDeviceStatusService {

    void setStatus(DeviceStatus deviceStatus);

    DeviceStatus getStatus(String deviceNo);

    DeviceStatusEnum getStatusEnum(String deviceNo);

    Map<String, Integer> matchDeviceStatus(Set<String> devices);

    void refreshDeviceLive(String deviceNO);

    Map<Object, Object> getDeviceLiveAllCache();

    Integer getDeviceOnlineNum(Set<String> devices);
}
