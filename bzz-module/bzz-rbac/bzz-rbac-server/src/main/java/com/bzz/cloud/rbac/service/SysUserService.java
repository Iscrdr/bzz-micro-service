package com.bzz.cloud.rbac.service;

import com.bzz.cloud.core.service.BaseService;
import com.bzz.cloud.rbac.dao.SysUserDao;
import com.bzz.cloud.rbac.entity.SysUser;


import com.bzz.cloud.framework.annotations.DataBaseSourceTarget;
import com.bzz.common.Utils.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * 系统管理，安全相关实体的管理类,包括用户、角色、菜单.
 * @author yang-ql
 * @version 2013-12-05
 */

@Service
@Transactional(readOnly = true)
public class SysUserService extends BaseService<SysUser,Long> {

	@Resource
	private SysUserDao sysUserDao;

	@DataBaseSourceTarget(dataBaseDialect = "mysql",dataSourceValue = "dataSourceA")
	//@Cacheable(value="userMysqlCache",key ="T(String).valueOf(#page.current).concat('-').concat(#page.size)")
	public SysUser selectUser(SysUser sysUser){
		SysUser sysUser1 = sysUserDao.selectUser(sysUser);
		System.out.println(sysUser1);
		return sysUser1;
	}
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	@DataBaseSourceTarget(dataBaseDialect = "mysql",dataSourceValue = "dataSourceB")
	public Page<SysUser> selectPageMysql(SysUser sysUser){
		Page<SysUser> sysUserMyPage = sysUserDao.selectPage(sysUser);
		return sysUserMyPage;
	}
	//@Cacheable(value="userOracleCache",key ="T(String).valueOf(#page.current).concat('-').concat(#page.size)")
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	@DataBaseSourceTarget(dataBaseDialect = "oracle",dataSourceValue = "dataSourceB")
	public Page<SysUser> selectPageOracle(SysUser sysUser){
		Page<SysUser> sysUserMyPage = sysUserDao.selectPage(sysUser);
		return sysUserMyPage;
	}
	@DataBaseSourceTarget(dataBaseDialect = "mysql",dataSourceValue = "dataSourceA")
	public List<SysUser> selectList(SysUser sysUser){
		List<SysUser> sysUserPage = sysUserDao.selectList(sysUser);
		return sysUserPage;
	}
}
