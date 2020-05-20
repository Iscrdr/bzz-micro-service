package com.bzz.cloud.rbac.service;

import com.bzz.cloud.config.Oauth2FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-05-19 00-23
 * @Modified by:
 * @Description:
 */

@FeignClient(value = "oauthservice",path = "/oauthservice",configuration = Oauth2FeignClientConfiguration.class)
public interface  Oauth2FeignClientService {

    @GetMapping("/currentUser")
    String currentUser() ;


    /**
     * 获取token,参数: String client_id,String client_secret,String grant_type,String username,String password
     * 刷新token,参数: String client_id,String client_secret,String grant_type,String username,String password,String refresh_token
     * @param client_id : 客户端id
     * @param client_secret: 客户端密码
     * @param grant_type:授权类型
     * @param username:用户名
     * @param password:密码
     * @return token
     */
    @PostMapping("/oauth/token")
    String getToken(@RequestParam("client_id")String client_id,
                    @RequestParam("client_secret")String client_secret,
                    @RequestParam("grant_type")String grant_type,
                    @RequestParam("username")String username,
                    @RequestParam("password")String password,
                    @RequestParam(value = "refresh_token",required = false)String refresh_token);


}
