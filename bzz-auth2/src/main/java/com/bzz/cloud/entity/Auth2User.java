package com.bzz.cloud.entity;

import com.bzz.cloud.rbac.entity.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class Auth2User extends User {

    private SysUser sysUser;

    public Auth2User(SysUser sysUser, Collection<? extends GrantedAuthority> authorities) {
        this(sysUser.getLoginName(),sysUser.getPassword(),authorities);
    }
    public Auth2User(SysUser sysUser,  boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        this(sysUser.getLoginName(),sysUser.getPassword(),sysUser.isEnabled(),sysUser.isAccountNonExpired(),sysUser.isCredentialsNonExpired(),sysUser.isAccountNonLocked(),authorities);
    }
    public Auth2User(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public Auth2User(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }


    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }
}
