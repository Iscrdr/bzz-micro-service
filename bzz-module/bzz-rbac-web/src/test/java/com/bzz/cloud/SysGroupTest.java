package com.bzz.cloud;

import com.bzz.cloud.rbac.entity.SysGroup;
import com.bzz.cloud.rbac.entity.SysRole;
import com.bzz.cloud.rbac.entity.SysUser;
import com.bzz.cloud.rbac.service.SysGroupService;
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
public class SysGroupTest {

    @Autowired
    private SysGroupService sysGroupService;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 添加角色
     */
    @Test
    public void testAddSysGroup(){
        SysGroup sysGroup = new SysGroup();
        sysGroup.setName("公司总部");
        sysGroup.setSort(1);
        sysGroup.setAddress("北京海淀上地七街");
        sysGroup.setEmail("624003618@qq.com");
        sysGroup.setGroupType(1);
        sysGroup.setGrade(1);
        sysGroup.setEnabled(true);
        sysGroup.setMaster("admin");
        sysGroup.setCreateUserId(1126741685360009216L);
        sysGroup.setUpdateUserId(1126741685360009216L);

        SysGroup parentSysGroup = new SysGroup();
        parentSysGroup.setId(0L);

        sysGroup.setParentId(parentSysGroup);
        sysGroupService.insert(sysGroup);


        SysGroup sysGroup1 = new SysGroup();
        sysGroup1.setName("技术部");
        sysGroup1.setSort(1);
        sysGroup1.setAddress("北京海淀上地七街");
        sysGroup1.setEmail("624003618@qq.com");
        sysGroup1.setGroupType(1);
        sysGroup1.setGrade(2);
        sysGroup1.setEnabled(true);
        sysGroup1.setMaster("admin");
        sysGroup1.setCreateUserId(1126741685360009216L);
        sysGroup1.setUpdateUserId(1126741685360009216L);
        sysGroup1.setParentId(sysGroup);
        sysGroupService.insert(sysGroup1);

        SysGroup sysGroup11 = new SysGroup();
        sysGroup11.setName("JAVA研发部");
        sysGroup11.setSort(1);
        sysGroup11.setAddress("北京海淀上地七街");
        sysGroup11.setEmail("624003618@qq.com");
        sysGroup11.setGroupType(1);
        sysGroup11.setGrade(3);
        sysGroup11.setEnabled(true);
        sysGroup11.setMaster("admin");
        sysGroup11.setCreateUserId(1126741685360009216L);
        sysGroup11.setUpdateUserId(1126741685360009216L);
        sysGroup11.setParentId(sysGroup1);
        sysGroupService.insert(sysGroup11);


        SysGroup sysGroup12 = new SysGroup();
        sysGroup12.setName("WEB开发部");
        sysGroup12.setSort(1);
        sysGroup12.setAddress("北京海淀上地七街");
        sysGroup12.setEmail("624003618@qq.com");
        sysGroup12.setGroupType(1);
        sysGroup12.setGrade(3);
        sysGroup12.setEnabled(true);
        sysGroup12.setMaster("admin");
        sysGroup12.setCreateUserId(1126741685360009216L);
        sysGroup12.setUpdateUserId(1126741685360009216L);
        sysGroup12.setParentId(sysGroup1);
        sysGroupService.insert(sysGroup12);



        SysGroup sysGroup2 = new SysGroup();
        sysGroup2.setName("财务部");
        sysGroup2.setSort(2);
        sysGroup2.setAddress("北京海淀上地七街");
        sysGroup2.setEmail("624003618@qq.com");
        sysGroup2.setGroupType(1);
        sysGroup2.setGrade(2);
        sysGroup2.setEnabled(true);
        sysGroup2.setMaster("admin");
        sysGroup2.setCreateUserId(1126741685360009216L);
        sysGroup2.setUpdateUserId(1126741685360009216L);
        sysGroup2.setParentId(sysGroup);
        sysGroupService.insert(sysGroup2);


        SysGroup sysGroup3 = new SysGroup();
        sysGroup3.setName("运维部");
        sysGroup3.setSort(3);
        sysGroup3.setAddress("北京海淀上地七街");
        sysGroup3.setEmail("624003618@qq.com");
        sysGroup3.setGroupType(1);
        sysGroup3.setGrade(2);
        sysGroup3.setEnabled(true);
        sysGroup3.setMaster("admin");
        sysGroup3.setCreateUserId(1126741685360009216L);
        sysGroup3.setUpdateUserId(1126741685360009216L);
        sysGroup3.setParentId(sysGroup);
        sysGroupService.insert(sysGroup3);
    }

    /**
     * 将角色分配给用户组
     */
    @Test
    public void testGroupRole(){
        SysGroup sysGroup1 = new SysGroup();
        sysGroup1.setId(1126765166109794304L);

        SysRole sysRole1 = new SysRole();
        sysRole1.setId(1126741685360009216L);
        SysRole sysRole2 = new SysRole();
        sysRole2.setId(1126741685666193408L);
        List<SysRole> sysRoleList = new ArrayList<>();
        sysRoleList.add(sysRole1);
        sysRoleList.add(sysRole2);
        //sysGroup1.setSysRoleList(sysRoleList);
        sysGroupService.insertGroupRole(sysGroup1);


    }


}
