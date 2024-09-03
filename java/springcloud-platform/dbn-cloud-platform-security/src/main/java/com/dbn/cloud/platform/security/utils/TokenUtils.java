package com.dbn.cloud.platform.security.utils;


import com.dbn.cloud.platform.core.utils.StringUtils;
import com.dbn.cloud.platform.security.constant.UaaConstant;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * TokenUtils工具类
 *
 * @author elinx
 * @version 1.0.0
 */
public class TokenUtils {

    public static String getToken() {
        String token = "";
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            String header = request.getHeader(UaaConstant.AUTHORIZATION);
            token = StringUtils.isBlank(StringUtils.substringAfter(header, OAuth2AccessToken.BEARER_TYPE + " ")) ? request.getParameter(OAuth2AccessToken.ACCESS_TOKEN) : StringUtils.substringAfter(header, OAuth2AccessToken.BEARER_TYPE + " ");
            token = StringUtils.isBlank(request.getHeader(UaaConstant.TOKEN_HEADER)) ? token : request.getHeader(UaaConstant.TOKEN_HEADER);
        } catch (IllegalStateException e) {
        }
        return token;
    }

    public static String extractToken(ServerHttpRequest request) {
        List<String> strings = request.getHeaders().get(UaaConstant.AUTHORIZATION);
        String authToken = "";
        if (!StringUtils.isEmpty(strings) && strings.get(0).contains("Bearer")) {
            authToken = strings.get(0).substring("Bearer".length()).trim();
        }
        if (org.apache.commons.lang3.StringUtils.isBlank(authToken)) {
            strings = request.getQueryParams().get(UaaConstant.TOKEN_PARAM);
            if (!StringUtils.isEmpty(strings)) {
                authToken = strings.get(0);
            }
        }
        return authToken;
    }
}
