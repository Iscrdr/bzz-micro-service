package com.bzz.cloud;

import com.bzz.cloud.rbac.entity.SysUser;
import com.bzz.cloud.rbac.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author ：Iscrdr
 * @description：获取当前用户
 * @email ：624003618@qq.com
 * @date ：2020-06-08 03:55
 * @modified By：
 * @version: 1.0.0
 */
@Component
public class UserUtils {

    @Autowired
    private  SysUserService sysUserService;


    private static SysUserService userService;

    public static SysUser getSysUser(String userName){
        SysUser sysUser =  new SysUser();
        sysUser.setUserName(userName);
        return  userService.getUserByLoginName(sysUser);
    }

    @PostConstruct
    public void init(){
        userService = this.sysUserService;
    }



}
