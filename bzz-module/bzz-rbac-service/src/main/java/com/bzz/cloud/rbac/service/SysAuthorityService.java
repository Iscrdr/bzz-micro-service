/**
 * Copyright &copy; 2018-2020 <a href="https://github.com/qianli8811/bzz-micro-service.git">bzz-micro-service</a> All rights reserved.
 */
package com.bzz.cloud.rbac.service;

import com.bzz.cloud.core.service.BzzBaseService;
import com.bzz.cloud.rbac.entity.SysAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bzz.cloud.rbac.dao.SysAuthorityDao;


/**
 * @desc: 权限表管理
 *
 * @author: yang qianli
 * @Email: 624003618@qq.com
 * @createDate: 2019-05-10 00:35:14
 * @updateDate: 2019-05-10 00:35:14
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysAuthorityService extends BzzBaseService<SysAuthorityDao, SysAuthority> {


	
}