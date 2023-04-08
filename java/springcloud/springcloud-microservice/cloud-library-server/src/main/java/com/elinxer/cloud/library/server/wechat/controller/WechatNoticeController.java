package com.elinxer.cloud.library.server.wechat.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import com.elinxer.cloud.library.server.wechat.config.WechatMaConfiguration;
import com.elinxer.cloud.library.server.wechat.config.WechatMaProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * notice
 */
@RestController
@RequestMapping("/wechat/notice")
@Api(tags = "WechatNoticeController")
public class WechatNoticeController {

    @Resource
    private WechatMaProperties wechatMaProperties;

    @GetMapping("/index")
    @ApiOperation(value = "消息通知")
    public String index(String signature, String timestamp, String nonce, String echostr) {

        String appid = wechatMaProperties.getConfigs().get(0).getAppid();

        final WxMaService wxService = WechatMaConfiguration.getMaService(appid);

        if (wxService.checkSignature(timestamp, nonce, signature)) {
            return echostr;
        }

        return "error";
    }

}
