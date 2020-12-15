package com.bzz.cloud.config;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-三月-15 22-32
 * @Modified by:
 * @Description:
 */
@Configuration
@EnableWebMvc //开启spring mvc的相关默认配置

public class WebMVCJsonConfig implements WebMvcConfigurer {

    /**
     * 自定义的返回值处理器
     */
    @Autowired
    private JsonReturnHandler jsonReturnHandler;

    @Autowired
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new Oauth2Interceptor()).excludePathPatterns("classpath*:/static/**")
                // swagger 排除规则
                .excludePathPatterns("/rbacservice/swagger-ui/**")
                .excludePathPatterns("/rbacservice/v3/api-docs")
                .excludePathPatterns("/rbacservice/swagger-resources/**")
                .excludePathPatterns("/rbacservice/error")
                .excludePathPatterns("/rbacservice/webjars/**");
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.
                addResourceHandler( "/rbacservice/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
                .resourceChain(false);
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {
        handlers.add(jsonReturnHandler);
    }
    @PostConstruct
    public void init() {
        final List<HandlerMethodReturnValueHandler> newHandlers = new LinkedList<>();
        final List<HandlerMethodReturnValueHandler> originalHandlers = requestMappingHandlerAdapter.getReturnValueHandlers();
        if (null != originalHandlers) {
            newHandlers.addAll(originalHandlers);
            // 获取处理器应处于的位置，需要在RequestResponseBodyMethodProcessor之前
            final int index = obtainValueHandlerPosition(originalHandlers, RequestResponseBodyMethodProcessor.class);
            newHandlers.add(index, jsonReturnHandler);
        } else {
            newHandlers.add(jsonReturnHandler);
        }
        requestMappingHandlerAdapter.setReturnValueHandlers(newHandlers);
    }

    /**
     * 获取让自定义处理器生效应处于的位置
     *
     * @param originalHandlers 已经添加的返回值处理器
     * @param handlerClass     返回值处理器的类型
     * @return 自定义处理器需要的位置
     */
    private int obtainValueHandlerPosition(final List<HandlerMethodReturnValueHandler> originalHandlers, Class<?> handlerClass) {
        for (int i = 0; i < originalHandlers.size(); i++) {
            final HandlerMethodReturnValueHandler valueHandler = originalHandlers.get(i);
            if (handlerClass.isAssignableFrom(valueHandler.getClass())) {
                return i;
            }
        }
        return -1;
    }
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        ObjectMapper mapper = new ObjectMapper();

        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        //设置为true时，属性名称不带双引号
        mapper.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, true);
        //反序列化是是否允许属性名称不带双引号
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        /**
         * json格式数据处理
         */
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        jackson2HttpMessageConverter.setObjectMapper(mapper);

        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        mediaTypes.add(MediaType.APPLICATION_ATOM_XML);
        mediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
        mediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
        mediaTypes.add(MediaType.APPLICATION_PDF);
        mediaTypes.add(MediaType.APPLICATION_RSS_XML);
        mediaTypes.add(MediaType.APPLICATION_XHTML_XML);
        mediaTypes.add(MediaType.APPLICATION_XML);
        mediaTypes.add(MediaType.IMAGE_GIF);
        mediaTypes.add(MediaType.IMAGE_JPEG);
        mediaTypes.add(MediaType.IMAGE_PNG);
        mediaTypes.add(MediaType.TEXT_EVENT_STREAM);
        mediaTypes.add(MediaType.TEXT_HTML);
        mediaTypes.add(MediaType.TEXT_MARKDOWN);
        mediaTypes.add(MediaType.TEXT_PLAIN);
        mediaTypes.add(MediaType.TEXT_XML);
        jackson2HttpMessageConverter.setSupportedMediaTypes(mediaTypes);

        converters.add(jackson2HttpMessageConverter);

        /**
         * 字符串格式数据处理
         */
        /*StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(Charset.forName("utf-8"));
        List<MediaType> mediaTypes1 = new ArrayList<>();
        mediaTypes1.add(MediaType.TEXT_PLAIN);
        stringHttpMessageConverter.setSupportedMediaTypes(mediaTypes1);
        converters.add(stringHttpMessageConverter);*/

    }


}
