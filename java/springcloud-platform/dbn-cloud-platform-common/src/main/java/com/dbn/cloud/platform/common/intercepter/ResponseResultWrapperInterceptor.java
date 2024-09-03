package com.dbn.cloud.platform.common.intercepter;

import com.dbn.cloud.platform.common.annotation.ResponseResultWrapper;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author elinx
 */
public class ResponseResultWrapperInterceptor implements HandlerInterceptor {

    /**
     * 标记名称
     */
    public static final String RESPONSE_RESULT_WRAPPER_TRUE = "RESPONSE_RESULT_WRAPPER_TRUE";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //请求方法
        if (handler instanceof HandlerMethod) {
            final HandlerMethod handlerMethod = (HandlerMethod) handler;
            //  拿到的是方法所在的controller类的class类
            final Class<?> clazz = handlerMethod.getBeanType();
            // 拿到method对象
            final Method method = handlerMethod.getMethod();
            // 判断是否在类对象上面加了注解
            if (clazz.isAnnotationPresent(ResponseResultWrapper.class)) {
                // 设置此请求返回体
                request.setAttribute(RESPONSE_RESULT_WRAPPER_TRUE,
                        clazz.getAnnotation(ResponseResultWrapper.class));
            } else if (method.isAnnotationPresent(ResponseResultWrapper.class)) {
                // 设置此请求返回体
                request.setAttribute(RESPONSE_RESULT_WRAPPER_TRUE,
                        method.getAnnotation(ResponseResultWrapper.class));
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
