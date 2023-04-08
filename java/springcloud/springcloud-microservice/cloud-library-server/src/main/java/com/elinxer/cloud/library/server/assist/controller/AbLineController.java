package com.elinxer.cloud.library.server.assist.controller;

import com.dbn.cloud.platform.cache.redis.CacheService;
import com.dbn.cloud.platform.core.utils.BeanConvertUtils;
import com.dbn.cloud.platform.web.crud.web.result.ResponseMessage;
import com.elinxer.cloud.library.server.assist.domain.entity.Abline;
import com.elinxer.cloud.library.server.assist.domain.vo.AbLineVo;
import com.elinxer.cloud.library.server.assist.service.IAblineService;
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
@RequestMapping("/v1/assist/abline")
@RestController
public class AbLineController {

    @Autowired
    CacheService cacheService;

    @Resource
    private IAblineService iAblineService;

    @PostMapping("/report")
    @ApiOperation(value = "AB线上报")
    public ResponseMessage<Boolean> report(@RequestBody @Validated AbLineVo req) {

        // TODO

        iAblineService.save(BeanConvertUtils.sourceToTarget(req, Abline.class));
        return ResponseMessage.ok(true);
    }

}
