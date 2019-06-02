package com.bzz.cloud.oauth.services;

import com.bzz.cloud.oauth.entity.Auth2User;
import com.bzz.cloud.rbac.entity.SysApi;
import com.bzz.cloud.rbac.entity.SysAuthority;
import com.bzz.cloud.rbac.entity.SysRole;
import com.bzz.cloud.rbac.entity.SysUser;
import com.bzz.cloud.rbac.service.SysApiService;
import com.bzz.cloud.rbac.service.SysUserService;
import com.bzz.cloud.rbac.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Auth2DetailsService implements UserDetailsService, SocialUserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysApiService sysApiService;

    /**
     * 验证用户信息
     * @param loginName
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {


        SysUser sysUser1 = new SysUser();
        sysUser1.setUserName(loginName);

        SysUser sysUser = sysUserService.getUserByLoginName(sysUser1);
        if(null == sysUser ){
            return  null;
        }

        String password = sysUser.getPassword();
        boolean accountNonExpired = sysUser.isAccountNonExpired();// 账户是否过期,过期无法验证
        boolean accountNonLocked = sysUser.isAccountNonLocked();// 指定用户是否被锁定或者解锁,锁定的用户无法进行身份验证
        boolean credentialsNonExpired = sysUser.isCredentialsNonExpired();// 指示是否已过期的用户的凭据(密码),过期的凭据防止认证
        boolean enabled = sysUser.isEnabled();// 是否被禁用,禁用的用户不能身份验证

        //查询用户拥有的所有权限
        sysUser = sysApiService.getAllApi(sysUser1);

        List<GrantedAuthority> grantedAuthorities = UserUtil.getUserAuthorityList(sysUser);

        return new Auth2User(loginName, password,
                enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,
                grantedAuthorities,sysUser);



    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        return buildUser(userId);
    }


    /**
     * 根据登录名查询用户信息
     * @param loginName
     * @return
     */
    private SocialUser buildUser(String loginName) {
        SysUser sysUser = new SysUser();
        sysUser.setUserName(loginName);

        sysUser = sysUserService.getUserByLoginName(sysUser);

        String password = sysUser.getPassword();
        boolean accountNonExpired = sysUser.isAccountNonExpired();// 账户是否过期,过期无法验证
        boolean accountNonLocked = sysUser.isAccountNonLocked();// 指定用户是否被锁定或者解锁,锁定的用户无法进行身份验证
        boolean credentialsNonExpired = sysUser.isCredentialsNonExpired();// 指示是否已过期的用户的凭据(密码),过期的凭据防止认证
        boolean enabled = sysUser.isEnabled();// 是否被禁用,禁用的用户不能身份验证


        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        List<SysRole> sysRoleList = sysUser.getSysRoleList();
        if(null != sysRoleList && sysRoleList.size()>0){
            for(SysRole role:sysRoleList){
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getRoleType());
                grantedAuthorities.add(grantedAuthority);
            }
        }else {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_ADMIN");
            grantedAuthorities.add(grantedAuthority);
        }

        return new SocialUser(loginName, password,
                enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,
                grantedAuthorities);
    }


}
