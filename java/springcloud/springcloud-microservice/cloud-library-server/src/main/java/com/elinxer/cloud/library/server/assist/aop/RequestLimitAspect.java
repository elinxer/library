package com.elinxer.cloud.library.server.assist.aop;

import com.dbn.cloud.platform.cache.redis.CacheService;
import com.dbn.cloud.platform.exception.AppException;
import com.dbn.cloud.platform.security.entity.LoginAssistUser;
import com.dbn.cloud.platform.security.utils.SysUserUtils;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Aspect//表示切面类
@Component
@Log4j2
public class RequestLimitAspect {


    @Resource
    CacheService cacheService;


    @Around(value = "@annotation(requestLimit)")
    @ResponseBody
    public Object requestLimit(ProceedingJoinPoint proceedingJoinPoint, RequestLimit requestLimit) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        LoginAssistUser loginAssistUser = SysUserUtils.getLoginAssistUser();

        StringBuilder redisKey = new StringBuilder();

        if (loginAssistUser == null) {
            if (request.getParameterMap().get("deviceNo") != null) {
                String methodName = proceedingJoinPoint.getSignature().getName();
                redisKey.append("vns:requestLimit:").append(methodName).append(":")
                        .append(request.getParameterMap().get("deviceNo")[0]).append("device");
            } else {
                throw new AppException("非法访问.");
            }
        } else {
            String userId = loginAssistUser.toString();
            String methodName = proceedingJoinPoint.getSignature().getName();
            redisKey.append("vns:requestLimit:").append(methodName).append(":").append(userId);
        }

        String requestCount2 = cacheService.getCache(redisKey.toString());
        int requestCount = requestCount2 == null ? 0 : Integer.parseInt(requestCount2);
        Object proceed = null;
        if (requestCount < requestLimit.count()) {
            cacheService.setCache(redisKey.toString(), String.valueOf(requestCount + 1), requestLimit.time());
            proceed = proceedingJoinPoint.proceed();
        } else {
            throw new AppException(-1 + "，操作频繁，请 " + cacheService.getExpireCache(redisKey.toString()) + " 秒后重试");
        }

        return proceed;
    }
}

