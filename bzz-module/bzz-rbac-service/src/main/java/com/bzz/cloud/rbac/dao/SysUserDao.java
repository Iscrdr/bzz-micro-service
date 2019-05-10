package com.bzz.cloud.rbac.dao;


import com.bzz.cloud.core.dao.BaseDao;
import com.bzz.cloud.core.entity.BaseEntity;
import com.bzz.cloud.framework.annotations.BzzMyBatisDao;
import com.bzz.cloud.rbac.entity.SysAuthority;
import com.bzz.cloud.rbac.entity.SysUser;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * 用户DAO接口
 * @author yang-ql
 * @version 2014-05-16
 */
@BzzMyBatisDao("sysUserDao")
public interface SysUserDao<S extends BaseEntity<SysUser, Long>> extends BaseDao<SysUser,Long> {

	SysUser selectPageList(SysUser sysUser);//通过手机号或者邮箱查询用户
	SysUser getUserByLoginName(SysUser sysUser);//通过登录户名查询用户

	List<SysUser> selectList(SysUser sysUser);

	void insertUserGroup(SysUser sysUser);// 保存用户与用户组的关系

	List<SysAuthority> findSysAuthority(SysUser sysUser);// 用户权限
}
