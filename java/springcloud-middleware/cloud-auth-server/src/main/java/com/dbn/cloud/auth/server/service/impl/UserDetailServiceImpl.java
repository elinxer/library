package com.dbn.cloud.auth.server.service.impl;

import com.dbn.cloud.auth.server.feign.AssistAuthFeign;
import com.dbn.cloud.auth.server.feign.SysAuthFeign;
import com.dbn.cloud.auth.server.service.AuthConstant;
import com.dbn.cloud.platform.core.utils.StringUtils;
import com.dbn.cloud.platform.exception.AppException;
import com.dbn.cloud.platform.security.entity.LoginAppUser;
import com.dbn.cloud.platform.security.entity.LoginAssistUser;
import com.dbn.cloud.platform.web.crud.web.result.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@SuppressWarnings("all")
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SysAuthFeign sysAuthFeign;

    @Autowired
    private AssistAuthFeign assistAuthFeign;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {

            if (username.startsWith(AuthConstant.LOGIN_SAAS_PRE)) {
                LoginAppUser loginAppUser = null;

                // 云平台登录入口
                loginAppUser = sysAuthFeign.findByUsername(StringUtils.substringAfter(username, AuthConstant.LOGIN_SAAS_PRE));

                if (loginAppUser == null) {
                    throw new UsernameNotFoundException("用户不存在");
                } else if (StringUtils.isBlank(loginAppUser.getUsername())) {
                    throw new ProviderNotFoundException("系统繁忙中");
                } else if (!loginAppUser.isEnabled()) {
                    throw new DisabledException("用户已作废");
                }
                return loginAppUser;
            }

            if (username.startsWith(AuthConstant.LOGIN_ASSIST_PRE)) {
                LoginAssistUser loginAssistUser = null;
                ResponseMessage<LoginAssistUser> responseMessage = assistAuthFeign.getUserInfo(StringUtils.substringAfter(username, AuthConstant.LOGIN_ASSIST_PRE));
                loginAssistUser = responseMessage.getResult();
                //log.info(loginAssistUser.toString());
                //loginAssistUser.setPassword(passwordEncoder.encode("123456"));
                return loginAssistUser;
            }

            if (username.startsWith(AuthConstant.LOGIN_ASSIST_MOBILE_PRE)) {
                LoginAssistUser loginAssistUser = null;
                ResponseMessage<LoginAssistUser> responseMessage = assistAuthFeign.getUserInfoByPhone(StringUtils.substringAfter(username, AuthConstant.LOGIN_ASSIST_MOBILE_PRE));
                loginAssistUser = responseMessage.getResult();

                if (loginAssistUser == null) {
                    throw new UsernameNotFoundException("用户不存在");
                } else if (!loginAssistUser.isEnabled()) {
                    throw new DisabledException("用户已作废");
                }

                loginAssistUser.setPassword(passwordEncoder.encode("123456"));
                return loginAssistUser;
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ProviderNotFoundException("认证系统繁忙，请稍后再试！");
        }

        return null;
    }

}
