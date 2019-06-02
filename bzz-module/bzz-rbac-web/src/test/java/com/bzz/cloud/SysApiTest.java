package com.bzz.cloud;

import com.bzz.cloud.rbac.entity.SysApi;
import com.bzz.cloud.rbac.entity.SysAuthority;
import com.bzz.cloud.rbac.entity.SysRole;
import com.bzz.cloud.rbac.entity.SysUser;
import com.bzz.cloud.rbac.service.SysApiService;
import com.bzz.cloud.rbac.service.SysAuthorityService;
import com.bzz.common.Utils.JsonUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-05-10 11-07
 * @Modified by:
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SysApiTest {

    @Autowired
    private SysApiService sysApiService;

    @Autowired
    private SysAuthorityService sysAuthorityService;

    @Test
    public void testAddSysApi(){
        SysApi sysApi0 = new SysApi();
        sysApi0.setId(0L);
        SysApi sysApi = new SysApi();
        sysApi.setSysApi(sysApi0);
        sysApi.setMenu(true);
        sysApi.setPath("/");
        sysApi.setRedirect("/dashboard/analysis");

        sysApi.setCreateUserId(1124988228517109759L);
        sysApi.setUpdateUserId(1124988228517109759L);
        sysApi.setRemarks("系统管理员");
        sysApi.setTodo("系统管理员");
        sysApiService.insert(sysApi);

        SysApi sysApi7 = new SysApi();
        sysApi7.setSysApi(sysApi0);
        sysApi7.setMenu(true);
        sysApi7.setPath("/sys");
        sysApi7.setComponent("/sys");
        sysApi7.setName("系统管理");
        sysApi7.setIcon("fa fa-windows");
        sysApi7.setHideChildrenInMenu(false);
        sysApi7.setCreateUserId(1124988228517109759L);
        sysApi7.setUpdateUserId(1124988228517109759L);
        sysApi7.setRemarks("系统管理员");
        sysApi7.setTodo("系统管理员");
        sysApiService.insert(sysApi7);

        SysApi sysApi1 = new SysApi();
        sysApi1.setSysApi(sysApi0);
        sysApi1.setMenu(true);
        sysApi1.setPath("/rbac");
        sysApi1.setComponent("/rbac");
        sysApi1.setName("权限管理");
        sysApi1.setIcon("fa fa-windows");
        sysApi1.setHideChildrenInMenu(false);
        sysApi1.setCreateUserId(1124988228517109759L);
        sysApi1.setUpdateUserId(1124988228517109759L);
        sysApi1.setRemarks("系统管理员");
        sysApi1.setTodo("系统管理员");
        sysApiService.insert(sysApi1);


        SysApi sysApi2 = new SysApi();
        sysApi2.setSysApi(sysApi1);
        sysApi2.setMenu(true);
        sysApi2.setPath("/rbac/user");
        sysApi2.setComponent("/rbac/user");
        sysApi2.setName("用户管理");
        sysApi2.setIcon("fa fa-address-card-o");
        sysApi2.setHideChildrenInMenu(false);
        sysApi2.setCreateUserId(1124988228517109759L);
        sysApi2.setUpdateUserId(1124988228517109759L);
        sysApi2.setRemarks("系统管理员");
        sysApi2.setTodo("系统管理员");
        sysApiService.insert(sysApi2);

        SysApi sysApi3 = new SysApi();
        sysApi3.setSysApi(sysApi1);
        sysApi3.setMenu(true);
        sysApi3.setPath("/rbac/dept");
        sysApi3.setComponent("/rbac/dept");
        sysApi3.setName("机构管理");
        sysApi3.setIcon("fa fa-address-card-o");
        sysApi3.setHideChildrenInMenu(false);
        sysApi3.setCreateUserId(1124988228517109759L);
        sysApi3.setUpdateUserId(1124988228517109759L);
        sysApi3.setRemarks("系统管理员");
        sysApi3.setTodo("系统管理员");
        sysApiService.insert(sysApi3);

        SysApi sysApi4 = new SysApi();
        sysApi4.setSysApi(sysApi1);
        sysApi4.setMenu(true);
        sysApi4.setPath("/rbac/role");
        sysApi4.setComponent("/rbac/role");
        sysApi4.setName("角色管理");
        sysApi4.setIcon("fa fa-address-card-o");
        sysApi4.setHideChildrenInMenu(false);
        sysApi4.setCreateUserId(1124988228517109759L);
        sysApi4.setUpdateUserId(1124988228517109759L);
        sysApi4.setRemarks("系统管理员");
        sysApi4.setTodo("系统管理员");
        sysApiService.insert(sysApi4);

    }

    @Test
    public void testUpdateSysApi(){


        SysApi sysApi = sysApiService.get(1126694201015279616L);

        sysApi.setUpdateUserId(1124988228517109759L);
        sysApi.setRemarks("系统管理员");
        sysApi.setTodo("系统管理员");
        sysApiService.update(sysApi);

    }

    /**
     * 查询用户所有的菜单
     */
    @Test
    public void testAllSysApi(){
        SysUser sysUser1 = new SysUser();
        sysUser1.setUserName("admin");
        SysUser sysUser2 = sysApiService.getAllApi(sysUser1);
        List<SysRole> sysRoleList = sysUser2.getSysRoleList();

        List<SysApi> sysApiList = new ArrayList<>();
        for (SysRole role:sysRoleList){
            System.out.println(role.getRoleType()+",");
            List<SysApi> roleApiList = role.getSysApiList();
            sysApiList.addAll(roleApiList);
        }
        SysApi rootApi = new SysApi();
        rootApi.setId(0L);
        List<SysApi> sysApis = treeApiList(sysApiList,rootApi);
        Collections.sort(sysApis);
        JsonUtils.filter(SysApi.class,null,"sysAuthorityList,sysApi,createUserId,createTime,updateUserId,updateTime,delFlag,version,todo,remarks");
        String s = JsonUtils.object2Json(sysApis);
        System.out.println(s);


    }

    /**
     * 获取某个父节点下面的所有子节点
     * @param sysApiList
     * @param parentApi
     * @return
     */
    private   List<SysApi> treeApiList( List<SysApi> sysApiList, SysApi parentApi){
        List<SysApi> apiList = new ArrayList<>();
        for(SysApi api: sysApiList){
            //遍历出父id等于参数的id，add进子节点集合
            if(null != api && api.getSysApi()!=null
                    && api.getSysApi().getId()!=null
                    && parentApi != null
                    && parentApi.getId() != null
                    && api.getSysApi().getId().equals(parentApi.getId())
            ){
                List<SysApi> childApi = treeApiList(sysApiList, api);
                api.setRoutes(childApi);
                //递归遍历下一级
                apiList.add(api);
            }
        }
        return apiList;
    }

}
