package com.bzz.cloud.framework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.social.security.SpringSocialConfigurer;


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

        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                /*.formLogin()
                .usernameParameter("userName").passwordParameter("password")
                .successHandler(customAuthenticationSuccessHandler)
                .failureHandler(customAuthenticationFailHandler)
                .and()*/
                // 其余所有请求全部需要鉴权认证
                .authorizeRequests()
                .antMatchers("/oauth/**").permitAll()
                .antMatchers("/login/**").permitAll()//登用户录url不拦截
               // .antMatchers("/qq/login/**").permitAll()//登用户录url不拦截
                .antMatchers("/bzzoauth/social/qq").permitAll()
               .antMatchers("/social/qq").permitAll()
                /*.antMatchers("/login/**").permitAll()//登用户录url不拦截
               .antMatchers("/qq/login/**").permitAll()//登用户录url不拦截
               .antMatchers("/oauth/**").permitAll()*///oauth认证不拦截*/
                .anyRequest().authenticated()
                .and().httpBasic();


    }

    @Override
    public void configure(ResourceServerSecurityConfigurer config) {
        config.resourceId("bzzoauth");
        config.stateless(false);
        config.tokenStore(tokenStore);
    }




}
