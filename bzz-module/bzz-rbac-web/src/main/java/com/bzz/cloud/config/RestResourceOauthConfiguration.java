package com.bzz.cloud.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.boot.autoconfigure.security.oauth2.authserver.AuthorizationServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
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

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Resource
    private Environment environment;

    @Bean
    public AuthorizationServerProperties authorizationServerProperties(){
        return new AuthorizationServerProperties();
    }

    /**
     * TokenStore:使用数据库或Redis存储token，
     */
    @Bean
    public TokenStore tokenStore() {
        //return new JdbcTokenStore(dataSource);
        return new RedisTokenStore(redisConnectionFactory);
    }


    @Override
    public void configure(final HttpSecurity http) throws Exception {
        /*
         * antMatchers 有时会把项目名称作为根路径,这时不要加项目名称
         */


        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authorizeRequests()
                //oauth认证url 不认证
                .antMatchers("/rabcservice/getCaptcha/**").permitAll()
                //用户注册不认证
                .antMatchers("/rabcservice/register/**").permitAll()
                //用户登录不认证
                .antMatchers("/rabcservice/login/**").permitAll()
                //用户登录不认证
                .antMatchers("/login/**").permitAll()
                //登用户录url不认证
                .antMatchers("/oauthservice/login/**").permitAll()
                //oauth认证url不认证
                .antMatchers("/oauthservice/oauth/**").permitAll()
                //swagger文档不认证
                .antMatchers("/swagger-ui/**","/swagger-ui/index.html",
                        "/v3/api-docs",
                        "/swagger-resources/**",
                        "/error"
                        ).permitAll()
                .anyRequest().authenticated()
                .and().headers().frameOptions().disable().and().csrf().disable();
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
