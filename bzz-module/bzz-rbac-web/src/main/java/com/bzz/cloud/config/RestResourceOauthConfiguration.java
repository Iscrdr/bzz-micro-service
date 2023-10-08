package com.bzz.cloud.config;


import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;


import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class RestResourceOauthConfiguration {



    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    String jwkSetUri;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/rabcservice/**").permitAll()
                        .requestMatchers("/rabcservice/getCaptcha/**").permitAll()
                        .requestMatchers("/rabcservice/register/**").permitAll()
                        .requestMatchers("/rabcservice/login/**").permitAll()
                        .requestMatchers("/login/**").permitAll()
                        .requestMatchers("/oauthservice/login/**").permitAll()
                        //oauth认证url不认证
                        .requestMatchers("/oauthservice/oauth/**").permitAll()
                        //swagger文档不认证
                        .requestMatchers("/swagger-ui/**","/swagger-ui/index.html",
                                "/v3/api-docs",
                                "/swagger-resources/**",
                                "/error"
                        ).permitAll().anyRequest().authenticated()

                )
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
        // @formatter:on
        return http.build();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri(this.jwkSetUri).build();
    }





}
