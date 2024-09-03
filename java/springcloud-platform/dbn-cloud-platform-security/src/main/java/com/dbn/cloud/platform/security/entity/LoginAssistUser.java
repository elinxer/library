package com.dbn.cloud.platform.security.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 辅助驾驶当前登录用户
 *
 * @author elinx
 * @version 1.0
 */
@Data
public class LoginAssistUser implements UserDetails {

    private static final long serialVersionUID = -3685249101751401211L;

    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String avatarUrl;
    private String phone;
    private Integer gender;
    private Integer status;
    private String openId;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return getStatus() == 1;
    }

}
