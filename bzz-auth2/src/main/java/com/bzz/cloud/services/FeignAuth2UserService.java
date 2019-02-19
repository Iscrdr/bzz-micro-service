package com.bzz.cloud.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "bzz-server-zookeeper")
public interface FeignAuth2UserService {

    @GetMapping(value = "/getUser")
    Object getUser();


}
