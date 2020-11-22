/**
 * Copyright &copy; 2018-2020 <a href="https://github.com/qianli8811/bzz-micro-service.git">bzz-micro-service</a> All rights reserved.
 */
package com.bzz.cloud.rbac.service;

import com.bzz.cloud.core.service.BzzBaseService;
import com.bzz.cloud.rbac.dao.SysMenuDao;
import com.bzz.cloud.rbac.entity.SysMenu;
import com.bzz.cloud.rbac.entity.SysRole;
import com.bzz.cloud.rbac.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bzz.cloud.rbac.dao.SysRoleDao;

import java.util.List;


/**
 * @desc: 角色管理
 *
 * @Auther: yang qianli
 * @Email: 624003618@qq.com
 * @createDate: 2019-05-10 00:01:58
 * @updateDate: 2019-05-10 00:01:58
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysRoleService extends BzzBaseService<SysRole,Long> {

    @Autowired
    private SysRoleDao sysRoleDao;

    public void insertRoleUser(SysRole role) {
        sysRoleDao.insertRoleUser(role);
    }

    public void insertRoleApi(SysRole role) {
        sysRoleDao.insertRoleApi(role);
    }

    public List<SysMenu> getSysMenu(List<SysRole> roles){
          return  sysRoleDao.getSysMenu(roles);
    }
}
