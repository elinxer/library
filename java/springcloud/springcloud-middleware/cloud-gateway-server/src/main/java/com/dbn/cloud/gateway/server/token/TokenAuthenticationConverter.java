package com.dbn.cloud.gateway.server.token;

/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.BearerTokenError;
import org.springframework.security.oauth2.server.resource.BearerTokenErrorCodes;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TokenAuthenticationConverter
        implements ServerAuthenticationConverter {
    private static final Pattern authorizationPattern = Pattern.compile("^Bearer (?<token>[a-zA-Z0-9-._~+/]+)=*$");

    private boolean allowUriQueryParameter = false;

    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return Mono.justOrEmpty(this.token(exchange.getRequest()))
                .map(BearerTokenAuthenticationToken::new);
    }

    private String token(ServerHttpRequest request) {

        String authorizationHeaderToken = resolveFromAuthorizationHeader(request.getHeaders());
        String parameterToken = request.getQueryParams().getFirst("access_token");
        if (authorizationHeaderToken != null) {
            // must have value {"code":401,"msg":"Not Authenticated"}
            if (parameterToken != null) {
                //add by someday
                if (!authorizationHeaderToken.equals(parameterToken)) {
                    BearerTokenError error = new BearerTokenError(BearerTokenErrorCodes.INVALID_REQUEST,
                            HttpStatus.BAD_REQUEST,
                            "Found multiple bearer tokens in the request",
                            "https://tools.ietf.org/html/rfc6750#section-3.1");
                    throw new OAuth2AuthenticationException(error);
                }


            }
            return authorizationHeaderToken;
        }
        //请求参数支持
        else if (parameterToken != null && isParameterTokenSupportedForRequest(request)) {
            return parameterToken;
        }
        return null;
    }

    /**
     * Set if transport of access token using URI query parameter is supported. Defaults to {@code false}.
     * <p>
     * The spec recommends against using this mechanism for sending bearer tokens, and even goes as far as
     * stating that it was only included for completeness.
     *
     * @param allowUriQueryParameter if the URI query parameter is supported
     */
    public void setAllowUriQueryParameter(boolean allowUriQueryParameter) {
        this.allowUriQueryParameter = allowUriQueryParameter;
    }

    private static String resolveFromAuthorizationHeader(HttpHeaders headers) {
        String authorization = headers.getFirst(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer")) {
            Matcher matcher = authorizationPattern.matcher(authorization);

            if (!matcher.matches()) {
                BearerTokenError error = new BearerTokenError(BearerTokenErrorCodes.INVALID_TOKEN,
                        HttpStatus.BAD_REQUEST,
                        "Bearer token is malformed",
                        "https://tools.ietf.org/html/rfc6750#section-3.1");
                throw new OAuth2AuthenticationException(error);
            }

            return matcher.group("token");
        }
        return null;
    }

    private boolean isParameterTokenSupportedForRequest(ServerHttpRequest request) {
        return this.allowUriQueryParameter && HttpMethod.GET.equals(request.getMethod());
    }
}
