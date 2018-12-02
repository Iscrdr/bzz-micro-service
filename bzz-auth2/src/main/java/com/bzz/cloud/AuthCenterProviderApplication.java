package com.bzz.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * 授权服务
 */

@SpringCloudApplication
@EnableAuthorizationServer
@ComponentScans({
        @ComponentScan("com.bzz.cloud.*")
})
@EnableDiscoveryClient
@EnableHystrix
public class AuthCenterProviderApplication {
    public static void main( String[] args ){
        SpringApplication.run(AuthCenterProviderApplication.class, args);
    }
}
