package com.bzz.cloud.rbac.service;


import com.bzz.cloud.core.entity.BaseEntity;
import com.bzz.cloud.core.service.BzzBaseService;
import com.bzz.cloud.rbac.dao.SysUserDao;
import com.bzz.cloud.rbac.entity.SysUser;


import com.bzz.cloud.framework.annotations.DataBaseSourceTarget;
import com.bzz.common.Utils.Page;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 系统管理，安全相关实体的管理类,包括用户、角色、菜单.
 * @author yang-ql
 * @version 2013-12-05
 */

@Service
@Transactional
public class SysUserService extends BzzBaseService<SysUserDao,SysUser,Long> {


	public SysUser getUserByLoginName(SysUser sysUser){
		SysUser sysUser1 = baseDao.getUserByLoginName(sysUser);
		return sysUser1;
	}


	@DataBaseSourceTarget(dataBaseDialect = "oracle",dataSourceValue = "dataSourceB")
	public long insertOracle(SysUser sysUser){
		return baseDao.insert(sysUser);
	}

	@DataBaseSourceTarget(dataBaseDialect = "mysql",dataSourceValue = "dataSourceA")
	public SysUser selectUser(SysUser sysUser){
		List<SysUser> sysUsers = baseDao.selectList(sysUser);
		System.out.println(sysUsers);
		return sysUsers.get(0);
	}

	@DataBaseSourceTarget(dataBaseDialect = "mysql",dataSourceValue = "dataSourceA")
	public Page<SysUser> selectPageMysql(SysUser sysUser){
		return getPage("mysql",sysUser,baseDao);
	}

	@DataBaseSourceTarget(dataBaseDialect = "oracle",dataSourceValue = "dataSourceB")
	public Page<SysUser> selectPageOracle(SysUser sysUser){
		return getPage("oracle",sysUser,baseDao);
	}
	@DataBaseSourceTarget(dataBaseDialect = "mysql",dataSourceValue = "dataSourceA")
	public List<SysUser> selectList(SysUser sysUser){
		List<SysUser> sysUserPage = baseDao.selectList(sysUser);
		return sysUserPage;
	}

	private Page<SysUser> getPage(String dbType, SysUser sysUser, SysUserDao<BaseEntity<SysUser, Long>> baseDao){
		sysUser.setDbType(dbType);
		int count = baseDao.findCount(sysUser);
		List<SysUser> sysUsers = baseDao.selectList(sysUser);
		Page<SysUser> page = sysUser.getPage();
		page.setTotalCount(count);
		page.setList(sysUsers);
		return page;
	}

}
