package com.dbn.cloud.platform.security.interceptor;

import com.dbn.cloud.platform.common.constant.TraceConstant;
import com.dbn.cloud.platform.core.utils.StringUtils;
import com.dbn.cloud.platform.security.constant.UaaConstant;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 传递token与traceId
 *
 * @author elinx
 * @date 2021-08-10
 */
public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest httpRequest = attributes.getRequest();
        String header = httpRequest.getHeader(UaaConstant.AUTHORIZATION);

        String token = StringUtils.isBlank(StringUtils.substringAfter(header, OAuth2AccessToken.BEARER_TYPE + " ")) ? httpRequest.getParameter(OAuth2AccessToken.ACCESS_TOKEN) : StringUtils.substringAfter(header, OAuth2AccessToken.BEARER_TYPE + " ");
        token = StringUtils.isBlank(httpRequest.getHeader(UaaConstant.TOKEN_HEADER)) ? token : httpRequest.getHeader(UaaConstant.TOKEN_HEADER);
        //传递token
        HttpHeaders headers = request.getHeaders();
        headers.add(UaaConstant.TOKEN_HEADER, token);
        //传递traceId
        String traceId = StringUtils.isNotEmpty(MDC.get(TraceConstant.LOG_TRACE_ID)) ? MDC.get(TraceConstant.LOG_TRACE_ID) : MDC.get(TraceConstant.LOG_B3_TRACEID);
        if (StringUtils.isNotEmpty(traceId)) {
            headers.add(TraceConstant.HTTP_HEADER_TRACE_ID, traceId);
        }
        // 保证请求继续执行
        return execution.execute(request, body);
    }
}
