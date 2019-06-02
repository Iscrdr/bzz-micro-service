package com.bzz.cloud.rbac.web;

import com.bzz.cloud.annotation.FieldsExclude;
import com.bzz.cloud.rbac.entity.SysApi;
import com.bzz.cloud.rbac.entity.SysAuthority;
import com.bzz.cloud.rbac.entity.SysRole;
import com.bzz.cloud.rbac.entity.SysUser;
import com.bzz.cloud.rbac.service.PrincipalFeignService;
import com.bzz.cloud.rbac.service.SysUserService;
import com.bzz.common.Utils.ResponseResult;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonView;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-05-06 14-35
 * @Modified by:
 * @Description:
 */
@RestController
public class AccountController {

    private Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private PrincipalFeignService principalFeignService;

    @PostMapping("/account/settings/base")
    @ResponseBody
    public ResponseResult loginout(HttpServletRequest request, HttpServletResponse response, BindingResult result){
        ResponseResult rr = new ResponseResult();


        return rr;
    }

    @GetMapping("/currentUser")
    @ResponseBody
    public SysUser currentUser(HttpServletRequest request,HttpServletResponse response){

        SysUser sysUser = null ;
        String principal;
        try {
            principal = principalFeignService.getPrincipal();
            sysUser = new SysUser();
            if(StringUtils.isNotBlank(principal)){
                sysUser.setUserName(principal);
                sysUser = sysUserService.getUserByLoginName(sysUser);
                sysUser.setPassword(null);
                sysUser.setNewPassword(null);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sysUser;
    }

    @GetMapping("/getUser")
    @FieldsExclude(returnType = SysUser.class,exclude = "accountNonExpired,accountNonLocked,credentialsNonExpired,createUserId,createTime,updateUserId,updateTime,delFlag,version")
    public SysUser getUser(HttpServletRequest request,HttpServletResponse response){

        SysUser sysUser = null ;
        String principal;
        try {
            principal = principalFeignService.getPrincipal();
            sysUser = new SysUser();
            if(StringUtils.isNotBlank(principal)){
                sysUser.setUserName(principal);
                sysUser = sysUserService.getUserByLoginName(sysUser);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sysUser;
    }


    @GetMapping("/getMenuData")
    @FieldsExclude(returnType = SysUser.class,exclude = "sysAuthorityList,sysApi,createUserId,createTime,updateUserId,updateTime,delFlag,version,todo,remarks")
    public List<SysApi> getMenuData(HttpServletRequest request,HttpServletResponse response){

        SysUser sysUser = null ;
        String principal;
        try {
            principal = principalFeignService.getPrincipal();
            sysUser = new SysUser();
            if(StringUtils.isNotBlank(principal)){
                sysUser.setUserName(principal);
                List<SysRole> sysRoleList = sysUser.getSysRoleList();
                List<String> authority = new ArrayList<>();
                List<SysApi> sysApiList = new ArrayList<>();
                for(SysRole role:sysRoleList){
                    authority.add(role.getRoleType());//角色权限
                    List<SysApi> sysRoleApiList = role.getSysApiList();
                    sysApiList.addAll(sysRoleApiList);
                }
                SysApi rootApi = new SysApi();
                rootApi.setId(0L);
                List<SysApi> sysApis = treeApiList(sysApiList, rootApi);
                Collections.sort(sysApis);
                return sysApis;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
