package com.bzz.cloud.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {






    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http
                //.addFilterAt(getMyLoginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/getCaptcha").permitAll()
                .anyRequest().authenticated()
                .and().httpBasic()
                // 由于使用的是JWT，我们这里不需要csrf
                .and().csrf().disable();

    }






}
