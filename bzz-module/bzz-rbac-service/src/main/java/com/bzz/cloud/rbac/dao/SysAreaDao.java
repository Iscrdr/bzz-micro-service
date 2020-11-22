/**
 * Copyright &copy; 2012-2019 <a href="https://github.com/qianli8811/bzz-cloud">bzz-cloud</a> All rights reserved.
 */
package com.bzz.cloud.rbac.dao;
import com.bzz.cloud.core.dao.BzzBaseDao;
import com.bzz.cloud.core.entity.BaseEntity;
import com.bzz.cloud.framework.annotations.BzzMyBatisDao;
import com.bzz.cloud.rbac.entity.SysArea;
import com.bzz.cloud.core.dao.BaseDao;

import java.io.Serializable;
import java.util.List;

/**
 * @desc: sys_area curd
 *
 * @Auther: yang qianli
 * @Email: 624003618@qq.com
 * @createDate: 2019-05-20 16:25:10
 * @updateDate: 2019-05-20 16:25:10
 */
@BzzMyBatisDao("sysAreaDao")
public interface SysAreaDao  extends BzzBaseDao<SysArea,Long> {

    /**
     * 查询所有的下级区域
     * @param sysArea
     * @return
     */
    List<SysArea> findAllChildArea(SysArea sysArea);

}
