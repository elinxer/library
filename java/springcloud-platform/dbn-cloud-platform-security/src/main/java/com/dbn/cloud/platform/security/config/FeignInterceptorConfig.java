package com.dbn.cloud.platform.security.config;


import com.dbn.cloud.platform.common.constant.TraceConstant;
import com.dbn.cloud.platform.core.utils.StringUtils;
import com.dbn.cloud.platform.security.constant.UaaConstant;
import com.dbn.cloud.platform.security.utils.TokenUtils;
import feign.RequestInterceptor;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * feign拦截
 *
 * @author elinx
 * @version 1.0
 */
@Configuration
public class FeignInterceptorConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        RequestInterceptor requestInterceptor = template -> {
            //传递token
            //使用feign client访问别的微服务时，将accessToken header
            //config.anyRequest().permitAll() 非强制校验token
            if (StringUtils.isNotBlank(TokenUtils.getToken())) {
                template.header(UaaConstant.TOKEN_HEADER, TokenUtils.getToken());
//            	template.header(UaaConstant.AUTHORIZATION,  OAuth2AccessToken.BEARER_TYPE  +  " "  +  TokenUtil.getToken() );
            }
            //传递traceId
            String traceId = StringUtils.isNotBlank(MDC.get(TraceConstant.LOG_TRACE_ID)) ? MDC.get(TraceConstant.LOG_TRACE_ID) : MDC.get(TraceConstant.LOG_B3_TRACEID);
            if (StringUtils.isNotBlank(traceId)) {
                template.header(TraceConstant.HTTP_HEADER_TRACE_ID, traceId);
            }


        };

        return requestInterceptor;
    }
}
