package com.bzz.cloud;

import com.bzz.cloud.framework.config.BzzCloudDbConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @PACKAGE_NAME: com.bzz.cloud
 * @Author : yang qianli
 * @Date: 2017-12-06 0:02
 * @Modified by:
 * @Description:
 */
@Import({BzzCloudDbConfig.class})
@EnableConfigurationProperties
@Configuration
@EnableTransactionManagement
@EnableDiscoveryClient
@EnableAspectJAutoProxy(proxyTargetClass=true,exposeProxy=true)
@EnableOpenApi
@SpringBootApplication(scanBasePackages={"com.bzz.cloud"})
@Slf4j
public class CodeGenApp {
    public static void main( String[] args ) {
        SpringApplication.run(CodeGenApp.class, args);
    }

}
