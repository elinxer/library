package com.dbn.cloud.auth.server.feign;


import com.dbn.cloud.auth.server.feign.fallback.AssistAuthFallbackFactory;
import com.dbn.cloud.platform.exception.feign.FeignExceptionConfig;
import com.dbn.cloud.platform.security.entity.LoginAssistUser;
import com.dbn.cloud.platform.web.crud.web.result.ResponseMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 调用用户中心中的userdetail对象，用户oauth中的登录
 * 获取的用户与页面输入的密码 进行BCryptPasswordEncoder匹配
 */
@FeignClient(value = "cloud-library-server", configuration = FeignExceptionConfig.class, fallbackFactory = AssistAuthFallbackFactory.class, decode404 = true)
public interface AssistAuthFeign {

    @GetMapping(value = "/v1/assist/user/getUserInfo", params = "username")
    ResponseMessage<LoginAssistUser> getUserInfo(@RequestParam("username") String username);

    @GetMapping(value = "/v1/assist/user/getUserInfoByPhone", params = "phone")
    ResponseMessage<LoginAssistUser> getUserInfoByPhone(@RequestParam("phone") String phone);

}
