package com.bzz.cloud;

import com.bzz.cloud.framework.config.BzzCloudDbConfig;
import com.bzz.cloud.framework.config.RedisConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @PACKAGE_NAME: com.bzz.cloud
 * @Author : yang qianli
 * @Date: 2017-12-06 0:02
 * @Modified by:
 * @Description:
 */
@Import({BzzCloudDbConfig.class, RedisConfig.class})
@EnableConfigurationProperties
@ComponentScans({
        @ComponentScan("com.bzz.cloud.*")
})
@EnableAspectJAutoProxy
@Configuration
@EnableTransactionManagement
@SpringBootApplication
public class GenApp {
    public static void main( String[] args ) {
        SpringApplication.run(GenApp.class, args);
    }
}
