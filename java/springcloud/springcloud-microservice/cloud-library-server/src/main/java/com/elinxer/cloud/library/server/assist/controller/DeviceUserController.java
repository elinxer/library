package com.elinxer.cloud.library.server.assist.controller;

import cn.hutool.core.date.DateUtil;
import com.dbn.cloud.platform.exception.AppException;
import com.dbn.cloud.platform.security.entity.LoginAssistUser;
import com.dbn.cloud.platform.security.utils.SysUserUtils;
import com.dbn.cloud.platform.web.crud.web.result.PagerResult;
import com.dbn.cloud.platform.web.crud.web.result.ResponseMessage;
import com.elinxer.cloud.library.server.assist.domain.DeviceStatus;
import com.elinxer.cloud.library.server.assist.domain.entity.DeviceSummaryLog;
import com.elinxer.cloud.library.server.assist.domain.vo.device.DeviceSnapshotVo;
import com.elinxer.cloud.library.server.assist.domain.vo.device.DeviceTaskVo;
import com.elinxer.cloud.library.server.assist.domain.vo.device.DeviceUserVo;
import com.elinxer.cloud.library.server.assist.domain.vo.device.HomeSummaryVo;
import com.elinxer.cloud.library.server.assist.service.*;
import com.elinxer.cloud.library.server.assist.service.*;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author elinx
 */
@Slf4j
@RequestMapping("/v1/assist/device-user")
@RestController
public class DeviceUserController {

    @Resource
    private IUserService iUserService;

    @Resource
    IDeviceService iDeviceService;

    @Resource
    IDeviceStatusService iDeviceStatusService;

    @Resource
    IDeviceSummaryService iDeviceSummaryService;

    @Resource
    ITaskService iTaskService;

    @GetMapping("/deviceDetail")
    @ApiOperation(value = "设备详情")
    public ResponseMessage<DeviceUserVo> deviceDetail(String deviceId) {
        if (SysUserUtils.getLoginAssistUser() == null) {
            throw new AppException("请先登录");
        }
        Long userId = SysUserUtils.getLoginAssistUser().getId();
        return ResponseMessage.ok(iDeviceService.getDeviceDetailByNo(deviceId, userId));
    }

    @GetMapping("/deviceSnapshot")
    @ApiOperation(value = "设备信息快照")
    public ResponseMessage<DeviceSnapshotVo> deviceSnapshot(String deviceId) {
        if (SysUserUtils.getLoginAssistUser() == null) {
            throw new AppException("请先登录");
        }
        return ResponseMessage.ok(DeviceSnapshotVo.builder().build());
    }

    @GetMapping("/homeSummary")
    @ApiOperation(value = "首页统计数据")
    public ResponseMessage<HomeSummaryVo> homeSummary() {
        if (SysUserUtils.getLoginAssistUser() == null) {
            throw new AppException("请先登录");
        }

        Long userId = SysUserUtils.getLoginAssistUser().getId();
        List<DeviceUserVo> list = iUserService.getUserDevices(userId);
        List<DeviceSnapshotVo> deviceSnapshots = new ArrayList<>();

        Set<String> devices = new HashSet<>();
        int totalDevice = 0;
        if (list != null && list.size() >= 1) {
            list.forEach(item -> {
                devices.add(item.getSerialNo());
                DeviceSnapshotVo deviceSnapshotVo = iDeviceSummaryService.getDeviceSnapshot(item.getSerialNo());

                DeviceStatus deviceStatus = iDeviceStatusService.getStatus(item.getSerialNo());
                deviceSnapshotVo.setState(deviceStatus != null ? deviceStatus.getStatus().getValue() : 0);

                deviceSnapshots.add(deviceSnapshotVo);
            });
            totalDevice = list.size();
        }
        Integer online = iDeviceStatusService.getDeviceOnlineNum(devices);

        int totalTask = iTaskService.getDeviceTaskTotal(devices);

        double totalArea = iDeviceSummaryService.getDeviceTotalArea(devices);

        return ResponseMessage.ok(HomeSummaryVo.builder()
                .curDate(DateUtil.format(new Date(), "yyyy-MM-dd"))
                .totalDevice(totalDevice)
                .totalTask(totalTask)
                .totalArea(totalArea)
                .totalOnlineDevice(online)
                .deviceSnapshots(deviceSnapshots)
                .build());
    }

    @GetMapping("/tasks")
    @ApiOperation(value = "设备作业记录")
    public ResponseMessage<PagerResult<DeviceTaskVo>> tasks(String deviceId, Integer page, Integer limit) {
        if (SysUserUtils.getLoginAssistUser() == null) {
            throw new AppException("请先登录");
        }

        // TODO 跟进用户ID判断是否属于用户

        return ResponseMessage.ok(iTaskService.getDeviceTasksPage(deviceId, page, limit));
    }

    @GetMapping("/taskDetail")
    @ApiOperation(value = "记录详情")
    public ResponseMessage<DeviceTaskVo> taskDetail(String taskId) {
        LoginAssistUser loginAssistUser = SysUserUtils.getLoginAssistUser();
        if (loginAssistUser == null) {
            throw new AppException("请先登录");
        }
        return ResponseMessage.ok(DeviceTaskVo.builder().build());
    }

    @GetMapping("/taskTrails")
    @ApiOperation(value = "作业轨迹")
    public ResponseMessage<PagerResult<DeviceSummaryLog>> taskTrails(String deviceId, String taskNo, Integer page, Integer limit) {
        LoginAssistUser loginAssistUser = SysUserUtils.getLoginAssistUser();
        if (loginAssistUser == null) {
            throw new AppException("请先登录");
        }

        // TODO 跟进用户ID判断是否属于用户

        return ResponseMessage.ok(iDeviceSummaryService.getDeviceTaskSummaryLog(deviceId, taskNo, page, limit));
    }

}
