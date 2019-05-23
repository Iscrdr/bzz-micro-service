package com.bzz.cloud.rbac.utils;

import com.bzz.cloud.rbac.entity.SysApi;
import com.bzz.cloud.rbac.entity.SysAuthority;
import com.bzz.cloud.rbac.entity.SysRole;
import com.bzz.cloud.rbac.entity.SysUser;
import com.bzz.common.Utils.JsonUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

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
    public static List<GrantedAuthority> getUserAuthorityList(SysUser sysUser){
        if(null == sysUser || null ==  sysUser.getSysRoleList() || sysUser.getSysRoleList().size() <= 0){
            return null;
        }

        List<SysRole> sysRoleList = sysUser.getSysRoleList();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();//权限集合
        for(SysRole role:sysRoleList){
            //角色权限
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getRoleType());
            grantedAuthorities.add(grantedAuthority);
            List<SysApi> sysApiList = role.getSysApiList();
            for(SysApi sysApi:sysApiList){
                //api操作权限集合
                List<SysAuthority> sysAuthorityList = sysApi.getSysAuthorityList();
                for(SysAuthority sysAuthority:sysAuthorityList){
                    GrantedAuthority grantedAuthority2 = new SimpleGrantedAuthority(sysAuthority.getCode());
                    grantedAuthorities.add(grantedAuthority2);
                }
            }
        }
        return  grantedAuthorities;
    }


    /**
     *
     * @desc: 获取用户权限信息，返回JSON数据
     *
     * @param: SysUser
     * @return: JSON
     * @auther: cloud
     * @date: 2019/5/16 21:56
     */
    public static String getUserAuthorityJson(SysUser sysUser){
        if(null == sysUser || null ==  sysUser.getSysRoleList() || sysUser.getSysRoleList().size() <= 0){
            return null;
        }
        List<SysRole> sysRoleList = sysUser.getSysRoleList();
        List<String> grantedAuthorities = new ArrayList<>();//权限集合
        for(SysRole role:sysRoleList){
            //角色权限
            grantedAuthorities.add(role.getRoleType());
            List<SysApi> sysApiList = role.getSysApiList();
            for(SysApi sysApi:sysApiList){
                //api操作权限集合
                List<SysAuthority> sysAuthorityList = sysApi.getSysAuthorityList();
                for(SysAuthority sysAuthority:sysAuthorityList){
                    grantedAuthorities.add(sysAuthority.getCode());
                }
            }
        }
        return JsonUtils.object2Json(grantedAuthorities);
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
        if(null == sysUser || null ==  sysUser.getSysRoleList() || sysUser.getSysRoleList().size() <= 0){
            return null;
        }
        List<SysRole> sysRoleList = sysUser.getSysRoleList();
        List<String> grantedAuthorities = new ArrayList<>();//权限集合
        for(SysRole role:sysRoleList){
            //角色权限
            grantedAuthorities.add(role.getRoleType());
            List<SysApi> sysApiList = role.getSysApiList();
            for(SysApi sysApi:sysApiList){
                //api操作权限集合
                List<SysAuthority> sysAuthorityList = sysApi.getSysAuthorityList();
                for(SysAuthority sysAuthority:sysAuthorityList){
                    grantedAuthorities.add(sysAuthority.getCode());
                }
            }
        }
        return grantedAuthorities;
    }
}
