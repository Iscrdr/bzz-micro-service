package com.bzz.cloud.framework.config;

import com.bzz.cloud.framework.interceptor.RestApiOauth2Interceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
@Primary
public class WebSecurityConfig implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getRestApiOauth2Interceptor());

    }

    @Bean
    RestApiOauth2Interceptor getRestApiOauth2Interceptor() {
        return new RestApiOauth2Interceptor();
    }
}
