package com.bzz.cloud.gen.service;


import com.bzz.cloud.core.service.BzzBaseService;
import com.bzz.cloud.gen.dao.GenTableDao;
import com.bzz.cloud.gen.entity.GenTable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
 * 业务表Service
 * @author ThinkGem
 * @version 2013-10-15
 */
@Service
@Transactional(readOnly = true)
public class GenTableService extends BzzBaseService<GenTableDao, GenTable> {

    public List<Map<String,String>> getTable(String dataBase, String tableName){
       return baseDao.getTable(dataBase,tableName);
    }

}
