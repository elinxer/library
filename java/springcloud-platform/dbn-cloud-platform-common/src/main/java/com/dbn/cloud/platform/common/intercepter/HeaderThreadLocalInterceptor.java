package com.dbn.cloud.platform.common.intercepter;


import cn.hutool.core.util.StrUtil;
import com.dbn.cloud.platform.common.constant.ContextConstant;
import com.dbn.cloud.platform.common.utils.ContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.dbn.cloud.platform.common.utils.WebUtils.getHeader;


/**
 * @author elinx
 * <p>
 * 拦截器：
 * 将请求头数据，封装到BaseContextHandler(ThreadLocal)
 * <p>
 * 该拦截器要优先于系统中其他的业务拦截器
 * <p>
 */
@Slf4j
public class HeaderThreadLocalInterceptor implements AsyncHandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        if (!ContextUtils.getBoot()) {
            ContextUtils.setUserId(getHeader(request, ContextConstant.KEY_USER_ID));
            ContextUtils.setAccount(getHeader(request, ContextConstant.KEY_ACCOUNT));
            ContextUtils.setName(getHeader(request, ContextConstant.KEY_NAME));
            ContextUtils.setTenant(getHeader(request, ContextConstant.KEY_TENANT));
            String traceId = request.getHeader(ContextConstant.TRACE_ID_HEADER);
            MDC.put(ContextConstant.LOG_TRACE_ID, StrUtil.isEmpty(traceId) ? StrUtil.EMPTY : traceId);
            MDC.put(ContextConstant.KEY_TENANT, getHeader(request, ContextConstant.KEY_TENANT));
            MDC.put(ContextConstant.KEY_USER_ID, getHeader(request, ContextConstant.KEY_USER_ID));
        }
        // cloud
        ContextUtils.setGrayVersion(getHeader(request, ContextConstant.GRAY_VERSION));
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ContextUtils.remove();
    }
}
