package com.bzz.cloud;

import com.bzz.cloud.rbac.entity.*;
import com.bzz.cloud.rbac.service.SysAuthorityService;
import com.bzz.cloud.rbac.service.SysRoleService;
import com.bzz.cloud.rbac.service.SysUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-05-10 11-07
 * @Modified by:
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes={BzzRbacApp.class})
public class SysRoleTest {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 添加角色
     */
    @Test
    public void testAddRole(){
        SysRole role = new SysRole();
        role.setSys(true);
        role.setName("系统管理员");
        role.setRoleType("SYS");
        role.setEnabled(true);

        role.setCreateUserId(1124988228517109759L);
        role.setUpdateUserId(1124988228517109759L);
        role.setRemarks("系统管理员");
        role.setTodo("系统管理员");
        sysRoleService.insert(role);


        SysRole role2 = new SysRole();
        role2.setSys(false);
        role2.setName("普通用户");
        role2.setRoleType("USER");
        role2.setEnabled(true);

        role2.setCreateUserId(1124988228517109759L);
        role2.setUpdateUserId(1124988228517109759L);
        role2.setRemarks("普通用户");
        role2.setTodo("普通用户");
        sysRoleService.insert(role2);
    }

    /**
     * 将角色分配给用户
     */
    @Test
    public void testRoleUser(){
        SysUser sysUser = new SysUser();
        sysUser.setUserName("qianli8822");
        sysUser.setEmail("624003611@qq.com");
        List<SysUser> list = sysUserService.selectList(sysUser);

        SysRole role1 = new SysRole();
        role1.setId(1126741685360009216L);

        role1.setSysUserList(list);

        sysRoleService.insertRoleUser(role1);

    }

    /**
     * 为角色分配权限
     */
    @Test
    public void testRoleUserGroup(){
        SysRole role1 = new SysRole();
        role1.setId(1126741685360009216L);

        SysApi sysApi = new SysApi();
        sysApi.setId(1126739319122436096L);

        List<SysApi> list = new ArrayList<>();
        list.add(sysApi);
        role1.setSysApiList(list);

        sysRoleService.insertRoleApi(role1);

    }



}
