package com.bzz.cloud.oauth.entity;

import com.bzz.cloud.rbac.entity.SysUser;

/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-05-27 17-13
 * @Modified by:
 * @Description:
 */
public interface IUser {
    /**
     * 获取用户
     * @param sysUser
     * @return
     */
    SysUser getSysUser(SysUser sysUser);
}
