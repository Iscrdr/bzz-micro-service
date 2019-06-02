package com.bzz.cloud.config;

import com.bzz.cloud.annotation.FieldsExclude;
import com.bzz.common.filter.BzzJsonFilter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;

/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-05-21 05-15
 * @Modified by:
 * @Description:
 */
//@ControllerAdvice
public class DynamicJsonResponseAdvice extends AbstractMappingJacksonResponseBodyAdvice {

    private Logger logger = LoggerFactory.getLogger(DynamicJsonResponseAdvice.class);

    public static final String EXCLUDE_FILTER_ID = "dynamicExclude";
    private static final String WEB_PARAM_NAME = "exclude";
    private static final String DELI = ",";
    private static final String[] EMPTY = new String[]{};

    @Override
    protected void beforeBodyWriteInternal(MappingJacksonValue mappingJacksonValue,
                                           MediaType mediaType,
                                           MethodParameter methodParameter,
                                           ServerHttpRequest serverHttpRequest,
                                           ServerHttpResponse serverHttpResponse) {

        Class<?> aClass = mappingJacksonValue.getValue().getClass();
        FieldsExclude annotation = methodParameter.getMethod().getAnnotation(FieldsExclude.class);
        if(null != annotation && annotation.returnType().equals(aClass)){
            String exclude = annotation.exclude();
            BzzJsonFilter jacksonFilter = new BzzJsonFilter();
            jacksonFilter.exclude(aClass,exclude);
            mappingJacksonValue.setFilters(jacksonFilter);
            //mappingJacksonValue.setFilters(configFilters(exclude.split(",")));
            logger.info("过滤 {} 中的字段{}: ",aClass.getName(),exclude);
        }

    }

    private FilterProvider configFilters(String[] attrs) {
        String[] ignored = (attrs == null) ? EMPTY : attrs;
        PropertyFilter filter = SimpleBeanPropertyFilter.serializeAllExcept(ignored);
        return new SimpleFilterProvider().addFilter(EXCLUDE_FILTER_ID, filter);
    }
}
