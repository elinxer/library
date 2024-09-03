package com.dbn.cloud.platform.security.utils;


import com.baomidou.mybatisplus.core.toolkit.BeanUtils;
import com.dbn.cloud.platform.cache.redis.serializer.RedisObjectSerializer;
import com.dbn.cloud.platform.common.utils.SpringUtils;
import com.dbn.cloud.platform.core.utils.StringUtils;
import com.dbn.cloud.platform.security.constant.UaaConstant;
import com.dbn.cloud.platform.security.entity.LoginAppUser;
import com.dbn.cloud.platform.security.entity.LoginAssistUser;
import com.dbn.cloud.platform.security.entity.SysRole;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 获取登陆的 LoginAppUser
 *
 * @author elinx
 * @version 1.0.0
 */
@SuppressWarnings("all")
public class SysUserUtils {


    @SuppressWarnings("rawtypes")
    public static LoginAppUser getLoginAppUser() {

        // 当OAuth2AuthenticationProcessingFilter设置当前登录时，直接返回
        // 强认证时处理
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2Authentication) {
            OAuth2Authentication oAuth2Auth = (OAuth2Authentication) authentication;
            authentication = oAuth2Auth.getUserAuthentication();

            if (authentication instanceof UsernamePasswordAuthenticationToken) {
                UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) authentication;

                if (authenticationToken.getPrincipal() instanceof LoginAppUser) {
                    return (LoginAppUser) authenticationToken.getPrincipal();
                } else if (authenticationToken.getPrincipal() instanceof Map) {
                    LoginAppUser loginAppUser = BeanUtils.mapToBean((Map) authenticationToken.getPrincipal(), LoginAppUser.class);
                    //old LoginAppUser loginAppUser = BeanUtil.mapToBean((Map) authenticationToken.getPrincipal(), LoginAppUser.class, true);
                    Set<SysRole> roles = new HashSet<>();
                    if (!CollectionUtils.isEmpty(loginAppUser.getSysRoles())) {
                        // old if (CollectionUtil.isNotEmpty(loginAppUser.getSysRoles())) {
                        for (Iterator<SysRole> it = loginAppUser.getSysRoles().iterator(); it.hasNext(); ) {
                            //old SysRole role =  BeanUtil.mapToBean((Map) it.next() , SysRole.class, false);
                            SysRole role = BeanUtils.mapToBean((Map) it.next(), SysRole.class);
                            roles.add(role);
                        }
                    }
                    loginAppUser.setSysRoles(roles);
                    return loginAppUser;
                }
            } else if (authentication instanceof PreAuthenticatedAuthenticationToken) {
                // 刷新token方式
                PreAuthenticatedAuthenticationToken authenticationToken = (PreAuthenticatedAuthenticationToken) authentication;
                return (LoginAppUser) authenticationToken.getPrincipal();
            }
        }

        // 弱认证处理，当内部服务，不带token时，内部服务
        String accessToken = TokenUtils.getToken();
        if (StringUtils.isNotEmpty(accessToken)) {
            RedisTemplate stringRedisTemplate = SpringUtils.getBean("redisTemplate", RedisTemplate.class);

            RedisTemplate redisTemplateTmp = new RedisTemplate();
            redisTemplateTmp.setConnectionFactory(stringRedisTemplate.getConnectionFactory());
            RedisSerializer stringSerializer = new StringRedisSerializer();
            redisTemplateTmp.setKeySerializer(stringSerializer);
            redisTemplateTmp.setHashKeySerializer(stringSerializer);
            RedisSerializer redisObjectSerializer = new RedisObjectSerializer();
            redisTemplateTmp.setValueSerializer(redisObjectSerializer); // value的序列化类型
            redisTemplateTmp.setHashValueSerializer(redisObjectSerializer);
            redisTemplateTmp.afterPropertiesSet();

            String cacheKey = UaaConstant.TOKEN + ":" + accessToken;
            cacheKey = "oauth2:" + UaaConstant.TOKEN + ":" + accessToken;

            LoginAppUser loginAppUser = (LoginAppUser) redisTemplateTmp.opsForValue().get(cacheKey);
            if (loginAppUser != null) {
                return loginAppUser;
            }
        }

        return null;
    }

    @SuppressWarnings("rawtypes")
    public static LoginAssistUser getLoginAssistUser() {
        // 弱认证处理，当内部服务，不带token时，内部服务
        String accessToken = TokenUtils.getToken();
        if (StringUtils.isNotEmpty(accessToken)) {
            RedisTemplate stringRedisTemplate = SpringUtils.getBean("redisTemplate", RedisTemplate.class);

            RedisTemplate redisTemplateTmp = new RedisTemplate();
            redisTemplateTmp.setConnectionFactory(stringRedisTemplate.getConnectionFactory());
            RedisSerializer stringSerializer = new StringRedisSerializer();
            redisTemplateTmp.setKeySerializer(stringSerializer);
            redisTemplateTmp.setHashKeySerializer(stringSerializer);
            RedisSerializer redisObjectSerializer = new RedisObjectSerializer();
            redisTemplateTmp.setValueSerializer(redisObjectSerializer); // value的序列化类型
            redisTemplateTmp.setHashValueSerializer(redisObjectSerializer);
            redisTemplateTmp.afterPropertiesSet();

            String cacheKey = UaaConstant.TOKEN + ":" + accessToken;
            cacheKey = "oauth2:" + UaaConstant.TOKEN + ":" + accessToken;

            LoginAssistUser loginAppUser = (LoginAssistUser) redisTemplateTmp.opsForValue().get(cacheKey);
            if (loginAppUser != null) {
                return loginAppUser;
            }
        }

        return null;
    }
}
