package com.dbn.cloud.platform.security.token;


import com.dbn.cloud.platform.security.entity.LoginAppUser;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;


public class ResJwtTokenStore extends JwtTokenStore {
    public ResJwtTokenStore(JwtAccessTokenConverter jwtTokenEnhancer) {
        super(jwtTokenEnhancer);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {

        LoginAppUser user = (LoginAppUser) authentication.getPrincipal();

        token.getAdditionalInformation().put("user", user);

    }

}
