package com.bzz.cloud.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.boot.autoconfigure.security.oauth2.authserver.AuthorizationServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Configuration
@EnableResourceServer
@Order(value = 99)
public class RestResourceOauthConfiguration extends ResourceServerConfigurerAdapter {



    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OAuth2ClientProperties oAuth2ClientProperties;

    @Autowired
    private AuthorizationServerProperties authorizationServerProperties;

    @Bean
    public AuthorizationServerProperties authorizationServerProperties(){
        return new AuthorizationServerProperties();
    }



    @Override
    public void configure(final HttpSecurity http) throws Exception {
        /*http.csrf().disable().anonymous().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authorizeRequests()
                //.antMatchers("/api/register/**").access("permitAll")
                .antMatchers("/api/getCaptcha").access("permitAll")
                .anyRequest().authenticated()
        ;*/

        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authorizeRequests()
                .antMatchers("/rabcservice/getCaptcha/**").permitAll() //oauth认证url不拦截
                .antMatchers("/rabcservice/register/**").permitAll() //用户注册不拦截
                .antMatchers("/rabcservice/login/**").permitAll() //用户登录不拦截
                .antMatchers("/login/**").permitAll() //用户登录不拦截
                .antMatchers("/oauthservice/login/**").permitAll()//登用户录url不拦截
                .antMatchers("/oauthservice/oauth/**").permitAll() //oauth认证url不拦截
                .anyRequest().authenticated()
                .and().headers().frameOptions().disable();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer config) {
        config.resourceId("oauthservice");
        config.stateless(false);

        RemoteTokenServices remoteTokenServices = remoteTokenServices();
        if (Objects.nonNull(remoteTokenServices)) {
            remoteTokenServices.setRestTemplate(restTemplate);
            config.tokenServices(remoteTokenServices);
        }




    }
    /**
     * 创建一个默认的资源服务token
     *
     * @return
     */
    @Primary
    @Bean
    public RemoteTokenServices remoteTokenServices() {
        RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
        remoteTokenServices.setCheckTokenEndpointUrl(authorizationServerProperties.getCheckTokenAccess());
        remoteTokenServices.setClientId(oAuth2ClientProperties.getClientId());
        remoteTokenServices.setClientSecret(oAuth2ClientProperties.getClientSecret());



        return remoteTokenServices;

    }


}
