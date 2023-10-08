package com.bzz.cloud.rbac.service;


import com.bzz.cloud.core.service.BzzBaseService;
import com.bzz.cloud.rbac.dao.SysUserDao;
import com.bzz.cloud.rbac.entity.SysAuthority;
import com.bzz.cloud.rbac.entity.SysUser;
import com.bzz.common.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 系统管理，安全相关实体的管理类,包括用户、角色、菜单.
 * @author yang-ql
 * @version 2013-12-05
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class SysUserService extends BzzBaseService<SysUserDao,SysUser,Long> {



	@Autowired
	private SysUserDao sysUserDao;

	/*@DataBaseSourceTarget(dataSourceValue="dataSourceB",dataBaseDialect = "oracle")*/

	public SysUser getUserByLoginName(SysUser sysUser){
		return sysUserDao.getUserByLoginName(sysUser);
	}

	public void insertUserGroup(SysUser sysUser){
		sysUserDao.insertUserGroup(sysUser);
	}
	public long insertOracle(SysUser sysUser){



		return sysUserDao.insert(sysUser);
	}

	public SysUser selectUser(SysUser sysUser){
		List<SysUser> sysUsers = sysUserDao.selectList(sysUser);
		System.out.println(sysUsers);
		return sysUsers.get(0);
	}


	public List<SysUser> selectList(SysUser sysUser){
		List<SysUser> sysUserPage = sysUserDao.selectList(sysUser);
		return sysUserPage;
	}



	public List<SysAuthority> findSysAuthority(SysUser sysUser){
		return sysUserDao.findSysAuthority(sysUser);
	}


    public  Page<SysUser> findPage(Page page, SysUser sysUser){
		return sysUserDao.findPage(page,sysUser);
	}
}
