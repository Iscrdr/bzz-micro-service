package com.bzz.cloud.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @RequestMapping("/api/login")
    @ResponseBody
    public String login(){
        return "123";
    }

}
