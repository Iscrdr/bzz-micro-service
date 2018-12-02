package com.bzz.cloud.services;

import com.bzz.cloud.entity.Auth2User;
import com.bzz.cloud.rbac.dao.SysUserDao;
import com.bzz.cloud.rbac.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Auth2DetailsService implements UserDetailsService {

    @Autowired
    private SysUserDao sysUserDao;

    /**
     * 验证用户信息
     * @param loginName
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {

        SysUser sysUser = sysUserDao.selectUser(loginName);
        List<GrantedAuthority>  authorities = null;
        return new Auth2User(sysUser, new User(sysUser.getLoginName(),sysUser.getPassword(),true, true, true, true, authorities));
    }
    /**
     * 获取权限信息
     */

    private List<GrantedAuthority> buildUserAuthority(){

        return null;
    }
}
