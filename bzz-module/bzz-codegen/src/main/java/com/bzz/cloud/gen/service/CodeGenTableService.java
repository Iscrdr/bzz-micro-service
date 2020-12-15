/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.bzz.cloud.gen.service;


import com.bzz.cloud.core.service.BzzBaseService;
import com.bzz.cloud.gen.dao.CodeGenTableDao;
import com.bzz.cloud.gen.entity.CodeGenTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


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
public class CodeGenTableService extends BzzBaseService<CodeGenTable,Long> {

    @Autowired
    private CodeGenTableDao codeGenTableDao;

    public List<Map<String,String>> getTable(String dataBase, String tableName){
       return codeGenTableDao.getTable(dataBase,tableName);
    }






}
