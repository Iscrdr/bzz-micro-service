package com.bzz.cloud.entity;

import com.bzz.cloud.rbac.entity.SysUser;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class Auth2User implements UserDetails, CredentialsContainer {

    private SysUser sysUser;
    private final User user;

    public Auth2User(SysUser sysUser, User user){
        this.sysUser = sysUser;
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities();
    }

    @Override
    public String getPassword() {
        return sysUser.getPassword();
    }

    @Override
    public String getUsername() {
        return sysUser.getLoginName();
    }
    /**
     * 账户是否过期,过期无法验证
     */
    @Override
    public boolean isAccountNonExpired() {
        return sysUser.isAccountNonExpired();
    }

    /**
     * 指定用户是否被锁定或者解锁,锁定的用户无法进行身份验证
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return sysUser.isAccountNonLocked();
    }

    /**
     * 指示是否已过期的用户的凭据(密码),过期的凭据防止认证
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return sysUser.isCredentialsNonExpired();
    }

    /**
     * 是否被禁用,禁用的用户不能身份验证
     * @return
     */
    @Override
    public boolean isEnabled() {
        return sysUser.isEnabled();
    }

    @Override
    public void eraseCredentials() {
        user.eraseCredentials();
    }

    public SysUser getSysUser(){
        return this.sysUser;
    }
}
