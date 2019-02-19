package com.bzz.cloud.core.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name  = "bzzoauth")
public interface Oath2FeignClientService {

    /**
     * 获取token
     * @return
     */
    @PostMapping(value = "/oauth/token")
    String getToken();

    /**
     * 验证token
     */
    @PostMapping(value = "/oauth/check_token")
    String checkToken(@RequestParam("token") String token);

    /**
     * 获取客户端
     */
    @PostMapping(value = "/getAllClient")
    String getAllClient();


}
