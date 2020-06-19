package com.bzz.cloud.rbac.web;

import com.bzz.cloud.annotation.FieldsExclude;
import com.bzz.cloud.rbac.entity.SysMenu;
import com.bzz.cloud.rbac.entity.SysRole;
import com.bzz.cloud.rbac.entity.SysUser;
import com.bzz.cloud.rbac.service.Oauth2FeignClientService;
import com.bzz.cloud.rbac.service.SysUserService;
import com.bzz.common.utils.ResponseResult;
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
    private Oauth2FeignClientService oauth2FeignClientService;

    @PostMapping("/account/settings/base")
    @ResponseBody
    public ResponseResult loginout(HttpServletRequest request, HttpServletResponse response, BindingResult result){
        ResponseResult rr = new ResponseResult();


        return rr;
    }



    @GetMapping("/getUser")
    @FieldsExclude(returnType = SysUser.class,exclude = "accountNonExpired,accountNonLocked,credentialsNonExpired,createUserId,createTime,updateUserId,updateTime,delFlag,version")
    public SysUser getUser(HttpServletRequest request,HttpServletResponse response){

        SysUser sysUser = null ;
        String principal;
        try {
            principal = oauth2FeignClientService.currentUser();
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
    public List<SysMenu> getMenuData(HttpServletRequest request, HttpServletResponse response){

        SysUser sysUser = null ;
        String principal;
        try {
            principal = oauth2FeignClientService.currentUser();
            sysUser = new SysUser();
            if(StringUtils.isNotBlank(principal)){
                sysUser.setUserName(principal);
                List<SysRole> sysRoleList = sysUser.getSysRoleList();
                List<String> authority = new ArrayList<>();
                List<SysMenu> sysApiList = new ArrayList<>();
                for(SysRole role:sysRoleList){
                    //角色权限
                    authority.add(role.getRoleType());
                    List<SysMenu> sysRoleApiList = role.getSysMenuList();
                    sysApiList.addAll(sysRoleApiList);
                }
                SysMenu rootApi = new SysMenu();
                rootApi.setId(0L);
                List<SysMenu> sysApis = treeApiList(sysApiList, rootApi);
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
    private   List<SysMenu> treeApiList( List<SysMenu> sysApiList, SysMenu parentApi){
        List<SysMenu> apiList = new ArrayList<>();
        for(SysMenu api: sysApiList){
            //遍历出父id等于参数的id，add进子节点集合
            if(null != api && api.getParentMenu()!=null
                    && api.getParentMenu().getId()!=null
                    && parentApi != null
                    && parentApi.getId() != null
                    && api.getParentMenu().getId().equals(parentApi.getId())
            ){
                List<SysMenu> childApi = treeApiList(sysApiList, api);
                api.setChildren(childApi);
                //递归遍历下一级
                apiList.add(api);
            }
        }
        return apiList;
    }

}
