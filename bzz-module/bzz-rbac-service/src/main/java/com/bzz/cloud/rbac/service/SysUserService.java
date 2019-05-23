package com.bzz.cloud.rbac.service;


import com.bzz.cloud.core.entity.BaseEntity;
import com.bzz.cloud.core.service.BzzBaseService;
import com.bzz.cloud.rbac.dao.SysUserDao;
import com.bzz.cloud.rbac.entity.SysAuthority;
import com.bzz.cloud.rbac.entity.SysRole;
import com.bzz.cloud.rbac.entity.SysUser;


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
public class SysUserService extends BzzBaseService<SysUserDao, SysUser,Long> {




	public SysUser getUserByLoginName(SysUser sysUser){
		SysUser sysUser1 = baseDao.getUserByLoginName(sysUser);
		return sysUser1;
	}

	public void insertUserGroup(SysUser sysUser){
		baseDao.insertUserGroup(sysUser);
	}
	public long insertOracle(SysUser sysUser){
		return baseDao.insert(sysUser);
	}

	public SysUser selectUser(SysUser sysUser){
		List<SysUser> sysUsers = baseDao.selectList(sysUser);
		System.out.println(sysUsers);
		return sysUsers.get(0);
	}

	public Page<SysUser> selectPageMysql(SysUser sysUser){
		return getPage("mysql",sysUser,baseDao);
	}

	public Page<SysUser> selectPageOracle(SysUser sysUser){
		return getPage("oracle",sysUser,baseDao);
	}
	public List<SysUser> selectList(SysUser sysUser){
		List<SysUser> sysUserPage = baseDao.selectList(sysUser);
		return sysUserPage;
	}

	private Page<SysUser> getPage(String dbType, SysUser sysUser, SysUserDao<BaseEntity<SysUser, Long>> baseDao){

		int count = baseDao.findCount(sysUser);
		List<SysUser> sysUsers = baseDao.selectList(sysUser);
		Page<SysUser> page = new Page<>();
		page.setTotalCount(count);
		page.setList(sysUsers);
		return page;
	}

	public List<SysAuthority> findSysAuthority(SysUser sysUser){
		return baseDao.findSysAuthority(sysUser);
	}


}
