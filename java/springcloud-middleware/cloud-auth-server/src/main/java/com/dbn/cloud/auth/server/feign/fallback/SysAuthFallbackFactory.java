package com.dbn.cloud.auth.server.feign.fallback;


import com.dbn.cloud.auth.server.feign.SysAuthFeign;
import com.dbn.cloud.platform.security.entity.LoginAppUser;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SysAuthFallbackFactory implements FallbackFactory<SysAuthFeign> {
    @Override
    public SysAuthFeign create(Throwable throwable) {
        return mobile -> new LoginAppUser();
    }
}
