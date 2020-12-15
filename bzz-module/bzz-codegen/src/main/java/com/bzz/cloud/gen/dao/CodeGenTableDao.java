
package com.bzz.cloud.gen.dao;
import com.bzz.cloud.core.dao.BaseDao;
import com.bzz.cloud.core.dao.BzzBaseDao;
import com.bzz.cloud.core.entity.BaseEntity;
import com.bzz.cloud.framework.annotations.BzzMyBatisDao;
import com.bzz.cloud.gen.entity.CodeGenTable;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * 业务表DAO接口
 * @author ThinkGem
 * @version 2013-10-15
 */

@BzzMyBatisDao("codeGenTableDao")
public interface CodeGenTableDao  extends BzzBaseDao<CodeGenTable,Long> {


    /**
     * @desc: 查询表属性
     * @Parmam: String dataBase:数据库名称,String tableName:表名
     * @Return: List<Map<String,String>>
     * @Date: 2019/2/26 14:30
     * @update:
     */
    @Select("show table status from ${dataBase} like '${tableName}'")
    List<Map<String,String>> getTable(@Param("dataBase") String dataBase, @Param("tableName") String tableName);




}
