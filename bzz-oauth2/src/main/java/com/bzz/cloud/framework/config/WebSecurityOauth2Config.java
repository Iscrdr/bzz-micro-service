package com.bzz.cloud.framework.config;

import com.bzz.cloud.framework.qqoauth.QQAuthenticationFilter;
import com.bzz.cloud.framework.qqoauth.QQAuthenticationManager;
import com.bzz.cloud.oauth.services.Auth2DetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class WebSecurityOauth2Config extends WebSecurityConfigurerAdapter {

    // 自动注入UserDetailsService
    @Autowired
    private Auth2DetailsService auth2DetailsService;

    @Autowired
    private Oauth2AuthenticationProvider oauth2AuthenticationProvider;

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(oauth2AuthenticationProvider);
    }

    /*@Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // 设置userDetailsService
        provider.setUserDetailsService(auth2DetailsService);
        // 禁止隐藏用户未找到异常
        provider.setHideUserNotFoundExceptions(false);
        // 使用BCrypt进行密码的hash        BCryptPasswordEncoder
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }*/










}
