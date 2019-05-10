package com.bzz.cloud;

import com.bzz.cloud.rbac.entity.SysApi;
import com.bzz.cloud.rbac.entity.SysAuthority;
import com.bzz.cloud.rbac.service.SysApiService;
import com.bzz.cloud.rbac.service.SysAuthorityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
public class SysApiTest {

    @Autowired
    private SysApiService sysApiService;

    @Autowired
    private SysAuthorityService sysAuthorityService;

    @Test
    public void testAddSysApi(){
        SysApi sysApi = new SysApi();
        sysApi.setName("当前用户信息");
        sysApi.setShow(true);
        sysApi.setUrl("/currentUser");
        sysApi.setSort(1);
        sysApi.setIcon("");

        sysApi.setCreateUserId(1124988228517109759L);
        sysApi.setUpdateUserId(1124988228517109759L);
        sysApi.setRemarks("系统管理员");
        sysApi.setTodo("系统管理员");

        sysApiService.insert(sysApi);


        SysAuthority sysAuthority2 = new SysAuthority();
        sysAuthority2.setName("插入");
        sysAuthority2.setCode("INSERT");
        sysAuthority2.setCreateUserId(1124988228517109759L);
        sysAuthority2.setUpdateUserId(1124988228517109759L);
        sysAuthority2.setRemarks("插入");
        sysAuthority2.setTodo("插入");
        sysAuthority2.setSysApi(sysApi);
        sysAuthorityService.insert(sysAuthority2);

        SysAuthority sysAuthority3 = new SysAuthority();
        sysAuthority3.setName("查询");
        sysAuthority3.setCode("FIND");
        sysAuthority3.setCreateUserId(1124988228517109759L);
        sysAuthority3.setUpdateUserId(1124988228517109759L);
        sysAuthority3.setRemarks("查询");
        sysAuthority3.setTodo("查询");
        sysAuthority3.setSysApi(sysApi);

        sysAuthorityService.insert(sysAuthority3);


    }

    @Test
    public void testUpdateSysApi(){


        SysApi sysApi = sysApiService.get(1126694201015279616L);

        sysApi.setUpdateUserId(1124988228517109759L);
        sysApi.setRemarks("系统管理员");
        sysApi.setTodo("系统管理员");
        sysApiService.update(sysApi);

    }
}
