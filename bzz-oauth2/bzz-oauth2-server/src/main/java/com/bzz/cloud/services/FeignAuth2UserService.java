package com.bzz.cloud.services;

import com.bzz.cloud.interceptor.FeignHeaderInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "bzz-rbac-server",configuration = FeignHeaderInterceptor.class)
public interface FeignAuth2UserService {

    @PostMapping(value = "/getUser")
    String getUser(@RequestParam("loginName") String loginName);

}
