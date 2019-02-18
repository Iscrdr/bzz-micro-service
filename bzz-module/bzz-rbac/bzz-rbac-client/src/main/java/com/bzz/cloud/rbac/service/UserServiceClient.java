package com.bzz.cloud.rbac.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "bzz-rbac-server")
public interface UserServiceClient {
	
	@PostMapping(value = "/getUser")
	Object getUser(@RequestParam("loginName") String loginName);
}
