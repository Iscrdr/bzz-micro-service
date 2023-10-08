package com.bzz.cloud.core.service;


import com.bzz.cloud.core.dao.BzzBaseDao;
import com.bzz.cloud.core.entity.BaseEntity;
import com.bzz.common.utils.IdUtils;

import com.bzz.common.utils.Page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-三月-16 14-58
 * @Modified by:
 * @Description:
 */

public abstract  class BzzBaseService<T extends BaseEntity,PK extends Serializable>  implements BaseService<T,PK> {
    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected BzzBaseDao<T,PK> bzzBaseDao;


    @Override
    public boolean checkUnique(T entity) {
        List<T> list = bzzBaseDao.selectList(entity);
        if(null != list && list.size()>0){
            return false;
        }
        return true;
    }

    @Override
    public T get(PK id) {
        return bzzBaseDao.get(id);
    }


    /**
     * 根据属性查询数据列表
     * @param entity
     * @return
     */
    @Override
    public List<T> findList(T entity){
        return bzzBaseDao.findList(entity);
    }


    /**
     * 分页查询
     */
    @Override
    public Page findPage(Page page, T entity) {
        page = bzzBaseDao.findPage(page, entity);
        return page;
    }

    /**
     * 查询所有数据列表
     * @param entity
     * @return
     */
    @Override
    public  List<T> findAllList(T entity){
        return  bzzBaseDao.findAllList(entity);
    }

    /**
     * 查询总条数
     * @param entity
     * @return
     */
    @Override
    public  int findCount(T entity){
        return  bzzBaseDao.findCount(entity);
    }



    /**
     * 批量插入数据
     * @param list
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public  int insertBatch(List<T> list){
        return  bzzBaseDao.insertBatch(list);
    }


    /**
     * 批量更新数据
     * @param list
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public  int updateBatch(List<T> list){
        return  bzzBaseDao.updateBatch(list);
    }


    /**
     * 删除数据（一般为逻辑删除，更新del_flag字段为1）
     * @param entity
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public int delete(T entity){
        return  bzzBaseDao.delete(entity);
    }


    /**
     * 批量删除数据（一般为逻辑删除，更新del_flag字段为1）
     * @param ids
     * @return
     */
    @Override
    public  int deleteBacth(List<Long> ids){
        return  bzzBaseDao.deleteBacth(ids);
    }


    @Override
    public long insert(T entity) {
        setCommData(entity,0);
        return bzzBaseDao.insert(entity);
    }

    @Override
    public int update(T entity) {
        setCommData(entity,1);
        return bzzBaseDao.update(entity);
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
