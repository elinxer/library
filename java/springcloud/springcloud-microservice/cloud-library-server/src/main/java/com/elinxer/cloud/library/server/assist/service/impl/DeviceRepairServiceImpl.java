package com.elinxer.cloud.library.server.assist.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dbn.cloud.platform.core.utils.BeanConvertUtils;
import com.dbn.cloud.platform.database.mybatis.impl.BaseServiceImpl;
import com.elinxer.cloud.library.server.assist.domain.dao.DeviceRepairDao;
import com.elinxer.cloud.library.server.assist.domain.entity.DeviceRepair;
import com.elinxer.cloud.library.server.assist.domain.vo.repair.DeviceRepairVo;
import com.elinxer.cloud.library.server.assist.service.IDeviceRepairService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceRepairServiceImpl extends BaseServiceImpl<DeviceRepairDao, DeviceRepair> implements IDeviceRepairService {


    @Override
    public Boolean addDeviceRepair(DeviceRepairVo repairVo) {
        DeviceRepair deviceRepair = BeanConvertUtils.sourceToTarget(repairVo, DeviceRepair.class);
        return this.save(deviceRepair);
    }

    @Override
    public DeviceRepairVo getDeviceRepairDetail(Long id, Long userId) {
        DeviceRepair deviceRepair = (DeviceRepair) this.getOne(new LambdaQueryWrapper<DeviceRepair>()
                .eq(DeviceRepair::getUserId, userId)
                .eq(DeviceRepair::getId, id)
                .last("limit 1"));
        return BeanConvertUtils.sourceToTarget(deviceRepair, DeviceRepairVo.class);
    }

    @Override
    public List<DeviceRepairVo> getDeviceRepairList(Long userId, String deviceNo) {

        List<DeviceRepair> deviceRepairs = null;
        if (deviceNo != null) {
            deviceRepairs = this.list(new LambdaQueryWrapper<DeviceRepair>()
                    .eq(DeviceRepair::getUserId, userId)
                    .eq(DeviceRepair::getDeviceNo, deviceNo)
                    .orderByDesc(DeviceRepair::getId)
                    .last("limit 999"));
        } else {
            deviceRepairs = this.list(new LambdaQueryWrapper<DeviceRepair>()
                    .eq(DeviceRepair::getUserId, userId)
                    .orderByDesc(DeviceRepair::getId)
                    .last("limit 999"));
        }

        return BeanConvertUtils.toTarget(deviceRepairs, DeviceRepairVo.class);
    }


}
