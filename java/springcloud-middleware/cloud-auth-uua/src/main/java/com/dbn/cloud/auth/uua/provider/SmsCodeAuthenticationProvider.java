package com.dbn.cloud.auth.uua.provider;


import com.dbn.cloud.platform.security.token.SmsCodeAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    @Autowired(required = false)
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsCodeAuthenticationToken token = (SmsCodeAuthenticationToken) authentication;

        UserDetails userDetails = userDetailsService.loadUserByUsername((String) token.getPrincipal());

        if (userDetails == null) {
            throw new AuthenticationCredentialsNotFoundException("用户（手机号）不存在.");
        } else if (!userDetails.isEnabled()) {
            throw new DisabledException("用户已作废");
        }

        SmsCodeAuthenticationToken authenticationResult = new SmsCodeAuthenticationToken(userDetails, userDetails.getAuthorities());
        // 需要把未认证中的一些信息copy到已认证的token中
        authenticationResult.setDetails(token);

        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(aClass);
    }

}
