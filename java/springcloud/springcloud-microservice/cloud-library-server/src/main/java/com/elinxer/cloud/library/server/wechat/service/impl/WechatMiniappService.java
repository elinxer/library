package com.elinxer.cloud.library.server.wechat.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.dbn.cloud.platform.exception.AppException;
import com.elinxer.cloud.library.server.wechat.config.WechatMaConfiguration;
import com.elinxer.cloud.library.server.wechat.config.WechatMaProperties;
import com.elinxer.cloud.library.server.wechat.domain.entity.WechatUser;
import com.elinxer.cloud.library.server.wechat.domain.vo.WechatLoginVo;
import com.elinxer.cloud.library.server.wechat.service.IWechatMiniappService;
import com.elinxer.cloud.library.server.wechat.service.IWechatUserService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class WechatMiniappService implements IWechatMiniappService {

    @Resource
    private WechatMaProperties wechatMaProperties;

    @Resource
    IWechatUserService iWechatUserService;

    @Override
    public void login(WechatLoginVo entity) throws WxErrorException {
        if (entity.getCode() == null || "".equals(entity.getCode())) {
            throw new AppException("code不能为空");
        }

        String appid = wechatMaProperties.getConfigs().get(0).getAppid();
        final WxMaService wxService = WechatMaConfiguration.getMaService(appid);

        try {
            // code换取session
            WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(entity.getCode());
            log.info("code换取session：{}", session);
            // 用户信息校验
            if (entity.getRawData() != null) {

                if (!wxService.getUserService().checkUserInfo(session.getSessionKey(), entity.getRawData(), entity.getSignature())) {
                    throw new AppException("用户信息校验失败");
                }

                WxMaUserInfo userInfo = wxService.getUserService().getUserInfo(session.getSessionKey(), entity.getEncryptedData(), entity.getIv());
                log.info("解密用户信息：{}", userInfo);

                WxMaPhoneNumberInfo phoneNoInfo = wxService.getUserService().getPhoneNoInfo(session.getSessionKey(), entity.getEncryptedData(), entity.getIv());
                log.info("获取用户绑定手机号信息：{}", phoneNoInfo);

                // =============================== 处理业务

                // 根据openId查询是否存在这个用户
                WechatUser wechatUser = iWechatUserService.getUserByOpenId(userInfo.getOpenId());

                if (wechatUser == null) {
                    WechatUser user = new WechatUser();
                    user.setOpenId(session.getOpenid());
                    user.setNickname(userInfo.getNickName());
                    user.setPhone(phoneNoInfo.getPhoneNumber());
                    user.setAvatarUrl(userInfo.getAvatarUrl());
                    user.setGender(userInfo.getGender());
                    iWechatUserService.saveUser(user);
                } else {
                    // 更新用户信息
                    wechatUser.setOpenId(null);
                    iWechatUserService.updateUser(wechatUser);
                }
            }
        } catch (WxErrorException e) {
            e.printStackTrace();
            throw e;
        }
    }

}
