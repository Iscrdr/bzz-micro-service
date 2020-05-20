package com.bzz.cloud.rbac.utils;

import com.bzz.cloud.rbac.entity.SysAuthority;
import com.bzz.cloud.rbac.entity.SysMenu;
import com.bzz.cloud.rbac.entity.SysRole;
import com.bzz.cloud.rbac.entity.SysUser;
import com.bzz.common.Utils.JsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-05-16 21-40
 * @Modified by:
 * @Description:
 */
public class UserUtil {
    /**
     *
     * @desc: 获取用户权限信息，返回List数组
     *
     * @param: SysUser
     * @return: JSON
     * @auther: cloud
     * @date: 2019/5/16 21:56
     */
    public static List<SysMenu> getUserAuthorityList(SysUser sysUser){
        if(!checkUserRoleList(sysUser)){
            return null;
        }

        List<SysRole> sysRoleList = sysUser.getSysRoleList();
        List<SysMenu> sysSysMenu = new ArrayList<>();
        //权限集合
        for(SysRole role : sysRoleList){
            sysSysMenu.addAll(role.getSysMenuList());
        }
        return  sysSysMenu;
    }




    /**
     *
     * @desc: 获取用户api信息，返回JSON数据
     *
     * @param: SysUser
     * @return: JSON
     * @auther: cloud
     * @date: 2019/5/16 21:56
     */
    public static List getUserApiAuthoritiesList(SysUser sysUser){

        if(!checkUserRoleList(sysUser)){
            return null;
        }

        List<SysRole> sysRoleList = sysUser.getSysRoleList();
        //权限集合
        List<String> grantedAuthorities = new ArrayList<>();
        for(SysRole role:sysRoleList){
            //角色权限
            grantedAuthorities.add(role.getRoleType());
            List<SysMenu> sysApiList = role.getSysMenuList();
            for(SysMenu sysApi:sysApiList){
                //api操作权限集合
                List<SysAuthority> sysAuthorityList = sysApi.getAuthorityList();
                for(SysAuthority sysAuthority:sysAuthorityList){
                    grantedAuthorities.add(sysAuthority.getAuthority());
                }
            }
        }
        return grantedAuthorities;
    }

    public static boolean checkUserRoleList(SysUser sysUser){
        if(null == sysUser || null ==  sysUser.getSysRoleList() || sysUser.getSysRoleList().size() <= 0){
            return false;
        }
        return true;
    }
}
