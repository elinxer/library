package com.dbn.cloud.auth.uua.token;

import com.dbn.cloud.auth.uua.service.ValidateCodeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.LinkedHashMap;
import java.util.Map;

public class SMSCodeTokenGranter extends AbstractTokenGranter {

    private static final String GRANT_TYPE = "mobile";

    private final UserDetailsService userDetailsService;

    private final ValidateCodeService validateCodeService;

    public SMSCodeTokenGranter(UserDetailsService userDetailsService, ValidateCodeService validateCodeService,
                               AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService,
                               OAuth2RequestFactory requestFactory) {
        this(userDetailsService, validateCodeService, tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
    }

    protected SMSCodeTokenGranter(UserDetailsService userDetailsService, ValidateCodeService validateCodeService,
                                  AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService,
                                  OAuth2RequestFactory requestFactory, String grantType) {
        super(tokenServices, clientDetailsService, requestFactory, grantType);
        this.userDetailsService = userDetailsService;
        this.validateCodeService = validateCodeService;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {

        Map<String, String> parameters = new LinkedHashMap<String, String>(tokenRequest.getRequestParameters());
        String deviceId = parameters.get("deviceId"); // 客户端提交的用户名
        String validCode = parameters.get("validCode"); // 客户端提交的验证码

        if (StringUtils.isBlank(deviceId)) {
            throw new InvalidGrantException("用户输入deviceId");
        }
        if (StringUtils.isBlank(validCode)) {
            throw new InvalidGrantException("用户没有输入validCode");
        }

        // 得到生成的验证码
        String code = "";
        try {
            code = validateCodeService.getCode(deviceId);
            if (!validCode.equals(code)) {
                throw new InvalidGrantException("验证码不正确");
            } else {
                // 移除验证码
                validateCodeService.remove(deviceId);
            }
        } catch (Exception e) {
            throw new InvalidGrantException("验证码不存在");
        }

        // 根据手机号查询用户
        UserDetails user = null;
        try {
            user = userDetailsService.loadUserByUsername("LOGIN_MOBILE_ASSIST_" + deviceId);
            if (!user.isEnabled()) {
                throw new InvalidGrantException("用户状态已禁用");
            }
        } catch (Exception e) {
            throw new InvalidGrantException("用户（手机号）不存在Granter");
        }

        Authentication userAuth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        // 关于user.getAuthorities(): 我们的自定义用户实体是实现了
        // org.springframework.security.core.userdetails.UserDetails 接口的, 所以有
        // user.getAuthorities()
        // 当然该参数传null也行
        ((AbstractAuthenticationToken) userAuth).setDetails(parameters);

        OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(storedOAuth2Request, userAuth);
    }

}