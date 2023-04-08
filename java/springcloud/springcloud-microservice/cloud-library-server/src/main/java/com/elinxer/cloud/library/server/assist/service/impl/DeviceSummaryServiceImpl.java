package com.elinxer.cloud.library.server.assist.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dbn.cloud.platform.cache.redis.CacheService;
import com.dbn.cloud.platform.core.utils.BeanConvertUtils;
import com.dbn.cloud.platform.database.mybatis.impl.BaseServiceImpl;
import com.dbn.cloud.platform.web.crud.web.result.PagerResult;
import com.elinxer.cloud.library.server.assist.domain.dao.DeviceSummaryDao;
import com.elinxer.cloud.library.server.assist.domain.dao.DeviceSummaryLogDao;
import com.elinxer.cloud.library.server.assist.domain.entity.DeviceSummary;
import com.elinxer.cloud.library.server.assist.domain.entity.DeviceSummaryLog;
import com.elinxer.cloud.library.server.assist.domain.vo.device.DeviceSnapshotVo;
import com.elinxer.cloud.library.server.assist.domain.vo.device.DeviceSummaryVo;
import com.elinxer.cloud.library.server.assist.service.IDeviceSummaryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

@Service
public class DeviceSummaryServiceImpl extends BaseServiceImpl<DeviceSummaryDao, DeviceSummary> implements IDeviceSummaryService {


    private static final String DEVICE_SNAPSHOT = "vns:device:snapshot:";


    @Resource
    CacheService cacheService;

    @Resource
    DeviceSummaryLogDao deviceSummaryLogDao;


    @Override
    public DeviceSnapshotVo getDeviceSnapshot(String deviceNo) {
        String result = cacheService.getCache(DEVICE_SNAPSHOT + deviceNo);
        if (result != null && !"".equals(result)) {
            return JSON.parseObject(result, DeviceSnapshotVo.class);
        }
        return DeviceSnapshotVo.builder().serialNo(deviceNo).state(0).build();
    }

    @Override
    public DeviceSummaryVo getDeviceTaskSummary(String deviceNo, String taskNo) {
        DeviceSummary deviceSummary = (DeviceSummary) this.getOne(new LambdaQueryWrapper<DeviceSummary>()
                .eq(DeviceSummary::getDeviceNo, deviceNo).last("limit 1"));

        return BeanConvertUtils.sourceToTarget(deviceSummary, DeviceSummaryVo.class);
    }

    @Override
    public PagerResult<DeviceSummaryLog> getDeviceTaskSummaryLog(String deviceNo, String taskNo, int page, int limit) {
        IPage<DeviceSummaryLog> iPage = new Page<>(page, limit);

        IPage<DeviceSummaryLog> result = deviceSummaryLogDao.selectPage(iPage, new LambdaQueryWrapper<DeviceSummaryLog>()
                .eq(DeviceSummaryLog::getDeviceNo, deviceNo)
                .eq(DeviceSummaryLog::getTaskNo, taskNo));

        return PagerResult.of(iPage, result.getRecords());
    }

    @Override
    public Double getDeviceTotalArea(Set<String> devices) {
        //求某人的成绩和。
        QueryWrapper<DeviceSummary> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("IFNULL(sum(area),0) as totalArea")
                .in("device_no", devices);
        Map<String, Object> map = this.getMap(queryWrapper);
        BigDecimal totalArea = (BigDecimal) map.get("totalArea");
        if (totalArea == null) {
            return 0.00;
        }
        return totalArea.doubleValue();
    }

}
