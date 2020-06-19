package com.bzz.cloud.oauth.entity;

import com.bzz.cloud.rbac.entity.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.Collection;

/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-05-27 02-06
 * @Modified by:
 * @Description: 将 sysuser (@see:SysUser) 包装成为认证用户类
 */
public class Auth2User extends User implements Serializable {

    private static final long serialVersionUID = 4605382951627438372L;
    private SysUser sysUser;
    private String loginType;

    public Auth2User(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public Auth2User(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities,SysUser sysUser) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.sysUser = sysUser;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getLoginType() {
        return loginType;
    }
}
