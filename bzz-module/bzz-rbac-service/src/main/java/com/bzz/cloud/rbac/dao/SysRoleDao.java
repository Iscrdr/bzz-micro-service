/**
 * Copyright &copy; 2012-2019 <a href="https://github.com/qianli8811/bzz-cloud">bzz-cloud</a> All rights reserved.
 */
package com.bzz.cloud.rbac.dao;
import com.bzz.cloud.core.dao.BzzBaseDao;
import com.bzz.cloud.framework.annotations.BzzMyBatisDao;
import com.bzz.cloud.rbac.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import com.bzz.cloud.rbac.entity.SysRole;
import com.bzz.cloud.core.dao.BaseDao;

import java.util.List;

/**
 * @desc: 角色管理
 *
 * @Auther: yang qianli
 * @Email: 624003618@qq.com
 * @createDate: 2019-05-10 00:01:58
 * @updateDate: 2019-05-10 00:01:58
 */
@BzzMyBatisDao("sysRoleDao")
public interface SysRoleDao extends BzzBaseDao<SysRole,Long> {

    void insertRoleUser(SysRole role);

    void insertRoleApi(SysRole role);

    /**
     * @param roles
     * @return
     */
    List<SysMenu> getSysMenu(List<SysRole> roles);
}
