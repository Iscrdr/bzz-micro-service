package com.bzz.cloud.config;

import com.bzz.cloud.annotation.FieldsExclude;
import com.bzz.common.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component
public class JsonReturnHandler implements HandlerMethodReturnValueHandler {

    private Logger logger = LoggerFactory.getLogger(JsonReturnHandler.class);

    @Override
    public boolean supportsReturnType(MethodParameter methodParameter) {
        return methodParameter.hasMethodAnnotation(FieldsExclude.class);
    }

    @Override
    public void handleReturnValue(Object o, MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest) throws Exception {
        // 设置这个就是最终的处理类了，处理完不再去找下一个类进行处理
        modelAndViewContainer.setRequestHandled(true);

        FieldsExclude annotation = methodParameter.getMethod().getAnnotation(FieldsExclude.class);
        String exclude = annotation.exclude();
        Class aClass1 = annotation.returnType();
        JsonUtils.filter(aClass1,"",exclude);
        logger.info("过滤 {} 中的字段: {} ",aClass1.getName(),exclude);
        String result  = JsonUtils.object2Json(o);
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        PrintWriter printWriter = response.getWriter();
        printWriter.write(result);
        printWriter.flush();

    }
}
