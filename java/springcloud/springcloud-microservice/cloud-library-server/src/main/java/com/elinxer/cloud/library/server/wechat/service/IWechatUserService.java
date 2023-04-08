package com.elinxer.cloud.library.server.wechat.service;

import com.elinxer.cloud.library.server.wechat.domain.entity.WechatUser;

public interface IWechatUserService {

    void saveUser(WechatUser entity);

    WechatUser getUserByOpenId(String openid);

    void updateUser(WechatUser user);

}
