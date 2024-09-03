package com.dbn.cloud.platform.common.config;

import com.dbn.cloud.platform.common.intercepter.HeaderThreadLocalInterceptor;
import com.dbn.cloud.platform.common.intercepter.ResponseResultWrapperInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * MVC 全局配置
 *
 * @author elinx
 * @version 1.0
 */
@AllArgsConstructor
public class GlobalMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //处理ResponseResultWrapper注解的
        registry.addInterceptor(new ResponseResultWrapperInterceptor()).addPathPatterns("/**");

        registry.addInterceptor(new HeaderThreadLocalInterceptor())
                .addPathPatterns("/**")
                .order(-20);
    }
}
