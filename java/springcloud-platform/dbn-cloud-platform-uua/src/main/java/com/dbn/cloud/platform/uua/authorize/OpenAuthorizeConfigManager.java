package com.dbn.cloud.platform.uua.authorize;

import com.dbn.cloud.platform.common.constant.ServiceConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@SuppressWarnings("all")
public class OpenAuthorizeConfigManager implements AuthorizeConfigManager {

    @Autowired
    private List<AuthorizeConfigProvider> authorizeConfigProviders;


    @Value("${spring.application.name:}")
    private String applicationName;

    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {

        //设置访问
        for (AuthorizeConfigProvider authorizeConfigProvider : authorizeConfigProviders) {
            authorizeConfigProvider.config(config);
        }
        int flag = 0;
        if (ServiceConst.CLOUD_GATEWAY_SERVICE.equalsIgnoreCase(applicationName)) {
            //网关API权限
            flag = 1;
        } else if (ServiceConst.CLOUD_AUTH_SERVICE.equalsIgnoreCase(applicationName)) {
            //认证中心
            flag = 2;
        }
        switch (flag) {
            case 1:
                config.anyRequest().authenticated();
                break;
            case 2:
                // 认证中心 强制校验token
                config.anyRequest().authenticated();
                break;
            default:
                config.anyRequest().permitAll();
        }

    }

}
