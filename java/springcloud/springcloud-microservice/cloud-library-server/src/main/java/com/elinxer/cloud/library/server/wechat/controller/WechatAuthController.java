package com.elinxer.cloud.library.server.wechat.controller;

import com.dbn.cloud.platform.web.crud.web.result.ResponseMessage;
import com.elinxer.cloud.library.server.wechat.domain.vo.WechatLoginVo;
import com.elinxer.cloud.library.server.wechat.service.IWechatUserService;
import com.elinxer.cloud.library.server.wechat.service.impl.WechatMiniappService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Slf4j
@RestController
@RequestMapping("/wechat/auth")
public class WechatAuthController {

    @Resource
    WechatMiniappService wxMiniappService;

    @Resource
    IWechatUserService iWechatUserService;


    @GetMapping("/test")
    @ApiOperation(value = "test")
    public String test(WechatLoginVo loginVo) {

        iWechatUserService.saveUser(null);

        return "String";
    }

    @ApiOperation(value = "微信登录")
    @PostMapping("login")
    public ResponseMessage<?> login(@RequestBody WechatLoginVo loginVo) {
        try {
            wxMiniappService.login(loginVo);
        } catch (WxErrorException e) {
            return ResponseMessage.error(e.getMessage());
        }
        return ResponseMessage.ok();
    }

}
