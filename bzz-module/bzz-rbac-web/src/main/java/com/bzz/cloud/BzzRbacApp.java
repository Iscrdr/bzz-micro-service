package com.bzz.cloud;

import com.bzz.cloud.framework.config.BzzCloudDbConfig;
import com.bzz.cloud.framework.config.RedisConfig;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.oas.annotations.EnableOpenApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
@Configuration
@EnableConfigurationProperties
@EnableTransactionManagement
@EnableFeignClients
@EnableHystrix
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.bzz"})
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@EnableOpenApi
public class BzzRbacApp {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(BzzRbacApp.class);
        springApplication.setAllowCircularReferences(Boolean.TRUE);
        springApplication.run(args);
    }

/*    @Bean
    public Validator validator(){
        ValidatorFactory validatorFactory = Validation.byProvider( HibernateValidator.class )
                .configure()
                .addProperty( "hibernate.validator.fail_fast", "false" )
                .buildValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        return validator;
    }*/



    @Bean
    @LoadBalanced
    public RestTemplate loadBalancedRestTemplate(RestTemplateBuilder builder){
        RestTemplate restTemplate = builder.build();
        return restTemplate;
    }







}
