package com.bzz.cloud;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;
/**
 * @PACKAGE_NAME: com.bzz.cloud
 * @CLASS_NAME:
 * @Author : yang qianli
 * @Date: 2019-06-10 1:13
 * @Modified by:
 * @Date:
 * @Description:
 */
@EnableConfigurationProperties
@Configuration
@EnableDiscoveryClient
@EnableFeignClients
@EnableAsync(proxyTargetClass = true)
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class BzzGateWayApp {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    public static void main( String[] args ){
        SpringApplication springApplication = new SpringApplication(BzzGateWayApp.class);
        springApplication.setAllowCircularReferences(true);
        springApplication.run(args);
    }
}
