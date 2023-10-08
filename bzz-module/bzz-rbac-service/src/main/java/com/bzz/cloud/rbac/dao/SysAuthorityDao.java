/**
 * Copyright &copy; 2012-2019 <a href="https://github.com/qianli8811/bzz-cloud">bzz-cloud</a> All rights reserved.
 */
package com.bzz.cloud.rbac.dao;
import com.bzz.cloud.core.dao.BzzBaseDao;
import com.bzz.cloud.framework.annotations.BzzMyBatisDao;
import com.bzz.cloud.rbac.entity.SysAuthority;
import org.apache.ibatis.annotations.Mapper;
import com.bzz.cloud.core.dao.BaseDao;
/**
 * @desc: 权限表管理
 *
 * @Auther: yang qianli
 * @Email: 624003618@qq.com
 * @createDate: 2019-05-10 00:35:14
 * @updateDate: 2019-05-10 00:35:14
 */
@BzzMyBatisDao("sysAuthorityDao")
public interface SysAuthorityDao extends BzzBaseDao<SysAuthority,Long> {

}
