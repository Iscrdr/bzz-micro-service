package com.bzz.cloud.core.dao;


import com.bzz.cloud.core.entity.BaseEntity;
import com.bzz.common.utils.Page;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

public interface  BaseDao<T extends BaseEntity,PK extends Serializable>  {

    /**
     * 根据ID获取单条数据
     * @param id
     * @return
     */
    T get(PK id);


    /**
     * 校验字段是否已经存在，用于用户名邮箱手机号唯一校验
     * @param entity
     * @return
     */
    List<T> selectList(T entity) ;



    /**
     * 插入数据
     * @param entity
     * @return
     */
    long insert(T entity);

    /**
     * 根据属性查询数据列表
     * @param entity
     * @return
     */
    List<T> findList(T entity);

    /**
     * 分页查询
     */
    List<T> findPage(int currentPage,int pageSize,T entity);
    /**
     * 分页查询
     */
    Page findPage(Page page,T entity);
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
    Integer findCount(T entity);


    /**
     * 批量插入数据
     * @param list
     * @return
     */
    Integer insertBatch(List<T> list);

    /**
     * 更新单条数据
     * @param entity
     * @return
     */
    Integer update(T entity);

    /**
     * 批量更新数据
     * @param list
     * @return
     */
    Integer updateBatch(List<T> list);

    /**
     * 删除数据（一般为逻辑删除，更新del_flag字段为1）
     * @param entity
     * @return
     */
    Integer delete(T entity);

    /**
     * 批量删除数据（一般为逻辑删除，更新del_flag字段为1）
     * @param list
     * @return
     */
    Integer deleteBacth(@Param("list") List<Long>  list);
}
