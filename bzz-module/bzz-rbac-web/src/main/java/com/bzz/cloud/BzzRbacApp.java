package com.bzz.cloud;

import com.bzz.cloud.framework.annotations.BzzMyBatisDao;
import com.bzz.cloud.framework.config.BzzCloudDbConfig;
import com.bzz.cloud.framework.config.RedisConfig;
import com.netflix.loadbalancer.BestAvailableRule;
import com.netflix.loadbalancer.IRule;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.HibernateValidator;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
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

@EnableTransactionManagement
@EnableHystrix
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages={"com.bzz.cloud"})
@Slf4j
public class BzzRbacApp {
    public static void main(String[] args) {
        SpringApplication.run(BzzRbacApp.class,args);
    }


    @Bean
    public Validator validator(){
        ValidatorFactory validatorFactory = Validation.byProvider( HibernateValidator.class )
                .configure()
                .addProperty( "hibernate.validator.fail_fast", "false" )
                .buildValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        return validator;
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }

    @Bean
    public IRule myRule(){
        //return new RoundRobinRule();//轮询
        //return new RetryRule();//重试
        return new BestAvailableRule();
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
