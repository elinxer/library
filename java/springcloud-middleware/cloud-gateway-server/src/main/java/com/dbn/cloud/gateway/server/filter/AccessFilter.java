package com.dbn.cloud.gateway.server.filter;

import com.alibaba.fastjson.JSONObject;
import com.dbn.cloud.platform.security.config.PermitUrlProperties;
import com.dbn.cloud.platform.security.utils.TokenUtils;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

/**
 * oauth校验
 */
@Component
@SuppressWarnings("all")
@EnableConfigurationProperties(PermitUrlProperties.class)
public class AccessFilter implements GlobalFilter, Ordered {

    // url匹配器
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Autowired
    private TokenStore tokenStore;

    @Resource
    private PermitUrlProperties permitUrlProperties;

    @Value("${security.oauth2.token.store.type:}")
    private String tokenType;

    @Value("${security.oauth2.enable:true}")
    private boolean tokenEnable;


    @Override
    public int getOrder() {
        return -500;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (!tokenEnable) {
            ServerHttpRequest req = exchange.getRequest();
            HttpHeaders headers = req.getHeaders();
            ServerHttpRequest.Builder requestBuilder = req.mutate();
            requestBuilder.headers(k -> k.remove("Authorization"));
            return chain.filter(exchange);
        } else {
            try {
                if (!"redis".equals(tokenType)) {
                    return chain.filter(exchange);
                }
                String accessToken = TokenUtils.extractToken(exchange.getRequest());
                boolean flag = false;
                flag = Lists.newArrayList(permitUrlProperties.getIgnored()).stream().anyMatch((item) -> {
                    try {
                        return pathMatcher.match(item, exchange.getRequest().getPath().value());
                    } catch (Exception e) {
                        return false;
                    }
                });
                if (flag) {
                    return chain.filter(exchange);
                } else {
                    OAuth2Authentication oauth2Authentication = tokenStore.readAuthentication(accessToken);
                    if (oauth2Authentication != null) {
                        return chain.filter(exchange);
                    } else {
                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        ServerHttpResponse response = exchange.getResponse();
                        JSONObject message = new JSONObject();
                        message.put("code", 401);
                        message.put("msg", "Unauthorized.");
                        byte[] bits = message.toJSONString().getBytes(StandardCharsets.UTF_8);
                        DataBuffer buffer = response.bufferFactory().wrap(bits);
                        response.setStatusCode(HttpStatus.UNAUTHORIZED);
                        // 指定编码，否则在浏览器中会中文乱码
                        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
                        return response.writeWith(Mono.just(buffer));
                    }
                }
            } catch (Exception e) {
                return chain.filter(exchange);
            }
        }
    }

}
