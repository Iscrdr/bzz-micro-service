package com.bzz.cloud.rbac.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bzz.cloud.rbac.entity.SysUser;
import com.bzz.cloud.rbac.service.SysUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.Callable;

@RestController
public class UserController  {
	
	@Resource
	private SysUserService sysUserService;
	
	@GetMapping("/getThread")
	@ResponseBody
	public Callable<Page<SysUser>> getThread(final Page<SysUser> page){
		SysUser sysUser = new SysUser();
		sysUser.setName("范冰冰");
		final QueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>();
		Callable<Page<SysUser>> callable = new Callable<Page<SysUser>>() {
			public Page<SysUser> call() throws Exception {
				
				Page<SysUser> sysUserIPage = sysUserService.selectPage(page, queryWrapper);
				return sysUserIPage;
			}
		};
		return callable;
	}
	
	@GetMapping("/getPageUser")
	@ResponseBody
	public Page<SysUser> getAllUser(Page<SysUser> page){
		SysUser sysUser = new SysUser();
		sysUser.setName("范冰冰");
		//Page<SysUser> page = new Page<SysUser>(0,1,2);
		QueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>();
		Page<SysUser> sysUserIPage = sysUserService.selectPage(page, queryWrapper);
		return sysUserIPage;
	}
	
	@GetMapping("/getOracleUser")
	@ResponseBody
	public Page<SysUser> getOracleUser(Page<SysUser> page){
		Page<SysUser> sysUserPage = sysUserService.selectPageOracle(page,null);
		return sysUserPage;
	}
	@GetMapping("/selectList")
	@ResponseBody
	public List<SysUser> selectList(Page<SysUser> page){
		Page<SysUser> sysUserPage = sysUserService.selectPage(page,null);
		return sysUserPage.getRecords();
	}
	/*
	@GetMapping("/getUser")
	@ResponseBody
	public SysUser getUser(SysUser sysUser){
		return sysUserService.getUser(sysUser);
	}
	
	@PostMapping("/addUser")
	public SysUser saveUser(){
		
		SysUser sysUser = new SysUser();
		sysUser.setId(IdUtils.uuid());
		sysUser.setEmail("624003618@qq.com");
		sysUser.setLoginFlag("Yes");
		sysUser.setMobile("15501236689");
		sysUser.setName("杨千里");
		sysUser.setLoginName("qianli");
		sysUser.setPassword("admin");
		sysUser.setPhone("15501236689");
		sysUser.setUserType("0");
		sysUser.setWorkNum("1001");
		Long startTime = System.currentTimeMillis();
		sysUserService.save(sysUser);
		Long endTime = System.currentTimeMillis();
		System.out.println("===================================================");
		System.out.println("保存用户："+sysUser.getId()+"成功，耗时: "+ (endTime-startTime));
		System.out.println("===================================================");
		return sysUser;
	}
	@PostMapping("/editUser")
	public SysUser editUser(){
		SysUser sysUser = new SysUser();
		sysUser.setId("a2cfe6b800fa4fc4b0b17fc4215dbd24");
		sysUser.setLoginName("qianli8811");
		Long startTime = System.currentTimeMillis();
		sysUserService.update(sysUser);
		Long endTime = System.currentTimeMillis();
		System.out.println("===================================================");
		System.out.println("修改用户："+sysUser.getId()+"成功，耗时: "+ (endTime-startTime));
		System.out.println("===================================================");
		return sysUser;
	}*/
}
