package com.bzz.cloud;

import com.bzz.cloud.core.entity.BaseEntity;
import com.bzz.cloud.rbac.entity.SysAuthority;
import com.bzz.cloud.rbac.service.SysAuthorityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-05-10 11-07
 * @Modified by:
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes={BzzRbacApp.class})
public class AuthorityTest {

    @Autowired
    private SysAuthorityService sysAuthorityService;

    @Test
    public void testAddAuthority(){
        /*SysAuthority sysAuthority1 = new SysAuthority();

        sysAuthority1.setName("添加");
        sysAuthority1.setCode("ADD");
        sysAuthority1.setCreateUserId(1124988228517109759L);
        sysAuthority1.setUpdateUserId(1124988228517109759L);
        sysAuthority1.setRemarks("添加");
        sysAuthority1.setTodo("添加");
        sysAuthorityService.insert(sysAuthority1);*/

        SysAuthority sysAuthority2 = new SysAuthority();
        sysAuthority2.setName("插入");
        sysAuthority2.setCode("INSERT");
        sysAuthority2.setCreateUserId(1124988228517109759L);
        sysAuthority2.setUpdateUserId(1124988228517109759L);
        sysAuthority2.setRemarks("插入");
        sysAuthority2.setTodo("插入");
        sysAuthorityService.insert(sysAuthority2);

        SysAuthority sysAuthority3 = new SysAuthority();
        sysAuthority3.setName("修改");
        sysAuthority3.setCode("EDIT");
        sysAuthority3.setCreateUserId(1124988228517109759L);
        sysAuthority3.setUpdateUserId(1124988228517109759L);
        sysAuthority3.setRemarks("修改");
        sysAuthority3.setTodo("修改");
        sysAuthorityService.insert(sysAuthority3);

        SysAuthority sysAuthority4 = new SysAuthority();
        sysAuthority4.setName("更新");
        sysAuthority4.setCode("UPDATE");
        sysAuthority4.setCreateUserId(1124988228517109759L);
        sysAuthority4.setUpdateUserId(1124988228517109759L);
        sysAuthority4.setRemarks("更新");
        sysAuthority4.setTodo("更新");
        sysAuthorityService.insert(sysAuthority4);

        SysAuthority sysAuthority5 = new SysAuthority();
        sysAuthority5.setName("删除");
        sysAuthority5.setCode("DELETE");
        sysAuthority5.setCreateUserId(1124988228517109759L);
        sysAuthority5.setUpdateUserId(1124988228517109759L);
        sysAuthority5.setRemarks("删除");
        sysAuthority5.setTodo("删除");
        sysAuthorityService.insert(sysAuthority5);

        SysAuthority sysAuthority6 = new SysAuthority();
        sysAuthority6.setName("查询");
        sysAuthority6.setCode("SELECT");
        sysAuthority6.setCreateUserId(1124988228517109759L);
        sysAuthority6.setUpdateUserId(1124988228517109759L);
        sysAuthority6.setRemarks("查询");
        sysAuthority6.setTodo("查询");
        sysAuthorityService.insert(sysAuthority6);

        SysAuthority sysAuthority7 = new SysAuthority();
        sysAuthority7.setName("查找");
        sysAuthority7.setCode("FIND");
        sysAuthority7.setCreateUserId(1124988228517109759L);
        sysAuthority7.setUpdateUserId(1124988228517109759L);
        sysAuthority7.setRemarks("查找");
        sysAuthority7.setTodo("查找");
        sysAuthorityService.insert(sysAuthority7);

        SysAuthority sysAuthority8 = new SysAuthority();
        sysAuthority8.setName("查询");
        sysAuthority8.setCode("QUERY");
        sysAuthority8.setCreateUserId(1124988228517109759L);
        sysAuthority8.setUpdateUserId(1124988228517109759L);
        sysAuthority8.setRemarks("查询");
        sysAuthority8.setTodo("查询");
        sysAuthorityService.insert(sysAuthority8);

    }

    @Test
    public void testUpdateAuthority(){


        SysAuthority sysAuthority = sysAuthorityService.get(1126694201015279616L);

        sysAuthority.setUpdateUserId(1124988228517109759L);
        sysAuthority.setRemarks("系统管理员");
        sysAuthority.setTodo("系统管理员");
        sysAuthorityService.update(sysAuthority);

    }
}
