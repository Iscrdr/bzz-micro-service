/**
 * Copyright &copy; 2012-2019 <a href="https://github.com/qianli8811/bzz-cloud">bzz-cloud</a> All rights reserved.
 */
package com.bzz.cloud.rbac.dao;
import com.bzz.cloud.core.dao.BzzBaseDao;
import com.bzz.cloud.core.entity.BaseEntity;
import com.bzz.cloud.framework.annotations.BzzMyBatisDao;
import com.bzz.cloud.rbac.entity.SysGroup;
import com.bzz.cloud.core.dao.BaseDao;
/**
 * @desc: 用户组管理
 *
 * @Auther: yang qianli
 * @Email: 624003618@qq.com
 * @createDate: 2019-05-10 00:34:59
 * @updateDate: 2019-05-10 00:34:59
 */
@BzzMyBatisDao("sysGroupDao")
public interface SysGroupDao extends BzzBaseDao<SysGroup,Long> {

    void insertGroupRole(SysGroup sysGroup);
}
