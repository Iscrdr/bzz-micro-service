package com.bzz.cloud.rbac.web;

import com.bzz.cloud.rbac.service.UserServiceClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.zookeeper.discovery.ZookeeperDiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class UserController {
	@Value("${spring.application.name}")
	private String instanceName;
	
	private String serviceName = "bzz-rbac-server";
	
	@Autowired
	private ZookeeperDiscoveryClient zookeeperDiscoveryClient;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	LoadBalancerClient loadBalancerClient;

	@Autowired
	private UserServiceClient userServiceClient;


	@PostMapping("/getUser")
	@ResponseBody
	public Object getUser(@RequestParam("loginName") String loginName) {

		Object user = userServiceClient.getUser(loginName);
		System.out.println(user==null?0:user.toString());
		return user;
	}
	
	@RequestMapping("/serviceUrl")
	@ResponseBody
	public Object serviceUrl() {
		
		List<ServiceInstance> instances = zookeeperDiscoveryClient.getInstances("bzz-rbac-server");
		for (int i = 0; i<instances.size();i++){
			ServiceInstance serviceInstance = instances.get(i);
			System.out.println(serviceInstance.getServiceId());
			System.out.println(serviceInstance.getHost()+":"+serviceInstance.getPort());
			System.out.println(serviceInstance.getUri());
			System.out.println(serviceInstance.getMetadata().toString());
			
			System.out.println("==========================");
		}
		return instances;
	}
	
	
	//@RequestMapping("/getPageUser")
	@GetMapping("/getPageUser")
	@ResponseBody
	public Object getPageUser(@RequestParam(value="current") Integer  current, @RequestParam(value="size") Integer  size) {

		ServiceInstance serviceInstance = loadBalancerClient.choose(serviceName);
		System.out.println("api --> getPageUser,serviceInstance:"+serviceInstance);
		System.out.println(loadBalancerClient.choose(serviceName));
		return restTemplate.getForObject("http://"+serviceName+"/getPageUser?current="+current+"&size="+size,String.class);
	}
	//@RequestMapping("/getOracleUser")
	@GetMapping("/getOracleUser")
	@ResponseBody
	public Object getOracleUser(@RequestParam(value="current") Integer  current, @RequestParam(value="size") Integer  size) {
		ServiceInstance serviceInstance = loadBalancerClient.choose(serviceName);
		System.out.println("api --> getOracleUser,serviceInstance:"+serviceInstance);
		return restTemplate.getForObject("http://"+serviceName+"/getPageUser?current="+current+"&size="+size,String.class);
	}
}
