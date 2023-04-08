package com.elinxer.cloud.library.server.assist.controller;

import com.dbn.cloud.platform.cache.redis.CacheService;
import com.dbn.cloud.platform.web.crud.web.result.ResponseMessage;
import com.elinxer.cloud.library.server.assist.domain.vo.ApkUpgradeVo;
import com.elinxer.cloud.library.server.assist.service.IApkService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author elinx
 */
@Slf4j
@RequestMapping("/v1/assist/apk")
@RestController
public class ApkController {

    @Autowired
    CacheService cacheService;

    @Resource
    private IApkService iApkService;

    @PostMapping("/upgrade")
    @ApiOperation(value = "APK升级列表")
    public ResponseMessage<?> upgrade(@RequestBody @Validated ApkUpgradeVo req) {
        return ResponseMessage.ok(iApkService.upgradeList(req));
    }

}
