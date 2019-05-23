/**
 * Copyright &copy; 2012-2019 <a href="https://github.com/qianli8811/bzz-cloud">bzz-cloud</a> All rights reserved.
 */
package com.bzz.cloud.rbac.dao;
import com.bzz.cloud.core.entity.BaseEntity;
import com.bzz.cloud.framework.annotations.BzzMyBatisDao;
import com.bzz.cloud.rbac.entity.SysArea;
import com.bzz.cloud.core.dao.BaseDao;

import java.util.List;

/**
 * @desc: 行政区域
 *
 * @Auther: yang qianli
 * @Email: 624003618@qq.com
 * @createDate: 2019-05-20 16:25:10
 * @updateDate: 2019-05-20 16:25:10
 */
@BzzMyBatisDao("sysAreaDao")
public interface SysAreaDao<S extends BaseEntity<SysArea, Long>> extends BaseDao<SysArea,Long> {

    List<SysArea> findAllChildArea(SysArea sysArea);
	
}