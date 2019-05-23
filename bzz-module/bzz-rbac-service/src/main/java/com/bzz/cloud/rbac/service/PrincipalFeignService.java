package com.bzz.cloud.rbac.service;

import com.bzz.cloud.config.Oauth2FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-05-19 00-23
 * @Modified by:
 * @Description:
 */

@FeignClient(value = "oauthservice",path = "/bzzoauth",configuration = Oauth2FeignClientConfiguration.class)
public interface  PrincipalFeignService {

    @GetMapping("/getPrincipal")
    String getPrincipal() throws Exception;
}
