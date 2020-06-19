package com.bzz.cloud.framework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
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
                .authorizeRequests()
                .antMatchers("/login/**").permitAll()
                .antMatchers("/oauthservice/login/**").permitAll()
                .antMatchers("/oauthservice/oauth/**").permitAll()
                .antMatchers("/oauthservice/social/qq").permitAll()
                .antMatchers("/social/qq").permitAll()
                .antMatchers("/qq/login/**").permitAll()
                .antMatchers("/oauth/**").permitAll()
                .anyRequest().authenticated()
                .and().headers().frameOptions().disable().and().httpBasic();

    }

    @Override
    public void configure(ResourceServerSecurityConfigurer config) {
        config.resourceId("oauthservice");
        config.stateless(false);
        config.tokenStore(tokenStore());
    }




}
