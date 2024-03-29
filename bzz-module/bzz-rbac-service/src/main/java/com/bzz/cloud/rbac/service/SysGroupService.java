/**
 * Copyright &copy; 2018-2020 <a href="https://github.com/qianli8811/bzz-micro-service.git">bzz-micro-service</a> All rights reserved.
 */
package com.bzz.cloud.rbac.service;

import com.bzz.cloud.core.service.BzzBaseService;
import com.bzz.cloud.rbac.dao.SysGroupDao;
import com.bzz.cloud.rbac.entity.SysGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @desc: 用户组管理
 *
 * @Auther: yang qianli
 * @Email: 624003618@qq.com
 * @createDate: 2019-05-10 00:34:59
 * @updateDate: 2019-05-10 00:34:59
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysGroupService extends BzzBaseService<SysGroupDao,SysGroup,Long> {

    @Autowired
    private SysGroupDao sysGroupDao;

    public void insertGroupRole(SysGroup sysGroup) {
        sysGroupDao.insertGroupRole(sysGroup);
    }
}
