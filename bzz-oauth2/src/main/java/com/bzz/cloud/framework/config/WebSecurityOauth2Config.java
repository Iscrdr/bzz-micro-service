package com.bzz.cloud.framework.config;

import com.bzz.cloud.framework.filter.JsonPayloadUserNamePasswordAuthenticationFilter;
import com.bzz.cloud.framework.filter.PermitAllAuthenticationFilter;
import com.bzz.cloud.framework.handler.CustomAuthenticationFailHandler;
import com.bzz.cloud.framework.handler.CustomAuthenticationSuccessHandler;

import com.bzz.cloud.framework.social.qq.QQAuthenticationFilter;
import com.bzz.cloud.framework.social.qq.QQAuthenticationManager;
import com.bzz.cloud.framework.social.qq.QQAuthenticationSuccessHandler;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;


import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;


@Configuration
@EnableWebSecurity
public class WebSecurityOauth2Config {


    @Autowired
    @Lazy
    private Oauth2AuthenticationProvider oauth2AuthenticationProvider;

    @Autowired
    @Lazy
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    @Autowired
    @Lazy
    private CustomAuthenticationFailHandler customAuthenticationFailHandler;

    @Autowired
    private QQAuthenticationSuccessHandler qqAuthenticationSuccessHandler;

    @Autowired
    private PermitAllAuthenticationFilter permitAllAuthenticationFilter;




    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        try {


            http.authorizeHttpRequests((authorize) ->
                            authorize
                                    .requestMatchers("/login/**").permitAll()
                                    .requestMatchers("/oauthservice/login/**").permitAll()
                                    .requestMatchers("/oauthservice/oauth/**").permitAll()
                                    .requestMatchers("/oauthservice/social/qq").permitAll()
                                    .requestMatchers("/social/qq").permitAll()
                                    .requestMatchers("/qq/login/**").permitAll()
                                    .requestMatchers("/oauth/**").permitAll()
                                    .requestMatchers("/oauthservice/test1").permitAll()
                                    .requestMatchers("/test1").permitAll()

                                    .anyRequest().authenticated()
                    ).formLogin().loginPage("/login");

            http.csrf().disable();
            http.addFilterBefore(permitAllAuthenticationFilter, JsonPayloadUserNamePasswordAuthenticationFilter.class);

            OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();
            authorizationServerConfigurer.authorizationEndpoint(endpoint ->
                endpoint.authenticationProvider(oauth2AuthenticationProvider)
            );

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return http.build();
    }






    /**
     * 获取AuthenticationManager（认证管理器），登录时认证使用
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }



    @Value("${jwt.public.key}")
    RSAPublicKey key;

    @Value("${jwt.private.key}")
    RSAPrivateKey priv;

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(this.key).build();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(this.key).privateKey(this.priv).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    public  PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
