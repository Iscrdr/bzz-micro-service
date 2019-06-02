package com.bzz.cloud.framework.social.qq;

import com.bzz.cloud.rbac.entity.QQUser;
import com.bzz.cloud.rbac.entity.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.Collection;

/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-05-30 05-07
 * @Modified by:
 * @Description:
 */
public class QQAuth2User extends User implements Serializable {
    private QQUser qqUser;
    private String loginType = "qq";

    public QQAuth2User(String username, String password, Collection<? extends GrantedAuthority> authorities,QQUser qqUser) {
        super(username, password, authorities);
        this.qqUser = qqUser;
    }

    public QQAuth2User(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.qqUser = qqUser;
    }

    public String getLoginType() {
        return loginType;
    }

    public QQUser getQqUser() {
        return qqUser;
    }
}
