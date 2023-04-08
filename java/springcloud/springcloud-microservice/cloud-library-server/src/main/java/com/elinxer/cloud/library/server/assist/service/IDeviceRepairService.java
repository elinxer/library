package com.elinxer.cloud.library.server.assist.service;

import com.dbn.cloud.platform.database.mybatis.impl.BaseService;
import com.elinxer.cloud.library.server.assist.domain.entity.DeviceRepair;
import com.elinxer.cloud.library.server.assist.domain.vo.repair.DeviceRepairVo;

import java.util.List;

public interface IDeviceRepairService extends BaseService<DeviceRepair> {

    Boolean addDeviceRepair(DeviceRepairVo repairVo);

    DeviceRepairVo getDeviceRepairDetail(Long id, Long userId);


    List<DeviceRepairVo> getDeviceRepairList(Long userId, String deviceNo);


}
