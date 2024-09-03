package com.dbn.cloud.platform.feign.parser;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;

import javax.servlet.http.HttpServletRequest;

public class HeaderRequestOriginParser implements RequestOriginParser {
    /**
     * 请求头获取allow
     */
    private static final String ALLOW = "Allow";


    @Override
    public String parseOrigin(HttpServletRequest request) {
        return request.getHeader(ALLOW);
    }
}
