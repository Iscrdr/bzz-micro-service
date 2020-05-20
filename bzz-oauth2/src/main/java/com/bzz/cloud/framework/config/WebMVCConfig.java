package com.bzz.cloud.framework.config;

import org.springframework.context.annotation.Configuration;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-三月-15 22-32
 * @Modified by:
 * @Description:
 */
//@Configuration
//@EnableWebMvc //开启spring mvc的相关默认配置
public class WebMVCConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new Oauth2Interceptor());
    }
}
