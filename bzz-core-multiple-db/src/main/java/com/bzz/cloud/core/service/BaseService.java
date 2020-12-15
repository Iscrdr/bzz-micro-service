
package com.bzz.cloud.core.service;


import com.bzz.cloud.core.dao.BaseDao;
import com.bzz.cloud.core.dao.BzzBaseDao;
import com.bzz.cloud.core.entity.BaseEntity;
import com.bzz.common.utils.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;


/**
 * Service基类
 * @author yang-ql
 * @version 2014-05-16
 */

public interface BaseService <T extends BaseEntity,PK extends Serializable> {



	public boolean checkUnique(T entity);

	/**
	 * 根据ID获取单条数据
	 * @param id
	 * @return
	 */
	public T get(PK id);

	abstract long insert(T entity);
	/**
	 * 根据属性查询数据列表
	 * @param entity
	 * @return
	 */
	public List<T> findList(T entity);


	/**
	 * 分页查询
	 */
	public Page findPage(Page page,T entity);

	/**
	 * 查询所有数据列表
	 * @param entity
	 * @return
	 */
	public  List<T> findAllList(T entity);
	/**
	 * 查询总条数
	 * @param entity
	 * @return
	 */
	public  int findCount(T entity);



	/**
	 * 批量插入数据
	 * @param list
	 * @return
	 */
	public  int insertBatch(List<T> list);

	/**
	 * 更新单条数据
	 * @param entity
	 * @return
	 */
	public int update(T entity);

	/**
	 * 批量更新数据
	 * @param list
	 * @return
	 */
	public  int updateBatch(List<T> list);


	/**
	 * 删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param entity
	 * @return
	 */
	public int delete(T entity);

	/**
	 * 批量删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param ids
	 * @return
	 */
	public  int deleteBacth(List<Long> ids);


}
