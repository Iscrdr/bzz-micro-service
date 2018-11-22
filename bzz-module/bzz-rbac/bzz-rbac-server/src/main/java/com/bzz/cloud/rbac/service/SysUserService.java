package com.bzz.cloud.rbac.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bzz.cloud.framework.annotations.DataBaseSourceTarget;
import com.bzz.cloud.rbac.entity.SysUser;
import com.bzz.cloud.rbac.dao.SysUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * 系统管理，安全相关实体的管理类,包括用户、角色、菜单.
 * @author yang-ql
 * @version 2013-12-05
 */

@Service
@Transactional(readOnly = true)
public class SysUserService   {

	@Autowired
	private SysUserDao userDao;
	
	@Cacheable(value="userMysqlCache",key ="T(String).valueOf(#page.current).concat('-').concat(#page.size)")
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public Page<SysUser> selectPage(Page<SysUser> page,  QueryWrapper<SysUser> queryWrapper){
		Page<SysUser> sysUserIPage = userDao.selectPage(page, queryWrapper);
		return sysUserIPage;
	}
	@Cacheable(value="userOracleCache",key ="T(String).valueOf(#page.current).concat('-').concat(#page.size)")
	@Transactional(propagation= Propagation.NOT_SUPPORTED)
	@DataBaseSourceTarget(dataBaseDialect = "oracle",dataSourceValue = "dataSourceB")
	public Page<SysUser> selectPageOracle(Page<SysUser> page,QueryWrapper<SysUser> queryWrapper){
		Page<SysUser> sysUserMyPage = userDao.selectPage(page,queryWrapper);
		return sysUserMyPage;
	}
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public Page<SysUser> selectList(Page<SysUser> page,QueryWrapper<SysUser> queryWrapper){
		return userDao.selectPage(page,queryWrapper);
	}
}
