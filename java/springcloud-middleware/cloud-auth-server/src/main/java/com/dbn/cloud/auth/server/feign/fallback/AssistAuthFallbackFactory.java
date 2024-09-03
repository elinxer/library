package com.dbn.cloud.auth.server.feign.fallback;


import com.dbn.cloud.auth.server.feign.AssistAuthFeign;
import com.dbn.cloud.platform.security.entity.LoginAssistUser;
import com.dbn.cloud.platform.web.crud.web.result.ResponseMessage;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AssistAuthFallbackFactory implements FallbackFactory<AssistAuthFeign> {
    @Override
    public AssistAuthFeign create(Throwable throwable) {
        return new AssistAuthFeign() {
            @Override
            public ResponseMessage<LoginAssistUser> getUserInfo(String username) {
                return ResponseMessage.ok(new LoginAssistUser());
            }

            @Override
            public ResponseMessage<LoginAssistUser> getUserInfoByPhone(String phone) {
                return ResponseMessage.ok(new LoginAssistUser());
            }
        };
    }
}
