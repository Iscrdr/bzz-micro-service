/**
 * Copyright &copy; 2018-2020 <a href="https://github.com/qianli8811/bzz-micro-service.git">bzz-micro-service</a> All rights reserved.
 */
package com.bzz.cloud.rbac.service;

import com.bzz.cloud.core.service.BzzBaseService;
import com.bzz.cloud.rbac.entity.SysApi;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bzz.cloud.rbac.dao.SysApiDao;


/**
 * @desc: Api表管理
 *
 * @Auther: yang qianli
 * @Email: 624003618@qq.com
 * @createDate: 2019-05-10 00:34:36
 * @updateDate: 2019-05-10 00:34:36
 */
@Service
@Transactional
public class SysApiService extends BzzBaseService<SysApiDao, SysApi,Long> {


	
}