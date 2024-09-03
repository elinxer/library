package com.dbn.cloud.auth.uua.config;


import com.dbn.cloud.auth.uua.constant.SecurityConstant;
import com.dbn.cloud.auth.uua.handle.OauthLogoutHandler;
import com.dbn.cloud.auth.uua.provider.SmsCodeAuthenticationProvider;
import com.dbn.cloud.platform.security.config.PermitUrlProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

/**
 * spring security配置
 * <p>
 * 在WebSecurityConfigurerAdapter不拦截oauth要开放的资源
 */
@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(PermitUrlProperties.class)
@SuppressWarnings("all")
//@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;
    @Autowired(required = false)
    private AuthenticationEntryPoint authenticationEntryPoint; // 自定义异常处理端口 默认空
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private OauthLogoutHandler oauthLogoutHandler;
    @Autowired
    private PermitUrlProperties permitUrlProperties;
    @Autowired
    private SmsCodeAuthenticationProvider smsCodeAuthenticationProvider;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs", "/v3/api-docs",
                "/configuration/ui", "/swagger-resources", "/configuration/security",
                "/swagger-ui.html", "/webjars/**", "/doc.html", "/login.html");
        web.ignoring().antMatchers("/js/**");
        web.ignoring().antMatchers("/css/**");
        web.ignoring().antMatchers("/health");
        // 忽略登录界面
        web.ignoring().antMatchers("/login.html");
        web.ignoring().antMatchers("/index.html");
        web.ignoring().antMatchers("/oauth/user/token");
        //web.ignoring().antMatchers("/oauth/refresh/token");
        web.ignoring().antMatchers("/oauth/client/token");
        web.ignoring().antMatchers("/validata/code/**");
        web.ignoring().antMatchers("/sms/**");
        web.ignoring().antMatchers("/authentication/**");
        web.ignoring().antMatchers("/ws/**");

        web.ignoring().antMatchers("/oauth/remove/token");

        web.ignoring().antMatchers(permitUrlProperties.getIgnored());
    }

    /**
     * 认证管理
     *
     * @return 认证管理对象
     * @throws Exceptionn 认证异常信息
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        //	http.cors();
        http.authorizeRequests()
                .anyRequest().authenticated();
        http.formLogin().loginPage(SecurityConstant.LOGIN_PAGE).loginProcessingUrl(SecurityConstant.LOGIN_PROCESSING_URL)
                .successHandler(authenticationSuccessHandler).failureHandler(authenticationFailureHandler);
        // 基于密码 等模式可以无session,不支持授权码模式
        if (authenticationEntryPoint != null) {
            http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        } else {
            // 授权码模式单独处理，需要session的支持，此模式可以支持所有oauth2的认证
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
        }

        http.logout().logoutSuccessUrl(SecurityConstant.LOGIN_PAGE)
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                .addLogoutHandler(oauthLogoutHandler).clearAuthentication(true);

        //注册到AuthenticationManager中去 增加支持SmsCodeAuthenticationToken
        http.authenticationProvider(smsCodeAuthenticationProvider);

        // http.logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler);
        // 解决不允许显示在iframe的问题
        http.headers().frameOptions().disable();
        http.headers().cacheControl();

    }

    /**
     * 全局用户信息
     *
     * @param auth 认证管理
     * @throws Exception 用户认证异常信息
     */
    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }


}
