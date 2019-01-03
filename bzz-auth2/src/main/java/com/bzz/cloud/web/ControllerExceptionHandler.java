package com.bzz.cloud.web;

import com.bzz.cloud.exception.UserNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(UserNotExistException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String,Object> handleUserNotExistException(UserNotExistException ex){
        Map<String,Object> map = new HashMap<>();
        map.put("id",ex.getId());
        map.put("message",ex.getMessage());
        return map;
    }
}
