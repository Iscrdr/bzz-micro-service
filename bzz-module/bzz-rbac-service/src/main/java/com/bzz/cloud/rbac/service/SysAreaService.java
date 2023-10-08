/**
 * Copyright &copy; 2018-2020 <a href="https://github.com/qianli8811/bzz-micro-service.git">bzz-micro-service</a> All rights reserved.
 */
package com.bzz.cloud.rbac.service;


import com.bzz.cloud.core.service.BzzBaseService;
import com.bzz.cloud.rbac.entity.SysArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bzz.cloud.rbac.dao.SysAreaDao;

import java.util.List;


/**
 * @desc: 行政区域
 *
 * @author: yang qianli
 * @Email: 624003618@qq.com
 * @createDate: 2019-05-20 16:25:10
 * @updateDate: 2019-05-20 16:25:10
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysAreaService extends BzzBaseService<SysAreaDao,SysArea ,Long>  {

    @Autowired
    private SysAreaDao sysAreaDao;

    public List<SysArea> findAllChildArea(SysArea sysArea){
        return sysAreaDao.findAllChildArea(sysArea);
    }

}
