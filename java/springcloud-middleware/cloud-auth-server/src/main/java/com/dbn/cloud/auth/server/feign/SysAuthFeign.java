package com.dbn.cloud.auth.server.feign;


import com.dbn.cloud.auth.server.feign.fallback.SysAuthFallbackFactory;
import com.dbn.cloud.platform.exception.feign.FeignExceptionConfig;
import com.dbn.cloud.platform.security.entity.LoginAppUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 调用用户中心中的userdetail对象，用户oauth中的登录
 * 获取的用户与页面输入的密码 进行BCryptPasswordEncoder匹配
 */
@FeignClient(value = "dbn-cloud-admin", configuration = FeignExceptionConfig.class, fallbackFactory = SysAuthFallbackFactory.class, decode404 = true)
public interface SysAuthFeign {

    /**
     * feign SAAS平台登录接口
     *
     * @param username 用户名
     * @return LoginAppUser
     */
    @GetMapping(value = "v1/sys-auth/login", params = "username")
    LoginAppUser findByUsername(@RequestParam("username") String username);


}
