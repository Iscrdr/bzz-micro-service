/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.bzz.cloud.rbac.web;

import com.bzz.cloud.rbac.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @desc: 角色管理
 *
 * @Auther: yang qianli
 * @Email: 624003618@qq.com
 * @createDate: 2019-05-10 00:01:58
 * @updateDate: 2019-05-10 00:01:58
 */
@Controller
@RequestMapping(value = "/rbac")
public class SysRoleController  {

	@Autowired
	private SysRoleService sysRoleService;
	

	
}