package com.bzz.cloud.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author ：Iscrdr
 * @description：Swagger3配置
 * @email ：624003618@qq.com
 * @date ：2020-11-21 10:40
 * @modified By：
 * @version: 1.0.0
 */
@Configuration
public class Swagger3Config {


    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30).apiInfo(apiInfo())
                // 是否开启
                .enable(true).select()
                // 扫描的路径包
                .apis(RequestHandlerSelectors.basePackage("com.bzz.cloud.rbac.web"))
                // 指定路径处理PathSelectors.any()代表所有的路径
                .paths(PathSelectors.any()).build().pathMapping("/");
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Swagger3接口文档")
                .description("更多请咨询服务开发者BZZ。")
                .contact(new Contact("名字：name", "个人链接：http://xiaostarstar.com/", "邮箱：624003618@qq.com"))
                .version("1.0.0")
                .build();
    }
}
