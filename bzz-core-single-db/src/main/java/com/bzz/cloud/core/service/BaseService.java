
package com.bzz.cloud.core.service;


import com.bzz.cloud.core.dao.BaseDao;
import com.bzz.cloud.core.entity.BaseEntity;
import com.bzz.common.utils.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;


/**
 * Service基类
 * @author yang-ql
 * @version 2014-05-16
 */
public abstract class BaseService<D extends BaseDao<T,PK>,T extends BaseEntity<T,PK> ,PK extends Serializable> {
	
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	protected D baseDao;



	/**
	 * 根据ID获取单条数据
	 * @param id
	 * @return
	 */
	public T get(PK id){
		return baseDao.get(id);
	}
	/**
	 * 插入数据
	 * @param entity
	 * @return Object 主键类型
	 */
	@Transactional(readOnly = false)
	public long insert(T entity){
		return baseDao.insert(entity);
	}

	/**
	 * 根据属性查询数据列表
	 * @param entity
	 * @return
	 */
	public List<T> findList(T entity){
		return baseDao.findList(entity);
	}


	/**
	 * 分页查询
	 */
	public  Page<T> findPage(T entity, int pageNo, int pageSzie){
		int count = findCount(entity);
		List<T> list = baseDao.findPage(entity, pageNo, pageSzie);
		Page<T> page = new Page<>(pageNo,pageSzie,count,list);
		return page;
	}
	/**
	 * 查询所有数据列表
	 * @param entity
	 * @return
	 */
	public  List<T> findAllList(T entity){
		return  baseDao.findAllList(entity);
	}

	/**
	 * 查询总条数
	 * @param entity
	 * @return
	 */
	public  int findCount(T entity){
		return  baseDao.findCount(entity);
	}



	/**
	 * 批量插入数据
	 * @param list
	 * @return
	 */
	@Transactional(readOnly = false)
	public  int insertBatch(List<T> list){
		return  baseDao.insertBatch(list);
	}

	/**
	 * 更新单条数据
	 * @param entity
	 * @return
	 */
	@Transactional(readOnly = false)
	public int update(T entity){
		return  baseDao.update(entity);
	}

	/**
	 * 批量更新数据
	 * @param list
	 * @return
	 */
	@Transactional(readOnly = false)
	public  int updateBatch(List<T> list){
		return  baseDao.updateBatch(list);
	}



	/**
	 * 删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param entity
	 * @return
	 */
	@Transactional(readOnly = false)
	public int delete(T entity){
		return  baseDao.delete(entity);
	}

	/**
	 * 批量删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param list
	 * @return
	 */
	@Transactional(readOnly = false)
	public  int deleteBacth(List<T> list){
		return  baseDao.deleteBacth(list);
	}


}
