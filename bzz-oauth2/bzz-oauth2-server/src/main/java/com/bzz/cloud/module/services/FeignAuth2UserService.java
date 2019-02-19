package com.bzz.cloud.module.services;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

//@FeignClient(value = "bzz-rbac-server",configuration = FeignHeaderInterceptor.class)
public interface FeignAuth2UserService {

    @PostMapping(value = "/getUser")
    String getUser(@RequestParam("loginName") String loginName);

}
