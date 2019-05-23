package com.bzz.cloud.framework.config;

import com.bzz.cloud.oauth.entity.Auth2User;
import com.bzz.cloud.oauth.services.Auth2DetailsService;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;

/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-05-19 23-57
 * @Modified by:
 * @Description:
 */
@Named
@Component
public class Oauth2AuthenticationProvider extends DaoAuthenticationProvider {

    // 构造函数中注入
    @Inject
    public Oauth2AuthenticationProvider(Auth2DetailsService auth2DetailsService){
        this.setUserDetailsService(auth2DetailsService);
    }

    /**
     * 自定义验证方式
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        Auth2User userDetails = (Auth2User)this.getUserDetailsService().loadUserByUsername(username);

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, authorities);
        return authenticationToken;

    }

    @Override
    public boolean supports(Class<?> arg0) {
        return true;
    }
}
