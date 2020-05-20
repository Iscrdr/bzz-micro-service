package com.bzz.cloud.framework.config;

import com.bzz.cloud.oauth.entity.Auth2User;
import com.bzz.cloud.oauth.services.Auth2DetailsService;
import com.bzz.cloud.rbac.entity.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;

/**
 * @author : yang qianli
 * @email: 624003618@qq.com
 * @date: 2019-05-19 23-57
 * @modified by:
 * @description:
 */
@Named
@Component
public class Oauth2AuthenticationProvider extends DaoAuthenticationProvider {

    /**
     *构造函数中注入
     */
    @Inject
    public Oauth2AuthenticationProvider(Auth2DetailsService auth2DetailsService){
        this.setUserDetailsService(auth2DetailsService);
    }
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;
    /**
     * 自定义验证方式
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {


        SysUser principal = (SysUser) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        Auth2User userDetails = (Auth2User)this.getUserDetailsService().loadUserByUsername(principal.getUserName());
        userDetails.setLoginType(principal.getLoginType());
        //用户没有找到
        if(null == userDetails || StringUtils.isBlank(userDetails.getUsername())){
            this.logger.debug("Authentication failed: username does not found");
            throw new UsernameNotFoundException("用户没有找到");
        }
        //密码错误
        String presentedPassword = authentication.getCredentials().toString();
        if (!this.bCryptPasswordEncoder.matches(presentedPassword, userDetails.getPassword())) {
            this.logger.debug("Authentication failed: password does not match stored value");
            throw new BadCredentialsException("密码错误");
        }
        //密码已经过期
        if (!userDetails.isCredentialsNonExpired()){
            this.logger.debug("Authentication failed: password is expired");
            throw new CredentialsExpiredException("密码已经过期，请修改密码");
        }
        //账号被禁用
        if (!userDetails.isEnabled()){
            this.logger.debug("Authentication failed: username is not Disabled");
            throw new DisabledException("账号被禁用");
        }
        //账号过期
        if (!userDetails.isAccountNonExpired()){
            this.logger.debug("Authentication failed: username does not found");
            throw new AccountExpiredException("用户没有找到");
        }
        //账号被锁
        if (!userDetails.isAccountNonLocked()){
            this.logger.debug("Authentication failed: username is locked");
            throw new LockedException("账号被锁定");
        }

        //client 未授权
        SysUser sysUser = userDetails.getSysUser();
        if(StringUtils.isBlank(sysUser.getClientId())){
            this.logger.debug("Authentication failed: client is not found");
            throw new UnapprovedClientAuthenticationException("client不存在");
        }

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, authorities);
        return authenticationToken;
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }
}
