package com.elinxer.cloud.library.server.wechat.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 微信登录参数
 */
@Data
@Accessors(chain = true)
public class WechatLoginVo {

    /**
     * 微信返回的code
     */
    private String code;

    /**
     * 非敏感的用户信息
     */
    private String rawData;

    /**
     * 签名信息
     */
    private String signature;

    /**
     * 加密的数据
     */
    private String encryptedData;

    /**
     * 加密密钥
     */
    private String iv;

}

