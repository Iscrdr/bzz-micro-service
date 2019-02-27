package com.bzz.cloud;

import com.bzz.cloud.framework.annotations.BzzMyBatisDao;
import com.bzz.cloud.framework.config.BzzCloudDbConfig;
import com.bzz.cloud.framework.config.RedisConfig;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

/**
 * @PACKAGE_NAME: com.bzz.cloud
 * @Author : yang qianli
 * @Date: 2017-12-06 0:02
 * @Modified by:
 * @Description:
 */
@Import({BzzCloudDbConfig.class})
@EnableConfigurationProperties
@EnableAspectJAutoProxy
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = {"com.bzz.cloud.*.dao"},annotationClass = BzzMyBatisDao.class)
@SpringBootApplication(scanBasePackages={"com.bzz.cloud"})
@Slf4j
public class GenApp {
    public static void main( String[] args ) {
        SpringApplication.run(GenApp.class, args);
    }


    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }




}
