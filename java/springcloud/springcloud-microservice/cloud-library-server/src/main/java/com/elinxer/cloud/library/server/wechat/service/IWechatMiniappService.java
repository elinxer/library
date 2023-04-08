package com.elinxer.cloud.library.server.wechat.service;

import com.elinxer.cloud.library.server.wechat.domain.vo.WechatLoginVo;
import me.chanjar.weixin.common.error.WxErrorException;

public interface IWechatMiniappService {

    /**
     * 授权登录
     *
     * @param entity
     * @return
     */
    void login(WechatLoginVo entity) throws WxErrorException;


}
