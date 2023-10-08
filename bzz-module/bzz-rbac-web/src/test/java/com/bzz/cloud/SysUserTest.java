package com.bzz.cloud;

import com.bzz.cloud.rbac.entity.*;
import com.bzz.cloud.rbac.service.SysGroupService;
import com.bzz.cloud.rbac.service.SysUserService;
import com.bzz.common.utils.JsonUtils;
import com.bzz.common.utils.Page;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-05-10 11-07
 * @Modified by:
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes={BzzRbacApp.class})
public class SysUserTest {

    @Autowired
    private SysGroupService sysGroupService;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 查询用户拥有的角色
     */
    @Test
    public void testFindUser()  {
        SysUser sysUser1 = new SysUser();
        sysUser1.setUserName("admin");
        Page page = new Page();
        page.setCurrent(2);
        page.setPageSize(3);

        Page page1 = sysUserService.findPage(page,sysUser1);
        System.out.println(page1!=null?page1.toString():"");

    }

    /**
     * 添加角色
     */
    @Test
    public void testAddSysUser(){
        SysUser sysUser = new SysUser();
        sysUser.setUserName("admin");
        sysUser.setEmail("624003618@qq.com");
        sysUser.setName("系统管理员");
        sysUser.setMobile("15501236689");
        sysUser.setNickName("admin1");
        sysUser.setUserType(1);
        sysUser.setRemarks("系统管理员");
        sysUser.setTodo("系统管理员");
        sysUser.setPassword("$2a$10$Ar9QTSGnaF9uwJUxgDOkB.TMEXAZ077nm7QXrLnrwpGAePjKzVTLC");
        sysUser.setEnabled(true);
        sysUser.setCredentialsNonExpired(true); //密码未过有效期
        sysUser.setAccountNonLocked(true); //账号没有被锁定
        sysUser.setAccountNonExpired(true); //账号没有过有效期
        sysUser.setCreateUserId(1124988228517109759L);
        sysUser.setUpdateUserId(1124988228517109759L);
        sysUserService.insert(sysUser);

        SysGroup sysGroup = new SysGroup();
        sysGroup.setId(1126765165765861376L);
        List<SysGroup> list = new ArrayList<>();
        list.add(sysGroup);

        sysUser.setSysGroupList(list);
        sysUserService.insertUserGroup(sysUser);


        SysUser sysUser1 = new SysUser();
        sysUser1.setUserName("bzz");
        sysUser1.setEmail("624003619@qq.com");
        sysUser1.setName("普通用户");
        sysUser1.setMobile("15501236688");
        sysUser1.setNickName("bzz");
        sysUser1.setUserType(1);
        sysUser1.setRemarks("普通用户");
        sysUser1.setTodo("普通用户");
        sysUser1.setPassword("123456");
        sysUser1.setEnabled(true);
        sysUser1.setCredentialsNonExpired(true); //密码未过有效期
        sysUser1.setAccountNonLocked(true); //账号没有被锁定
        sysUser1.setAccountNonExpired(true); //账号没有过有效期
        sysUser1.setCreateUserId(1124988228517109759L);
        sysUser1.setUpdateUserId(1124988228517109759L);
        sysUserService.insert(sysUser1);

        SysGroup sysGroup1 = new SysGroup();
        sysGroup1.setId(1126765166109794304L);

        List<SysGroup> list1 = new ArrayList<>();
        list1.add(sysGroup1);

        sysUser1.setSysGroupList(list1);
        sysUserService.insertUserGroup(sysUser1);


    }

    /**
     * 查询用户拥有的权限
     */
    @Test
    public void testFindSysAuthority(){
        SysUser sysUser1 = new SysUser();
        sysUser1.setUserName("admin");
        List<SysAuthority> sysAuthority = sysUserService.findSysAuthority(sysUser1);
        for (SysAuthority sysAuthority1:sysAuthority){
            System.out.println(sysAuthority1.getAuthority());
        }


    }
    /**
     * 查询用户拥有的权限
     */
    @Test
    public void testfindSysUserRoleApiAuthority(){
       /* SysUser sysUser1 = new SysUser();
        sysUser1.setUserName("admin");
        sysUser1 = sysUserService.findSysUserRoleApiAuthority(sysUser1);
        List<SysRole> sysRoleList = sysUser1.getSysRoleList();
        for(SysRole sysRole: sysRoleList){
            System.out.println("role:"+sysRole.getName()+","+sysRole.getRoleType());
            List<SysApi> sysApiList = sysRole.getSysApiList();
            for(SysApi sysApi:sysApiList){
                System.out.println("api:"+sysApi.getName()+","+sysApi.getPath());
                List<SysAuthority> sysAuthorityList = sysApi.getSysAuthorityList();
                for(SysAuthority sysAuthority:sysAuthorityList){
                    System.out.println("sysAuthority:"+sysAuthority.getName()+","+sysAuthority.getCode());
                }
            }
        }*/


    }
    /**
     * 查询用户拥有的角色
     */
    @Test
    public void testFindSysRole() throws JsonProcessingException {
        SysUser sysUser1 = new SysUser();
        sysUser1.setUserName("admin");
        sysUser1 = sysUserService.getUserByLoginName(sysUser1);
        sysUser1.setPassword(null);

        JsonUtils.filter(SysUser.class,null,"password,accountNonExpired,accountNonLocked,credentialsNonExpired,country,province,city,area,sysGroupList,sysRoleList,createUserId,createTime,updateUserId,updateTime,delFlag,version");
        String s = JsonUtils.object2Json(sysUser1,true);

        System.out.println(s);

    }







}
