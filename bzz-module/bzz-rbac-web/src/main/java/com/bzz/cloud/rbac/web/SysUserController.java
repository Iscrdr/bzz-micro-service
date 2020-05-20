package com.bzz.cloud.rbac.web;
import com.bzz.cloud.annotation.FieldsExclude;
import com.bzz.cloud.core.utils.RequestPage;
import com.bzz.cloud.core.web.BaseController;

import com.bzz.cloud.rbac.dao.SysUserDao;
import com.bzz.cloud.rbac.entity.SysUser;
import com.bzz.cloud.rbac.service.SysUserService;
import com.bzz.common.Utils.Page;
import com.bzz.common.Utils.ResponseResult;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @description：用户CRUD
 * @author     ：Iscrdr
 * @email      ：624003618@qq.com
 * @date       ：2020-04-23 02:01
 * @modified By：
 * @version: 1.0.0
 */
@RestController
@RequestMapping("/rbac")
public class SysUserController extends BaseController<SysUserService, SysUserDao,SysUser> {

    @Autowired
    private SysUserService sysUserService;


    @RequestMapping(value = "/user/list",method = RequestMethod.POST)
    @Override
    @FieldsExclude(returnType = Page.class,exclude = "qqUser,sysGroupList,sysRoleList,beginTime,endTime,orderBy,dbType")
    public Page list(HttpServletRequest request, HttpServletResponse response,@RequestBody RequestPage<SysUser> requestPage) {
        return super.list(request, response,requestPage);
    }

    @RequestMapping(value = "/user/add",method = RequestMethod.POST)
    @Override
    public ResponseResult add(HttpServletRequest request, HttpServletResponse response, SysUser sysUser) {
        return super.add(request, response, sysUser);
    }
    @RequestMapping(value = "/user/update",method = RequestMethod.POST)
    @Override
    public ResponseResult update(HttpServletRequest request, HttpServletResponse response, SysUser sysUser) {
        return super.update(request, response, sysUser);
    }
    @RequestMapping(value = "/user/detele",method = RequestMethod.POST)
    @Override
    public ResponseResult detele(HttpServletRequest request, HttpServletResponse response, SysUser sysUser) {
        return super.detele(request, response, sysUser);
    }


    @GetMapping("/user/currentUser")
    @FieldsExclude(returnType = SysUser.class,exclude = "geographic,sysGroupList,sysRoleList,password,newPassword,currentPage,current,pageSize,createTime,updateTime,orderBy,qqUser,qqUser,sysGroupList,sysRoleList,beginTime,endTime,orderBy")
    public SysUser currentUser(HttpServletRequest request,HttpServletResponse response,SysUser sysUser){
        return sysUserService.getUserByLoginName(sysUser);
    }
}
