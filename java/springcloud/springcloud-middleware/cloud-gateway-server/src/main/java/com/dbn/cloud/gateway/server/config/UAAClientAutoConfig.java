package com.dbn.cloud.gateway.server.config;

import com.dbn.cloud.gateway.server.handle.ResAuthenticationFailureHandler;
import com.dbn.cloud.gateway.server.handle.ResAuthenticationSuccessHandler;
import com.dbn.cloud.gateway.server.token.AuthorizeConfigManager;
import com.dbn.cloud.gateway.server.token.TokenAuthenticationConverter;
import com.dbn.cloud.gateway.server.token.TokenAuthenticationManager;
import com.dbn.cloud.gateway.server.util.ResponseUtils;
import com.dbn.cloud.gateway.server.util.ResultCode;
import com.dbn.cloud.platform.security.config.PermitUrlProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.security.reactive.EndpointRequest;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Mono;

/**
 * 资源服务器UAAClientAutoConfig
 */

@Configuration
@SuppressWarnings("all")
@EnableConfigurationProperties(PermitUrlProperties.class)
@EnableWebFluxSecurity
public class UAAClientAutoConfig {
    @Autowired
    private PermitUrlProperties permitUrlProperties;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private AuthorizeConfigManager authorizeConfigManager;

    @Value("${security.oauth2.enable:true}")
    private boolean tokenEnable;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        //认证处理器
        ReactiveAuthenticationManager tokenAuthenticationManager = new TokenAuthenticationManager(tokenStore);
        //构建Bearer Token
        //请求参数强制加上 Authorization BEARER token
        http.addFilterAt((WebFilter) (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if (request.getQueryParams().getFirst("access_token") != null) {
                exchange.getRequest().mutate().headers(httpHeaders ->
                        httpHeaders.add(
                                "Authorization",
                                OAuth2AccessToken.BEARER_TYPE + " " + request.getQueryParams().getFirst("access_token"))
                );
            }
            return chain.filter(exchange);
        }, SecurityWebFiltersOrder.FIRST);

        //身份认证
        if (tokenEnable) {
            AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(tokenAuthenticationManager);
            authenticationWebFilter.setAuthenticationFailureHandler(new ResAuthenticationFailureHandler()); //登陆验证失败
            authenticationWebFilter.setAuthenticationSuccessHandler(new ResAuthenticationSuccessHandler()); //认证成功
            //token转换器
            TokenAuthenticationConverter tokenAuthenticationConverter = new TokenAuthenticationConverter();
            tokenAuthenticationConverter.setAllowUriQueryParameter(true);
            authenticationWebFilter.setServerAuthenticationConverter(tokenAuthenticationConverter);
            http.addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION);
        }

        ServerHttpSecurity.AuthorizeExchangeSpec authorizeExchange = http.authorizeExchange();
        authorizeExchange.matchers(EndpointRequest.toAnyEndpoint()).permitAll(); //无需进行权限过滤的请求路径
        authorizeExchange.pathMatchers(permitUrlProperties.getIgnored()).permitAll();//无需进行权限过滤的请求路径
        if (tokenEnable) {
            authorizeExchange
                    .pathMatchers(HttpMethod.OPTIONS).permitAll()    //option 请求默认放行
                    .anyExchange().access(authorizeConfigManager)    // 应用api权限控制
//                    .anyExchange().authenticated()                    //token 有效性控制
                    .and()
                    .exceptionHandling()
                    .accessDeniedHandler(accessDeniedHandler())
                    .authenticationEntryPoint(authenticationEntryPoint())
                    .and()
                    .headers()
                    .frameOptions()
                    .disable()
                    .and()
                    .httpBasic().disable()
                    .csrf().disable();
        } else {
            authorizeExchange
                    .pathMatchers(HttpMethod.OPTIONS).permitAll()    //option 请求默认放行
                    //.anyExchange().access(authorizeConfigManager)    // 应用api权限控制
                    .anyExchange().permitAll()                    //token 有效性控制
                    .and()
                    .exceptionHandling()
                    .accessDeniedHandler(accessDeniedHandler())
                    .authenticationEntryPoint(authenticationEntryPoint())
                    .and()
                    .headers()
                    .frameOptions()
                    .disable()
                    .and()
                    .httpBasic().disable()
                    .csrf().disable();
        }
        return http.build();
    }

    /**
     * 自定义未授权响应
     */
    @Bean
    ServerAccessDeniedHandler accessDeniedHandler() {
        return (exchange, denied) -> {
            Mono<Void> mono = Mono.defer(() -> Mono.just(exchange.getResponse()))
                    .flatMap(response -> ResponseUtils.writeErrorInfo(response, ResultCode.ACCESS_UNAUTHORIZED));
            return mono;
        };
    }

    /**
     * token无效或者已过期自定义响应
     */
    @Bean
    ServerAuthenticationEntryPoint authenticationEntryPoint() {
        return (exchange, e) -> {
            Mono<Void> mono = Mono.defer(() -> Mono.just(exchange.getResponse()))
                    .flatMap(response -> ResponseUtils.writeErrorInfo(response, ResultCode.TOKEN_INVALID_OR_EXPIRED));
            return mono;
        };
    }
}
