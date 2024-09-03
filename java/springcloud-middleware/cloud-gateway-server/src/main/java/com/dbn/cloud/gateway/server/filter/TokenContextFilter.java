package com.dbn.cloud.gateway.server.filter;

import cn.hutool.core.util.StrUtil;
import com.dbn.cloud.platform.common.constant.ContextConstant;
import com.dbn.cloud.platform.common.utils.ContextUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * 过滤器
 */
//@Component
//@Slf4j
//@RequiredArgsConstructor
public class TokenContextFilter implements GlobalFilter, Ordered {
    protected boolean isDev(String token) {
        return false;
    }

    @Override
    public int getOrder() {
        return -1000;
    }

    protected boolean isIgnoreToken(String path) {
        return false;
    }

    protected boolean isIgnoreTenant(String path) {
        return false;
    }

    protected String getHeader(String headerName, ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String token = StrUtil.EMPTY;
        if (headers == null || headers.isEmpty()) {
            return token;
        }

        token = headers.getFirst(headerName);
        if (StrUtil.isNotBlank(token)) {
            return token;
        }
        return request.getQueryParams().getFirst(headerName);
    }


    private Mono<Void> parseToken(ServerWebExchange exchange, WebFilterChain chain) {
        return null;
    }

    private void parseClient(ServerHttpRequest request, ServerHttpRequest.Builder mutate) {
    }

    private void parseTenant(ServerHttpRequest request, ServerHttpRequest.Builder mutate) {
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        ServerHttpRequest.Builder mutate = request.mutate();
        ContextUtils.setGrayVersion(getHeader(ContextConstant.GRAY_VERSION, request));
        ServerHttpRequest build = mutate.build();
        return chain.filter(exchange.mutate().request(build).build());
    }
}
