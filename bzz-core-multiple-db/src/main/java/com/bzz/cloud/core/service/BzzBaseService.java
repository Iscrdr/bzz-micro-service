package com.bzz.cloud.core.service;

import com.bzz.cloud.core.dao.BaseDao;
import com.bzz.cloud.core.entity.BaseEntity;
import com.bzz.common.utils.IdUtils;

import com.bzz.common.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-三月-16 14-58
 * @Modified by:
 * @Description:
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BzzBaseService<D extends BaseDao,T extends BaseEntity> extends BaseService {

    @Autowired
    protected D baseDao;


    @Override
    public long insert(BaseEntity entity) {
        setCommData(entity,0);
        return baseDao.insert(entity);
    }

    @Override
    public int update(BaseEntity entity) {
        setCommData(entity,1);
        return baseDao.update(entity);
    }



    /**
     * 公共数据:创建时间、更新时间、版本号
     * @param entity
     * @param flag 0：插入，1更新
     */
    private void setCommData(BaseEntity entity,int flag){

        Date date = new Date();
        //更新时间
        entity.setUpdateTime(date);

        /*
         * 插入
         */
        if(flag == 0){
            if(entity.getId() == null ){
                //set id
                entity.setId(IdUtils.getLongId());
            }
            //创建时间
            entity.setCreateTime(date);
            entity.setDelFlag(0);
            entity.setVersion(1);
        }else {
            /**
             * 修改
             */
            entity.setVersion(entity.getVersion()+1);
        }
    }
}
