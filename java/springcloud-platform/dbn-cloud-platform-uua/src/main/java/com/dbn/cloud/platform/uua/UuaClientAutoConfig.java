package com.dbn.cloud.platform.uua;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.dbn.cloud.platform.common.config.GlobalFeignConfig;
import com.dbn.cloud.platform.security.config.FeignInterceptorConfig;
import com.dbn.cloud.platform.security.config.PermitUrlProperties;
import com.dbn.cloud.platform.security.config.RestTemplateConfig;
import com.dbn.cloud.platform.uua.authorize.AuthorizeConfigManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
@Configuration
@EnableResourceServer
@SuppressWarnings("all")
@AutoConfigureAfter(TokenStore.class)
@EnableConfigurationProperties(PermitUrlProperties.class)
@Import({RestTemplateConfig.class, FeignInterceptorConfig.class})
@EnableFeignClients(defaultConfiguration = GlobalFeignConfig.class)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class UuaClientAutoConfig extends ResourceServerConfigurerAdapter {
    // 对应oauth_client_details的 resource_ids字段 如果表中有数据
    // client_id只能访问响应resource_ids的资源服务器
    private static final String DEMO_RESOURCE_ID = "";

    @Resource
    private ObjectMapper objectMapper; //springmvc启动时自动装配json处理类

    @Autowired(required = false)
    private TokenStore tokenStore;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private AuthorizeConfigManager authorizeConfigManager;

    @Autowired
    private OAuth2WebSecurityExpressionHandler expressionHandler;
    @Autowired
    private OAuth2AccessDeniedHandler oAuth2AccessDeniedHandler;

    @Autowired
    private PermitUrlProperties permitUrlProperties;


    public void configure(WebSecurity web) throws Exception {

        web.ignoring().antMatchers(permitUrlProperties.getIgnored());
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        if (tokenStore != null) {
            resources.tokenStore(tokenStore);
        }
        resources.stateless(true);
        // 自定义异常处理端口
        resources.authenticationEntryPoint(authenticationEntryPoint);
        resources.expressionHandler(expressionHandler);
        resources.accessDeniedHandler(oAuth2AccessDeniedHandler);


    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/ws/**").permitAll();

        http.csrf().disable();
        //http.cors();

        http.headers().frameOptions().disable();
        authorizeConfigManager.config(http.authorizeRequests());

    }

}
