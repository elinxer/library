package com.elinxer.cloud.library.server.assist.service;

import com.dbn.cloud.platform.database.mybatis.impl.BaseService;
import com.dbn.cloud.platform.web.crud.web.result.PagerResult;
import com.elinxer.cloud.library.server.assist.domain.entity.Task;
import com.elinxer.cloud.library.server.assist.domain.vo.device.DeviceTaskVo;

import java.util.List;
import java.util.Set;

public interface ITaskService extends BaseService<Task> {


    List<DeviceTaskVo> getDeviceTasks(String deviceNo);

    boolean saveUserTask(Task task);

    PagerResult<DeviceTaskVo> getDeviceTasksPage(String deviceNo, Integer page, Integer limit);

    int getDeviceTaskTotal(Set<String> devices);

}
