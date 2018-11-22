package com.bzz.cloud.core.dao;

import com.bzz.common.page.Page;

import java.util.List;

public  interface CrudDao<T> extends BaseDao{
	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public T get(String id);
	
	/**
	 * 获取单条数据
	 * @param entity
	 * @return
	 */
	public T get(T entity);
	
	/**
	 * 查询数据列表
	 * @param entity
	 * @return
	 */
	public List<T> findList(T entity);
	
	
	/**
	 * 分页查询
	 */
	public Page<T> findPage(T entity,int pageNo,int pageSzie);
	/**
	 * 查询所有数据列表
	 * @param entity
	 * @return
	 */
	public List<T> findAllList(T entity);
	
	/**
	 * 查询总条数
	 * @param entity
	 * @return
	 */
	public Integer findCount(T entity);
	/**
	 * 插入数据
	 * @param entity
	 * @return
	 */
	public int insert(T entity);
	
	/**
	 * 批量插入数据
	 * @param entity
	 * @return
	 */
	public int insertBatch(T entity);
	
	/**
	 * 更新数据
	 * @param entity
	 * @return
	 */
	public int update(T entity);
	
	/**
	 * 批量更新数据
	 * @param entity
	 * @return
	 */
	public int updateBatch(T entity);
	
	/**
	 * 删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param id
	 * @see public int delete(T entity)
	 * @return
	 */
	@Deprecated
	public int delete(String id);
	
	/**
	 * 删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param entity
	 * @return
	 */
	public int delete(T entity);

}
