/**
 * Copyright &copy; 2012-2019 <a href="https://github.com/qianli8811/bzz-cloud">bzz-cloud</a> All rights reserved.
 */
package com.bzz.cloud.rbac.web;


import com.bzz.cloud.rbac.entity.SysArea;
import com.bzz.cloud.rbac.service.SysAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @desc: 行政区域
 *
 * @Auther: yang qianli
 * @Email: 624003618@qq.com
 * @createDate: 2019-05-20 16:25:10
 * @updateDate: 2019-05-20 16:25:10
 */
@Controller
@RequestMapping(value = "/rbac")
public class SysAreaController  {

	@Autowired
	private SysAreaService sysAreaService;
	

	
}