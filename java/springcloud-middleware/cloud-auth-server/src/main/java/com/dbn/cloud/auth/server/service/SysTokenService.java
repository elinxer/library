package com.dbn.cloud.auth.server.service;


import com.dbn.cloud.platform.web.crud.web.result.PagerResult;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.util.Map;

public interface SysTokenService {
    //通用校验
    void preCheckClient(String clientId, String clientSecret);

    //模拟客户端模式
    OAuth2AccessToken getClientTokenInfo(String clientId, String clientSecret);

    //模拟密码模式
    OAuth2AccessToken getUserTokenInfo(String clientId, String clientSecret, String username, String password);

    //模拟手机验证码模式
    OAuth2AccessToken getMobileTokenInfo(String clientId, String clientSecret, String deviceId, String validCode);

    //刷新
    OAuth2AccessToken getRefreshTokenInfo(String access_token);

    //token list
    PagerResult<Map<String, String>> getTokenList(Map<String, Object> params);

    //移除
    void removeToken(String access_token);

    OAuth2AccessToken getTokenInfo(String access_token);

    String desEncrypt(String val);
}
