package com.bzz.cloud.framework.config;

import com.bzz.common.Utils.ResponseResult;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-03-30 20-41
 * @Modified by:
 * @Description:
 */
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    // Bean 校验异常
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseResult notValidExceptionHandler(MethodArgumentNotValidException e) throws Exception {
        ResponseResult ret = new ResponseResult();

        Map<String,String> map = new HashMap<>();
        if (e.getBindingResult() != null && !CollectionUtils.isEmpty(e.getBindingResult().getAllErrors())) {
            List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
            for(ObjectError error : allErrors){
                System.out.println(error.getCode()+"---"+error.getArguments()+"---"+error.getDefaultMessage());
                //map.put(oje.get,oje.getDefaultMessage());
            }
            ret.setSuccess(false);
            ret.setMsgMap(map);
        } else {

        }
        return ret;
    }

}
