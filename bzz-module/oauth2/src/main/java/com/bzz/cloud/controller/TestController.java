package com.bzz.cloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/hello")
    public String getService(){
        System.out.println("hello 。。。");
        return "Hello SpringSecurity";
    }
    @GetMapping("/test")
    public String test(){
        System.out.println("test 。。。");
        return "test";
    }

    @GetMapping("/login")
    public String bzzlogin(){
        System.out.println("登录成功。。。");
        return "please login";
    }
}