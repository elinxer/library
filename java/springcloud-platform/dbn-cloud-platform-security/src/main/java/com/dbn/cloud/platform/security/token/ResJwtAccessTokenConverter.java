package com.dbn.cloud.platform.security.token;


import com.baomidou.mybatisplus.core.toolkit.BeanUtils;
import com.dbn.cloud.platform.security.entity.LoginAppUser;
import com.dbn.cloud.platform.security.entity.SysRole;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("all")
public class ResJwtAccessTokenConverter extends JwtAccessTokenConverter {


    public ResJwtAccessTokenConverter() {
        super();
        super.setAccessTokenConverter(new JwtUserAuthenticationConverter());
    }

    public class JwtUserAuthenticationConverter extends DefaultAccessTokenConverter {


        public JwtUserAuthenticationConverter() {
            super.setUserTokenConverter(new JWTfaultUserAuthenticationConverter());
        }


        public class JWTfaultUserAuthenticationConverter extends DefaultUserAuthenticationConverter {

            public Authentication extractAuthentication(Map<String, ?> map) {

                if (map.containsKey(USERNAME)) {
                    Object principal = map.get(USERNAME);
//					Collection<? extends GrantedAuthority> authorities = getAuthorities(map);
                    LoginAppUser loginUser = new LoginAppUser();
                    if (principal instanceof Map) {

                        //old loginUser = BeanUtil.mapToBean((Map) principal, LoginAppUser.class, true);
                        loginUser = BeanUtils.mapToBean((Map) principal, LoginAppUser.class);
                        Set<SysRole> roles = new HashSet<>();

                        for (Iterator<SysRole> it = loginUser.getSysRoles().iterator(); it.hasNext(); ) {
                            //old SysRole role =  BeanUtil.mapToBean((Map) it.next() , SysRole.class, false);
                            SysRole role = BeanUtils.mapToBean((Map) it.next(), SysRole.class);

                            roles.add(role);
                        }
                        loginUser.setSysRoles(roles);
                    }
                    return new UsernamePasswordAuthenticationToken(loginUser, "N/A", loginUser.getAuthorities());
                }


                return null;
            }

        }

    }
}
