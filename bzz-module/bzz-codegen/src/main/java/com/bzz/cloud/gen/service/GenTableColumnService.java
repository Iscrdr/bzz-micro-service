package com.bzz.cloud.gen.service;


import com.bzz.cloud.core.service.BzzBaseService;
import com.bzz.cloud.gen.dao.GenTableColumnDao;
import com.bzz.cloud.gen.entity.GenTableColumn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 业务表Service
 * @author ThinkGem
 * @version 2013-10-15
 */
@Service
@Transactional(readOnly = true)
public class GenTableColumnService extends BzzBaseService<GenTableColumnDao, GenTableColumn> {

    public List<GenTableColumn> getTableColumn(String dataBase, String tableName){
       return baseDao.getTableColumn(dataBase,tableName);
    }

}
