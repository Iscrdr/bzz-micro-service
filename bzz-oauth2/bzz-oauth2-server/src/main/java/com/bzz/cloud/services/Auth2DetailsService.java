package com.bzz.cloud.services;


import com.bzz.common.Utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Auth2DetailsService implements UserDetailsService {

    @Autowired
    private FeignAuth2UserService feignAuth2UserService;

    /**
     * 验证用户信息
     * @param loginName
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
        String userjson = feignAuth2UserService.getUser(loginName);
        String password = JsonUtils.getFeildFromJson("password",userjson);
        String accountNonExpired = JsonUtils.getFeildFromJson("accountNonExpired", userjson);// 账户是否过期,过期无法验证
        String credentialsNonExpired = JsonUtils.getFeildFromJson("accountNonLocked", userjson);// 指定用户是否被锁定或者解锁,锁定的用户无法进行身份验证
        String accountNonLocked = JsonUtils.getFeildFromJson("credentialsNonExpired", userjson);// 指示是否已过期的用户的凭据(密码),过期的凭据防止认证
        String enabled = JsonUtils.getFeildFromJson("enabled", userjson);// 是否被禁用,禁用的用户不能身份验证

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_ADMIN");
        grantedAuthorities.add(grantedAuthority);

        return new User(loginName,password,
                enabled.equals("true")?true:false,
                accountNonExpired.equals("true")?true:false,
                credentialsNonExpired.equals("true")?true:false,
                accountNonLocked.equals("true")?true:false,grantedAuthorities);
    }

}
