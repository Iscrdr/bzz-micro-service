/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.bzz.cloud.gen.dao;


import com.bzz.cloud.core.dao.BaseDao;
import com.bzz.cloud.gen.entity.GenTableColumn;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 业务表字段DAO接口
 * @author ThinkGem
 * @version 2013-10-15
 */
@Repository
public interface GenTableColumnDao extends BaseDao<GenTableColumn,Long> {

    /**
     *
     * @param dataBase
     * @param tableName
     * @return List<Map<String,String>>
     */
    List<GenTableColumn> getTableColumn(@Param("dataBase") String dataBase, @Param("tableName") String tableName);

}
