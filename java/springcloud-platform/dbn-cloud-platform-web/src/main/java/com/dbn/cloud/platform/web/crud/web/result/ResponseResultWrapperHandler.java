package com.dbn.cloud.platform.web.crud.web.result;


import com.alibaba.fastjson.JSON;

import com.dbn.cloud.platform.common.intercepter.ResponseResultWrapperInterceptor;
import com.dbn.cloud.platform.common.annotation.ResponseResultWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@RestControllerAdvice
public class ResponseResultWrapperHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        ResponseResultWrapper responseResultWrapper = (ResponseResultWrapper) request.getAttribute(ResponseResultWrapperInterceptor.RESPONSE_RESULT_WRAPPER_TRUE);
        return responseResultWrapper == null ? false : true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof Mono) {
            return ((Mono<?>) body)
                    .map(ResponseMessage::ok)
                    .switchIfEmpty(Mono.just(ResponseMessage.ok()));
        }
        if (body instanceof Flux) {
            return ((Flux<?>) body)
                    .collectList()
                    .map(ResponseMessage::ok)
                    .switchIfEmpty(Mono.just(ResponseMessage.ok()));
        }
        if (body instanceof String) {
            return JSON.toJSONString(ResponseMessage.ok(body));
        }
        return ResponseMessage.ok(body);
    }

}
