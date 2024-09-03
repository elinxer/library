package com.dbn.cloud.auth.uua.token;


import com.dbn.cloud.auth.uua.service.ValidateCodeService;
import com.dbn.cloud.platform.core.utils.StringUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 描述 : 密码增强模式
 */
public class PasswordEnhanceTokenGranter extends AbstractTokenGranter {

    private static final String GRANT_TYPE = "password";

    private final AuthenticationManager authenticationManager;

    private final ValidateCodeService validateCodeService;

    public PasswordEnhanceTokenGranter(AuthenticationManager authenticationManager,
                                       AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, ValidateCodeService validateCodeService) {
        this(authenticationManager, tokenServices, clientDetailsService, requestFactory, GRANT_TYPE, validateCodeService);
    }

    protected PasswordEnhanceTokenGranter(AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices,
                                          ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, String grantType, ValidateCodeService validateCodeService) {
        super(tokenServices, clientDetailsService, requestFactory, grantType);
        this.authenticationManager = authenticationManager;
        this.validateCodeService = validateCodeService;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {

        Map<String, String> parameters = new LinkedHashMap<String, String>(tokenRequest.getRequestParameters());
        String username = parameters.get("username");
        String password = parameters.get("password");

        //终端
        String deviceId = MapUtils.getString(parameters, "deviceId");
        //验证码
        String validCode = MapUtils.getString(parameters, "validCode");


        //校验图形验证码
        if (StringUtils.isNotBlank(deviceId) || StringUtils.isNotEmpty(validCode)) {
            try {
                validateCodeService.validate(deviceId, validCode);
            } catch (Exception e) {
                throw new InvalidGrantException(e.getMessage());
            }
        }


        // Protect from downstream leaks of password
        parameters.remove("password");
        parameters.remove("deviceId");
        parameters.remove("validCode");


        Authentication userAuth = new UsernamePasswordAuthenticationToken(username, password);
        ((AbstractAuthenticationToken) userAuth).setDetails(parameters);
        try {
            userAuth = authenticationManager.authenticate(userAuth);
        } catch (AccountStatusException ase) {
            throw new InvalidGrantException(ase.getMessage());
        } catch (BadCredentialsException e) {
            throw new InvalidGrantException(e.getMessage());
        }
        if (userAuth == null || !userAuth.isAuthenticated()) {
            throw new InvalidGrantException("Could not authenticate user: " + username);
        }

        OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(storedOAuth2Request, userAuth);
    }

}