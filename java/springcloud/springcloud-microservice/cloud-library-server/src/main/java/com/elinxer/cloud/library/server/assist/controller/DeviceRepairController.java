package com.elinxer.cloud.library.server.assist.controller;

import com.dbn.cloud.platform.exception.AppException;
import com.dbn.cloud.platform.security.utils.SysUserUtils;
import com.dbn.cloud.platform.web.crud.web.result.ResponseMessage;
import com.elinxer.cloud.library.server.assist.aop.RequestLimit;
import com.elinxer.cloud.library.server.assist.domain.vo.repair.DeviceRepairVo;
import com.elinxer.cloud.library.server.assist.service.IDeviceRepairService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author elinx
 */
@Slf4j
@RequestMapping("/v1/assist/device-repair")
@RestController
public class DeviceRepairController {

    @Resource
    IDeviceRepairService iDeviceRepairService;


    @RequestLimit(time = 60)
    @PostMapping("/repair")
    @ApiOperation(value = "新增报修")
    public ResponseMessage<?> repair(@RequestBody @Validated DeviceRepairVo repairVo) {
        if (SysUserUtils.getLoginAssistUser() == null) {
            throw new AppException("请先登录");
        }

        repairVo.setUserId(SysUserUtils.getLoginAssistUser().getId());

        return ResponseMessage.ok(iDeviceRepairService.addDeviceRepair(repairVo));
    }

    @GetMapping("/list")
    @ApiOperation(value = "报修工单列表")
    public ResponseMessage<?> list(String deviceNo) {
        if (SysUserUtils.getLoginAssistUser() == null) {
            throw new AppException("请先登录");
        }
        Long userId = SysUserUtils.getLoginAssistUser().getId();
        return ResponseMessage.ok(iDeviceRepairService.getDeviceRepairList(userId, deviceNo));
    }

    @GetMapping("/detail")
    @ApiOperation(value = "报修详情")
    public ResponseMessage<DeviceRepairVo> detail(Long id) {
        if (SysUserUtils.getLoginAssistUser() == null) {
            throw new AppException("请先登录");
        }
        Long userId = SysUserUtils.getLoginAssistUser().getId();
        return ResponseMessage.ok(iDeviceRepairService.getDeviceRepairDetail(id, userId));
    }

}
