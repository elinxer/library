package com.elinxer.cloud.library.server.assist.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dbn.cloud.platform.cache.redis.CacheService;
import com.dbn.cloud.platform.core.utils.BeanConvertUtils;
import com.dbn.cloud.platform.database.mybatis.impl.BaseServiceImpl;
import com.dbn.cloud.platform.exception.AppException;
import com.dbn.cloud.platform.web.crud.web.result.PagerResult;
import com.elinxer.cloud.library.server.assist.domain.dao.TaskDao;
import com.elinxer.cloud.library.server.assist.domain.entity.Task;
import com.elinxer.cloud.library.server.assist.domain.vo.device.DeviceSummaryVo;
import com.elinxer.cloud.library.server.assist.domain.vo.device.DeviceTaskVo;
import com.elinxer.cloud.library.server.assist.service.IDeviceSummaryService;
import com.elinxer.cloud.library.server.assist.service.ITaskService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Service
public class TaskServiceImpl extends BaseServiceImpl<TaskDao, Task> implements ITaskService {

    @Resource
    CacheService cacheService;

    @Resource
    IDeviceSummaryService iDeviceSummaryService;


    @Override
    public PagerResult<DeviceTaskVo> getDeviceTasksPage(String deviceNo, Integer page, Integer limit) {

        IPage<DeviceTaskVo> iPage = new Page<>((long) page, (long) limit);

        IPage<Task> result = this.page(iPage, new LambdaQueryWrapper<Task>()
                .eq(Task::getDeviceNo, deviceNo)
                .eq(Task::getDeleted, 0)
                .orderByDesc(Task::getId));

        List<DeviceTaskVo> taskVoList = BeanConvertUtils.toTarget(result.getRecords(), DeviceTaskVo.class);

        if (taskVoList != null) {
            for (DeviceTaskVo deviceTaskVo : taskVoList) {
                // TODO 他快啊
                DeviceSummaryVo deviceSummaryVo = iDeviceSummaryService.getDeviceTaskSummary(deviceNo, deviceTaskVo.getTaskNo());
                if (deviceSummaryVo != null) {
                    deviceTaskVo.setArea(deviceSummaryVo.getArea());
                    deviceTaskVo.setMileage(deviceSummaryVo.getMileage());
                    deviceTaskVo.setDuration(deviceSummaryVo.getDuration());
                }
            }
        }

        return PagerResult.of(iPage, taskVoList);
    }

    @Override
    public List<DeviceTaskVo> getDeviceTasks(String deviceNo) {
        return this.list(new LambdaQueryWrapper<Task>().eq(Task::getDeviceNo, deviceNo).orderByDesc(Task::getId)
                .last("limit 1000"));
    }

    public Task getTaskByNo(String taskNo) {
        return (Task) this.getOne(new LambdaQueryWrapper<Task>().eq(Task::getTaskNo, taskNo).last("limit 1"));
    }

    @Override
    public boolean saveUserTask(Task task) {
        if (task.getTaskNo() == null) {
            throw new AppException("task no error.");
        }
        if (getTaskByNo(task.getTaskNo()) != null) {
            this.update(task, new LambdaQueryWrapper<Task>().eq(Task::getTaskNo, task.getTaskNo()));
            return true;
        } else {
            return this.save(task);
        }
    }


    @Override
    public int getDeviceTaskTotal(Set<String> devices) {
        return this.count(new LambdaQueryWrapper<Task>().in(Task::getDeviceNo, devices));
    }

}
