package com.bzz.cloud.rbac.web;

import com.bzz.cloud.rbac.entity.SysUser;
import com.bzz.cloud.rbac.service.SysUserService;

import java.util.List;
import com.bzz.common.Utils.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
		Callable<Page<SysUser>> callable = new Callable<Page<SysUser>>() {
			public Page<SysUser> call() throws Exception {
				
				Page<SysUser> sysUserIPage = sysUserService.selectPageMysql(sysUser);
				return sysUserIPage;
			}
		};
		return callable;
	}
	
	@GetMapping("/selectUser")
	@ResponseBody
	public SysUser getAllUser(Page<SysUser> page){
		SysUser sysUser = new SysUser();
		sysUser.setName("范冰冰");
		//Page<SysUser> page = new Page<SysUser>(0,1,2);

		page.setPageNum(1);
		page.setPageSize(3);
		sysUser.setPage(page);
		SysUser sysUser1 = sysUserService.selectUser(sysUser);
		System.out.println(sysUser1);
		return sysUser1;
	}
	
	@GetMapping("/getOracleUser")
	@ResponseBody
	public Page<SysUser> getOracleUser(Page<SysUser> page){
		//Page<SysUser> page = new Page();
		page.setPageNum(1);
		page.setPageSize(3);

		SysUser sysUser = new SysUser();
		sysUser.setPage(page);
		Page<SysUser> sysUserPage = sysUserService.selectPageOracle(sysUser);
		return sysUserPage;
	}
	@GetMapping("/selectList")
	@ResponseBody
	public List<SysUser> selectList(Page<SysUser> page){
		page.setPageNum(1);
		page.setPageSize(3);

		SysUser sysUser = new SysUser();
		sysUser.setPage(page);
		List<SysUser> list = sysUserService.selectList(sysUser);
		return list;
	}

}
