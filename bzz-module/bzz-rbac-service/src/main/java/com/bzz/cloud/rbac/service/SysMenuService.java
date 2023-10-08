/**
 * Copyright &copy; 2018-2020 <a href="https://github.com/qianli8811/bzz-micro-service.git">bzz-micro-service</a> All rights reserved.
 */
package com.bzz.cloud.rbac.service;

import com.bzz.cloud.core.service.BzzBaseService;
import com.bzz.cloud.rbac.dao.SysMenuDao;
import com.bzz.cloud.rbac.entity.SysMenu;
import com.bzz.cloud.rbac.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @desc: Api表管理
 *
 * @author: yang qianli
 * @email: 624003618@qq.com
 * @createDate: 2019-05-10 00:34:36
 * @updateDate: 2019-05-10 00:34:36
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysMenuService extends BzzBaseService<SysMenuDao,SysMenu,Long> {

    @Autowired
    private SysMenuDao sysMenuDao;

    public SysUser getAllMenu(SysUser sysUser){
        return sysMenuDao.getAllMenu(sysUser);
    }


}
