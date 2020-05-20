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



    /**
     * @Description: Bean 校验异常
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseResult notValidExceptionHandler(MethodArgumentNotValidException e) throws Exception {
        ResponseResult ret = new ResponseResult();

        Map<String,String> map = new HashMap<>(16);
        if (e.getBindingResult() != null && !CollectionUtils.isEmpty(e.getBindingResult().getAllErrors())) {
            List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
            for(ObjectError error : allErrors){
                //map.put(oje.get,oje.getDefaultMessage());
                System.out.println(error.getCode()+"---"+error.getArguments()+"---"+error.getDefaultMessage());
            }
            ret.setSuccess(false);
            ret.setMsgMap(map);
        } else {

        }
        return ret;
    }

}
