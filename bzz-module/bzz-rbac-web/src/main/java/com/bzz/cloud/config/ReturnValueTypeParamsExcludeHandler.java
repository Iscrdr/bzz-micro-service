package com.bzz.cloud.config;

import com.bzz.cloud.annotation.FieldsExclude;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-05-21 01-53
 * @Modified by:
 * @Description:
 */
public class ReturnValueTypeParamsExcludeHandler implements HandlerMethodReturnValueHandler {

    private Class clazz;
    private String excludeField;

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public void setExcludeField(String excludeField) {
        this.excludeField = excludeField;
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return true;
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        Method method = returnType.getMethod();
        Annotation annotation = method.getAnnotation(FieldsExclude.class);
        if(annotation instanceof FieldsExclude){
            FieldsExclude fieldsExclude = (FieldsExclude)annotation;
            clazz = fieldsExclude.returnType();
            excludeField = fieldsExclude.exclude();
        }
    }
}
