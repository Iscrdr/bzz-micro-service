package com.bzz.cloud.oauth.services;

import com.bzz.cloud.oauth.entity.Auth2User;
import com.bzz.cloud.rbac.entity.SysMenu;
import com.bzz.cloud.rbac.entity.SysRole;
import com.bzz.cloud.rbac.entity.SysUser;
import com.bzz.cloud.rbac.service.SysMenuService;
import com.bzz.cloud.rbac.service.SysUserService;
import com.bzz.cloud.rbac.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class Auth2DetailsService implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 验证用户信息
     * @param loginName
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {

        SysUser sysUser = new SysUser();
        sysUser.setUserName(loginName);

        sysUser = sysUserService.getUserByLoginName(sysUser);
        if(null == sysUser ){
            return  null;
        }

        String password = sysUser.getPassword();
        // 账户是否过期,过期无法验证
        boolean accountNonExpired = sysUser.isAccountNonExpired();
        // 指定用户是否被锁定或者解锁,锁定的用户无法进行身份验证
        boolean accountNonLocked = sysUser.isAccountNonLocked();
        // 指示是否已过期的用户的凭据(密码),过期的凭据防止认证
        boolean credentialsNonExpired = sysUser.isCredentialsNonExpired();
        // 是否被禁用,禁用的用户不能身份验证
        boolean enabled = sysUser.isEnabled();

        //查询用户拥有的权限
        sysUser = sysMenuService.getAllMenu(sysUser);

        List<SysRole> sysRoleList = sysUser.getSysRoleList();

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (int i = 0; i < sysRoleList.size(); i++) {
            SysRole sysRole = sysRoleList.get(i);
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(sysRole.getCode());
            grantedAuthorities.add(grantedAuthority);
        }
        return new Auth2User(loginName, password,
                enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,
                grantedAuthorities,sysUser);
    }






}
