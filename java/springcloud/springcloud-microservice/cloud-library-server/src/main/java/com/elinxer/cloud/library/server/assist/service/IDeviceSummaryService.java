package com.elinxer.cloud.library.server.assist.service;

import com.dbn.cloud.platform.database.mybatis.impl.BaseService;
import com.dbn.cloud.platform.web.crud.web.result.PagerResult;
import com.elinxer.cloud.library.server.assist.domain.entity.DeviceSummary;
import com.elinxer.cloud.library.server.assist.domain.entity.DeviceSummaryLog;
import com.elinxer.cloud.library.server.assist.domain.vo.device.DeviceSnapshotVo;
import com.elinxer.cloud.library.server.assist.domain.vo.device.DeviceSummaryVo;

import java.util.Set;

public interface IDeviceSummaryService extends BaseService<DeviceSummary> {

    DeviceSnapshotVo getDeviceSnapshot(String deviceNo);

    DeviceSummaryVo getDeviceTaskSummary(String deviceNo, String taskNo);

    PagerResult<DeviceSummaryLog> getDeviceTaskSummaryLog(String deviceNo, String taskNo, int page, int limit);

    Double getDeviceTotalArea(Set<String> devices);

}
