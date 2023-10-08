package com.bzz.cloud.framework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;


import org.springframework.web.cors.CorsUtils;


/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-05-12 14-31
 * @Modified by:
 * @Description:
 */


public class RestJWTResourceOauthConfiguration  {


   /*
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    public TokenStore tokenStore() {
        //return new JdbcTokenStore(dataSource);
        return new RedisTokenStore(redisConnectionFactory);
    }

    @Override
    public void configure(final HttpSecurity http) throws Exception {

        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                //.apply(permitAllSecurityConfig)
                //.and()
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .requestMatchers("/login/**").permitAll()
                .requestMatchers("/oauthservice/login/**").permitAll()
                .requestMatchers("/oauthservice/oauth/**").permitAll()
                .requestMatchers("/oauthservice/social/qq").permitAll()
                .antMatchers("/social/qq").permitAll()
                .requestMatchers("/qq/login/**").permitAll()
                .requestMatchers("/oauth/**").permitAll()
                .requestMatchers("/oauthservice/test1").permitAll()
                .requestMatchers("/test1").permitAll()
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and().headers().frameOptions().disable().and().httpBasic();

    }

    @Override
    public void configure(ResourceServerSecurityConfigurer config) {
        config.resourceId("oauthservice");
        config.stateless(false);
        config.tokenStore(tokenStore());
    }

*/


}
