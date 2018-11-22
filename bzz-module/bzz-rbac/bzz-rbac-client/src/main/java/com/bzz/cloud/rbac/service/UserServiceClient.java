package com.bzz.cloud.rbac.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "bzz-server-zookeeper")
public interface UserServiceClient {
	
	@GetMapping(value = "/getPageUser")
	Object getUser();
}
