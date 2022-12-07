package com.bzz.cloud.config;

import com.bzz.common.annotation.FieldsExclude;
import com.bzz.common.utils.JsonUtils;
import com.bzz.common.utils.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.HashMap;
import java.util.Map;

//@ControllerAdvice
public class JsonResponseAdvice implements ResponseBodyAdvice {

    private Logger logger = LoggerFactory.getLogger(JsonResponseAdvice.class);
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return methodParameter.hasMethodAnnotation(FieldsExclude.class);
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        FieldsExclude annotation = methodParameter.getMethod().getAnnotation(FieldsExclude.class);
        String exclude = annotation.exclude();
        Class aClass1 = annotation.returnType();
        JsonUtils.filter(aClass1,"",exclude);
        logger.info("过滤 {} 中的字段: {} ",aClass.getName(),exclude);

        Map<String,String> msgMap = new HashMap<>();
        msgMap.put(ResponseData.RESPONE_CODE,"成功");

        String result  = JsonUtils.object2Json(o,false);
        Object o1 = JsonUtils.json2Object(result, o.getClass());
        return o1;
    }
}
