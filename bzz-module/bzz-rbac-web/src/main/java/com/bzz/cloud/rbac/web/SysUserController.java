package com.bzz.cloud.rbac.web;
import com.bzz.cloud.UserUtils;
import com.bzz.cloud.annotation.FieldsExclude;
import com.bzz.cloud.core.utils.RequestPage;

import com.bzz.cloud.rbac.entity.SysUser;
import com.bzz.cloud.rbac.service.SysUserService;
import com.bzz.cloud.rbac.uitls.RequestParams;
import com.bzz.common.utils.Page;
import com.bzz.common.utils.ResponseData;
import com.bzz.common.utils.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
public class SysUserController  {

    private Logger LOGGER= LoggerFactory.getLogger(SysUserController.class);

    @Autowired
    private SysUserService sysUserService;


    /**
     * @description：分页查询
     * @author     ：Iscrdr
     * @date       ：2020-06-08 19:00
     * @email      ：624003618@qq.com
     * @modified By：
     * @version:     1.0.0
     */
    @RequestMapping(value = "/user/list",method = RequestMethod.POST)
    @FieldsExclude(returnType = Page.class,exclude = "qqUser,sysGroupList,sysRoleList,beginTime,endTime,orderBy,dbType")
    public Page<SysUser> list(HttpServletRequest request, HttpServletResponse response,@RequestBody RequestPage<SysUser> requestPage) {
        Page<SysUser> page = sysUserService.findPage(requestPage.getPage(), requestPage.getBaseEntity());
        return page;
    }

    /**
     * @description：添加
     * @author     ：Iscrdr
     * @date       ：2020-06-08 19:00
     * @email      ：624003618@qq.com
     * @modified By：
     * @version:     1.0.0
     */
    @RequestMapping(value = "/user/add",method = RequestMethod.POST)
    public ResponseResult add(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid SysUser sysUser, BindingResult bindingResult) {
        ResponseResult rr = new ResponseResult();
        SysUser loginUser = UserUtils.getSysUser(request.getHeader("LoginName"));
        if (null == loginUser){
            rr.setSuccess(false);
            Map map = new HashMap<String,String>(1);
            map.put(ResponseData.USERNAME_NOT_FOUND,"用户没有登录");
            rr.setMsgMap(map);
            return rr;
        }
        /*
         * 字段规则校验
         */
        for (ObjectError error : bindingResult.getAllErrors()) {
            rr.setSuccess(false);
            Map map = new HashMap<String,String>(1);
            map.put(error.getObjectName(),error.getDefaultMessage());
            rr.setMsgMap(map);
            return rr;
        }
        /*
         * 验证用户名、邮箱、手机号是否重复
         */
        List<SysUser> list = sysUserService.selectList(sysUser);
        if(null != list && list.size()>0){
            Map map = new HashMap<String,String>(1);
            list.forEach(u->{
                if (u.getUserName().equals(sysUser.getUserName())){
                    map.put(ResponseData.NAME_CODE_ERROR,"用户名已经存在");
                }
                if (u.getEmail().equals(sysUser.getEmail())){
                    map.put(ResponseData.EMAIL_CODE_ERROR,"邮箱已经存在");
                }
                if (u.getMobile().equals(sysUser.getMobile())){
                    map.put(ResponseData.MOBILE_CODE_ERROR,"手机号已经存在");
                }
            });
            rr.setMsgMap(map);
            rr.setSuccess(false);
            return rr;
        }


        sysUser.setCreateUserId(loginUser.getCreateUserId());
        sysUser.setUpdateUserId(loginUser.getCreateUserId());

        long insert = sysUserService.insert(sysUser);
        rr.setSuccess(true);
        return rr;
    }
    /**
     * @description：修改
     * @author     ：Iscrdr
     * @date       ：2020-06-08 19:00
     * @email      ：624003618@qq.com
     * @modified By：
     * @version:     1.0.0
     */
    @RequestMapping(value = "/user/update",method = RequestMethod.POST)
    public ResponseResult update(HttpServletRequest request, HttpServletResponse response, @RequestBody SysUser sysUser) {
        ResponseResult rr = new ResponseResult();
        SysUser loginUser = UserUtils.getSysUser(request.getHeader("LoginName"));
        if (null == loginUser){
            rr.setSuccess(false);
            Map map = new HashMap<String,String>(1);
            map.put(ResponseData.USERNAME_NOT_FOUND,"用户没有登录");
            rr.setMsgMap(map);
            return rr;
        }
        sysUser.setUpdateUserId(loginUser.getCreateUserId());
        int update = sysUserService.update(sysUser);
        rr.setSuccess(true);
        return rr;
    }
    /**
     * @description：删除
     * @author     ：Iscrdr
     * @date       ：2020-06-08 19:00
     * @email      ：624003618@qq.com
     * @modified By：
     * @version:     1.0.0
     */
    @RequestMapping(value = "/user/delete",method = RequestMethod.POST)
    public ResponseResult detele(HttpServletRequest request, HttpServletResponse response,   @RequestBody RequestParams requestParams) {
        ResponseResult rr = new ResponseResult();
        SysUser loginUser = UserUtils.getSysUser(request.getHeader("LoginName"));
        if (null == loginUser){
            rr.setSuccess(false);
            Map map = new HashMap<String,String>(1);
            map.put(ResponseData.USERNAME_NOT_FOUND,"用户没有登录");
            rr.setMsgMap(map);
            return rr;
        }

        int update = sysUserService.deleteBacth(requestParams.getIds());
        rr.setSuccess(true);
        return rr;
    }

    /**
     * @description：获取当前用户信息
     * @author     ：Iscrdr
     * @date       ：2020-06-08 19:01
     * @email      ：624003618@qq.com
     * @modified By：
     * @version:     1.0.0
     */
    @GetMapping("/user/currentUser")
    @FieldsExclude(returnType = SysUser.class,exclude = "geographic,sysGroupList,sysRoleList,password,newPassword,createTime,updateTime,orderBy,qqUser,sysGroupList,sysRoleList,beginTime,endTime")
    public SysUser currentUser(HttpServletRequest request,HttpServletResponse response,SysUser sysUser){
        SysUser sysUser1 = sysUserService.getUserByLoginName(sysUser);
        return sysUser1;
    }
}
