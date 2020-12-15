/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.bzz.cloud.gen.service;


import com.bzz.cloud.core.dao.BzzBaseDao;
import com.bzz.cloud.core.service.BzzBaseService;
import com.bzz.cloud.framework.annotations.BzzMyBatisDao;
import com.bzz.cloud.gen.dao.CodeGenTableColumnDao;
import com.bzz.cloud.gen.entity.CodeGenTable;
import com.bzz.cloud.gen.entity.CodeGenTableColumn;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @description：
 * @author     ：Iscrdr
 * @date       ：2020-11-23 00:21
 * @email      ：624003618@qq.com
 * @modified By：
 * @version:     1.0.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CodeGenTableColumnService extends BzzBaseService<CodeGenTableColumn,Long> {

    @Autowired
    private CodeGenTableColumnDao codeGenTableColumnDao;

    /**
     *
     * @param dataBase
     * @param tableName
     * @return List<Map<String,String>>
     */
    public List<CodeGenTableColumn> getTableColumn(String dataBase, String tableName){
        return codeGenTableColumnDao.getTableColumn(dataBase,tableName);

    }

    /**
     *
     * @param dataBase
     * @param tableName
     * @return void
     */
    public void insertBatchTableColumn(List<CodeGenTableColumn> list){
        codeGenTableColumnDao.insertBatchTableColumn(list);
    }

}
