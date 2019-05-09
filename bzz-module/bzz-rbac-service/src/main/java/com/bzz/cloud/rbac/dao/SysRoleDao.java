/**
 * Copyright &copy; 2012-2019 <a href="https://github.com/qianli8811/bzz-cloud">bzz-cloud</a> All rights reserved.
 */
package com.bzz.cloud.rbac.dao;
import com.bzz.cloud.core.entity.BaseEntity;
import com.bzz.cloud.framework.annotations.BzzMyBatisDao;
import org.springframework.stereotype.Repository;
import com.bzz.cloud.rbac.entity.SysRole;
import com.bzz.cloud.core.dao.BaseDao;
/**
 * @desc: 角色管理
 *
 * @Auther: yang qianli
 * @Email: 624003618@qq.com
 * @createDate: 2019-05-10 00:01:58
 * @updateDate: 2019-05-10 00:01:58
 */
@BzzMyBatisDao("sysRoleDao")
public interface SysRoleDao<S extends BaseEntity<SysRole, Long>> extends BaseDao<SysRole,Long> {
	
}