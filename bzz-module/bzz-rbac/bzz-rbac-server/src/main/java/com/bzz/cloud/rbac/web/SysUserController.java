package com.bzz.cloud.rbac.web;

import com.bzz.cloud.rbac.entity.SysUser;
import com.bzz.cloud.rbac.service.SysUserService;


import com.bzz.common.Utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.Callable;
@RestController("/sys")
public class SysUserController  {
	
	@Autowired
	private SysUserService sysUserService;

	@PostMapping("/getUser")
	public SysUser getUser(SysUser sysUser){
		SysUser sysUser1 = sysUserService.getUserByLoginName(sysUser);
		return sysUser1;
	}

	@GetMapping("/getThread")
	public Callable<Page<SysUser>> getThread(final SysUser sysUser){
		sysUser.setName("范冰冰");
		Callable<Page<SysUser>> callable = new Callable<Page<SysUser>>() {
			public Page<SysUser> call() throws Exception {
				Page<SysUser> sysUserIPage = sysUserService.selectPageMysql(sysUser);
				return sysUserIPage;
			}
		};
		return callable;
	}

	
	@GetMapping("/getOracleUser")
	public Page<SysUser> getOracleUser(SysUser sysUser){
		Page<SysUser> sysUserPage = sysUserService.selectPageOracle(sysUser);
		return sysUserPage;
	}
	@GetMapping("/getMysqlUser")
	public Page<SysUser> getMysqlUser(SysUser sysUser){
		Page<SysUser> sysUserPage = sysUserService.selectPageMysql(sysUser);
		return sysUserPage;
	}
	@GetMapping("/selectList")
	public Page<SysUser> selectList(SysUser sysUser){
		Page<SysUser> sysUserIPage  = sysUserService.selectPageMysql(sysUser);
		return sysUserIPage;
	}
	@PostMapping("/register")
	public String register(SysUser sysUser){
		System.out.println(sysUser.toString());
		return "注册成功";
	}
}
