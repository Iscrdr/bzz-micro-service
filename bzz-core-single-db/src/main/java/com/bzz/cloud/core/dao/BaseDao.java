package com.bzz.cloud.core.dao;


import com.bzz.cloud.core.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;

public  interface BaseDao<T  extends BaseEntity,PK extends Serializable>  {

    /**
     * 根据ID获取单条数据
     * @param id
     * @return
     */
    T  get(PK id);
    /**
     * 插入数据
     * @param entity
     * @return
     */
    Long insert(T entity);

    /**
     * 根据属性查询数据列表
     * @param entity
     * @return
     */
    List<T> findList(T entity);


    /**
     * 分页查询
     */
    List<T> findPage(T entity, int pageNo, int pageSzie);
    /**
     * 查询所有数据列表
     * @param entity
     * @return
     */
    List<T> findAllList(T entity);

    /**
     * 查询总条数
     * @param entity
     * @return
     */
    int findCount(T entity);


    /**
     * 批量插入数据
     * @param list
     * @return
     */
    int insertBatch(List<T> list);

    /**
     * 更新单条数据
     * @param entity
     * @return
     */
    int update(T entity);

    /**
     * 批量更新数据
     * @param list
     * @return
     */
    int updateBatch(List<T> list);

    /**
     * 删除数据（一般为逻辑删除，更新del_flag字段为1）
     * @param entity
     * @return
     */
    int delete(T entity);

    /**
     * 批量删除数据（一般为逻辑删除，更新del_flag字段为1）
     * @param list
     * @return
     */
    int deleteBacth(List<T> list);
}
