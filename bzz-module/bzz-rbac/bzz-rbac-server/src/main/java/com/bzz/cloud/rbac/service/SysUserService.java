package com.bzz.cloud.rbac.service;

import com.bzz.cloud.core.dao.BaseDao;
import com.bzz.cloud.core.service.BaseService;
import com.bzz.cloud.rbac.dao.SysUserDao;
import com.bzz.cloud.rbac.entity.SysUser;


import com.bzz.cloud.framework.annotations.DataBaseSourceTarget;
import com.bzz.common.Utils.Page;
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
public class SysUserService extends BaseService<SysUserDao,SysUser,Long> {

	public SysUser getUserByLoginName(SysUser sysUser){
		SysUser sysUser1 = baseDao.getUserByLoginName(sysUser);

		return sysUser1;
	}


	public long insert(SysUser sysUser){
		//sysUser.preInsert(sysUser.getId());
		return baseDao.insert(sysUser);
	}
	@DataBaseSourceTarget(dataBaseDialect = "oracle",dataSourceValue = "dataSourceB")
	public long insertOracle(SysUser sysUser){
		//sysUser.preInsert(sysUser.getId());
		return baseDao.insert(sysUser);
	}

	@DataBaseSourceTarget(dataBaseDialect = "mysql",dataSourceValue = "dataSourceA")
	public SysUser selectUser(SysUser sysUser){
		SysUser sysUser1 = (SysUser) baseDao.selectUser(sysUser);
		System.out.println(sysUser1);
		return sysUser1;
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

	private Page<SysUser> getPage(String dbType, SysUser sysUser, SysUserDao baseDao){
		sysUser.setDbType(dbType);
		int count = baseDao.findCount(sysUser);
		List<SysUser> sysUsers = baseDao.selectList(sysUser);
		Page<SysUser> page = sysUser.getPage();
		page.setTotalCount(count);
		page.setList(sysUsers);
		return page;
	}

}
