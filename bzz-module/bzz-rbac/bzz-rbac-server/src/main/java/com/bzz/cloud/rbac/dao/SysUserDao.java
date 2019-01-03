package com.bzz.cloud.rbac.dao;


import com.bzz.cloud.core.dao.BaseDao;
import com.bzz.common.Utils.Page;
import org.springframework.stereotype.Repository;
import com.bzz.cloud.rbac.entity.SysUser;

import java.util.List;


/**
 * 用户DAO接口
 * @author yang-ql
 * @version 2014-05-16
 */
@Repository
public interface SysUserDao extends BaseDao<SysUser,Long> {

	SysUser selectUser(SysUser sysUser);

	Page<SysUser> selectPage(SysUser sysUser);

	List<SysUser> selectList(SysUser sysUser);

}
