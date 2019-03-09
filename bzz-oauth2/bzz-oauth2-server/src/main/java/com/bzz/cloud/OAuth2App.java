package com.bzz.cloud;

import com.bzz.cloud.framework.annotations.BzzMyBatisDao;
import com.bzz.cloud.framework.config.BzzCloudDbConfig;
import org.mybatis.spring.annotation.MapperScan;
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
 * 授权服务
 */

@Import({BzzCloudDbConfig.class})
@MapperScan(basePackages = {"com.bzz.cloud.*.dao"},annotationClass = BzzMyBatisDao.class)
@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrix
@EnableConfigurationProperties
@Configuration
@EnableTransactionManagement
@SpringBootApplication(scanBasePackages = {"com.bzz.cloud"})
public class OAuth2App {
    public static void main( String[] args ){
        SpringApplication.run(OAuth2App.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
