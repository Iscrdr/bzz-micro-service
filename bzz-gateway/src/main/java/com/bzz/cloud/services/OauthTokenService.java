package com.bzz.cloud.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-二月-23 18-22
 * @Modified by:
 * @Description:
 */
@FeignClient(value = "bzzoauth",path = "/bzzoauth")
public interface OauthTokenService {

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

    /**
     * 验证token
     * @param token
     * @return
     */
    @PostMapping("/oauth/check_token")
    String checkToken(@RequestParam("token")String token);


}
