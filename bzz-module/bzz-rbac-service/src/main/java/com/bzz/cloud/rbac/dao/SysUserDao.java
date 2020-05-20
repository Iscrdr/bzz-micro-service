package com.bzz.cloud.rbac.dao;


import com.bzz.cloud.core.dao.BaseDao;
import com.bzz.cloud.core.entity.BaseEntity;
import com.bzz.cloud.framework.annotations.BzzMyBatisDao;
import com.bzz.cloud.rbac.entity.SysAuthority;
import com.bzz.cloud.rbac.entity.SysUser;
import com.bzz.common.Utils.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 用户DAO接口
 * @author yang-ql
 * @version 2014-05-16
 */
@BzzMyBatisDao("sysUserDao")
public interface SysUserDao<S extends BaseEntity<SysUser, Long>> extends BaseDao<SysUser,Long> {

	/**
	 * 通过手机号或者邮箱查询用户
	 */
	public SysUser selectPageList(SysUser sysUser);

	/**
	 * 通过登录户名查询用户
	 */

	public SysUser getUserByLoginName(SysUser sysUser);

	@Override
	public List<SysUser> selectList(SysUser sysUser);

	/**
	 * 保存用户与用户组的关系
 	 */

	public void insertUserGroup(SysUser sysUser);

	/**
	 * 用户权限
 	 */
	public List<SysAuthority> findSysAuthority(SysUser sysUser);




	/**
	 * 分页
	 */
	@Override
	public Page findPage(@Param("page") Page page, @Param("sysUser") SysUser sysUser);


}
