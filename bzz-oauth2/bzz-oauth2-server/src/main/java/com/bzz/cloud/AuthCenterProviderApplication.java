package com.bzz.cloud;

import com.bzz.cloud.framework.config.BzzCloudDbConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.*;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

/**
 * 授权服务
 */

@Import({BzzCloudDbConfig.class})
@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrix
@EnableConfigurationProperties
@Configuration
@SpringBootApplication(scanBasePackages = {"com.bzz.cloud"})
public class AuthCenterProviderApplication {
    public static void main( String[] args ){
        SpringApplication.run(AuthCenterProviderApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
