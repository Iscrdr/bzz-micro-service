package com.bzz.cloud;

import com.bzz.cloud.framework.config.BzzCloudDbConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

/**
 * 认证与授权服务
 * @author cloud
 */
@Import({BzzCloudDbConfig.class})
@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrix
@EnableConfigurationProperties
@EnableTransactionManagement
@SpringBootApplication(scanBasePackages = {"com.bzz.cloud"})
public class Oauth2App {
    
    public static void main( String[] args ){
        SpringApplication springApplication = new SpringApplication(Oauth2App.class);
        springApplication.setAllowCircularReferences(Boolean.TRUE);
        springApplication.run(args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
