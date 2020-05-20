package com.bzz.cloud.core.service;

import com.bzz.cloud.core.dao.BaseDao;
import com.bzz.cloud.core.entity.BaseEntity;
import com.bzz.common.Utils.IdUtils;
import com.bzz.common.Utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-三月-16 14-58
 * @Modified by:
 * @Description:
 */

public class BzzBaseService<D extends BaseDao,T extends BaseEntity,PK extends Serializable> extends BaseService {

    @Autowired
    protected D baseDao;

    @Autowired
    private RedisTemplate redisTemplate;

    public T get(Long id) {
        return (T)baseDao.get(id);
    }

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
     * 公共数据
     * @param entity
     * @param flag 0：插入，1更新
     */
    private void setCommData(BaseEntity entity,int flag){

        Date date = new Date();
        entity.setUpdateTime(date);//更新时间

        //获取当前登录用户
        BaseEntity currentUser = (BaseEntity)redisTemplate.opsForValue().get("currentUser");
        /*
         * 插入
         */
        if(flag == 0){
            if(entity.getId() == null ){
                entity.setId(IdUtils.getLongId()); //id
            }
            entity.setCreateTime(date); //创建时间
            //设置创建人
            if(entity.getCreateUserId() == null && currentUser != null){
                entity.setCreateUserId((Long)currentUser.getId());
                entity.setUpdateUserId((Long)currentUser.getId());
            }else if(entity.getCreateUserId() == null && currentUser == null){
                throw new RuntimeException("保存到数据库中的数据必须设置创建人的id: "+entity.toString());
            }else if(entity.getUpdateUserId() == null && currentUser == null){
                throw new RuntimeException("保存到数据库中的数据必须设置最后修改人的id: "+entity.toString());
            }

            entity.setDelFlag(0);
            entity.setVersion(1);
        }else {
            /**
             * 修改
             */
            entity.setVersion(entity.getVersion()+1);
            //设置最后修改人
            if(entity.getUpdateUserId() == null && currentUser != null){
                entity.setUpdateUserId((Long)currentUser.getId());
            }else if(entity.getUpdateUserId() == null && currentUser == null){
                throw new RuntimeException("保存到数据库中的数据必须设置最后修改人的id: "+entity.toString());
            }
            entity.setVersion(entity.getVersion()+1);

        }

    }
}
