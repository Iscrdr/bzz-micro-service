package com.bzz.cloud.config;

import com.bzz.common.annotation.FieldsExclude;
import com.bzz.common.annotation.FieldsInclude;
import com.bzz.common.utils.JsonUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;


import java.io.PrintWriter;

@Component
public class JsonReturnHandler implements HandlerMethodReturnValueHandler {

    private Logger logger = LoggerFactory.getLogger(JsonReturnHandler.class);

    @Override
    public boolean supportsReturnType(MethodParameter methodParameter) {


        if( methodParameter.hasMethodAnnotation(FieldsInclude.class) ){
            return true;
        }
        if( methodParameter.hasMethodAnnotation(FieldsExclude.class) ){
            return true;
        }
        return false;
    }

    @Override
    public void handleReturnValue(Object o, MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest) throws Exception {
        // 设置这个就是最终的处理类了，处理完不再去找下一个类进行处理
        modelAndViewContainer.setRequestHandled(true);

        FieldsExclude excludeAnnotation = methodParameter.getMethod().getAnnotation(FieldsExclude.class);
        String result = "";
        if(excludeAnnotation != null){
            String exclude = excludeAnnotation.exclude();
            Class aClass1 = excludeAnnotation.returnType();
            JsonUtils.filter(aClass1,"",exclude);
            logger.info("{} 中的字段: {} 不转为JSON",aClass1.getName(),exclude);
            result  = JsonUtils.object2Json(o,false);
        }

        FieldsInclude includeAnnotation = methodParameter.getMethod().getAnnotation(FieldsInclude.class);
        if(includeAnnotation != null){
            String include = includeAnnotation.include();
            Class aClass1 = includeAnnotation.returnType();
            JsonUtils.filter(aClass1,include,"");
            logger.info("只把{} 中的字段: {} 转为JSON ",aClass1.getName(),include);
            result  = JsonUtils.object2Json(o,true);
        }


        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        PrintWriter printWriter = response.getWriter();
        printWriter.write(result);
        printWriter.flush();

    }
}
