package com.bzz.cloud;

import com.bzz.cloud.framework.annotations.BzzMyBatisDao;
import com.bzz.cloud.framework.config.BzzCloudDbConfig;
import com.bzz.cloud.framework.config.RedisConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * @PACKAGE_NAME: com.bzz.cloud
 * @CLASS_NAME:
 * @Author : yang qianli 
 * @Date: 2017-12-06 0:02
 * @Modified by:
 * @Date:
 * @Description:
 */
@Import({BzzCloudDbConfig.class, RedisConfig.class})
@MapperScan(basePackages = {"com.bzz.cloud.*.dao"},annotationClass = BzzMyBatisDao.class)
@Configuration
@EnableConfigurationProperties
@EnableAspectJAutoProxy
@EnableTransactionManagement
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication(scanBasePackages={"com.bzz.cloud.framework","com.bzz.cloud.rbac"})
public class BzzRbacServer {
    public static void main(String[] args) {
        SpringApplication.run(BzzRbacServer.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                if(beanName.toLowerCase().startsWith("sys")){
                    System.out.println(beanName);
                }

            }
        };
    }

}
