package com.dbn.cloud.gateway.server.filter;

import cn.hutool.core.util.IdUtil;
import com.dbn.cloud.platform.common.constant.ContextConstant;
import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 生成日志链路追踪id
 */
@Component
public class TraceFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //链路追踪id
        String traceId = IdUtil.fastSimpleUUID();
        MDC.put(ContextConstant.LOG_TRACE_ID, traceId);
        ServerHttpRequest serverHttpRequest = exchange.getRequest().mutate()
                .headers(h -> h.add(ContextConstant.TRACE_ID_HEADER, traceId))
                .build();

        ServerWebExchange build = exchange.mutate().request(serverHttpRequest).build();
        return chain.filter(build);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
