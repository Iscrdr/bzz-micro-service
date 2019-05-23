package com.bzz.cloud.framework.config;

import com.bzz.cloud.framework.qqoauth.QQAuthenticationFilter;
import com.bzz.cloud.framework.qqoauth.QQAuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-05-12 14-31
 * @Modified by:
 * @Description:
 */
@Configuration
@EnableResourceServer
public class RestJWTResourceOauthConfiguration extends ResourceServerConfigurerAdapter {

    @Autowired
    private TokenStore tokenStore;

    @Override
      public void configure(final HttpSecurity http) throws Exception {


        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                // 其余所有请求全部需要鉴权认证
                .authorizeRequests()
                .antMatchers("/bzzoauth/oauth/**").permitAll()
                .antMatchers("/bzzoauth/login/**").permitAll()//登用户录url不拦截
                .antMatchers("/login/**").permitAll()//登用户录url不拦截
                .antMatchers("/oauth/**").permitAll()//oauth认证不拦截
                .anyRequest().authenticated()
                .and().httpBasic();
        http.addFilterAt(qqAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer config) {
        config.resourceId("bzzoauth");
        config.stateless(false);
        config.tokenStore(tokenStore);
    }

    /**
     * 自定义 QQ登录 过滤器
     */
    private QQAuthenticationFilter qqAuthenticationFilter(){
        QQAuthenticationFilter authenticationFilter = new QQAuthenticationFilter("/login/qq");
        authenticationFilter.setAuthenticationManager(new QQAuthenticationManager());
        return authenticationFilter;
    }


}
