
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

public class BaseService <T extends BaseEntity ,PK extends Serializable> {

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	protected BzzBaseDao<T,PK> bzzBaseDao;

	public boolean checkUnique(T entity) {
		List list = bzzBaseDao.selectList(entity);
		if(null != list && list.size()>0){
			return false;
		}
		return true;
	}

	/**
	 * 根据ID获取单条数据
	 * @param id
	 * @return
	 */
	public Serializable get(PK id){
		return bzzBaseDao.get(id);
	}
	/**
	 * 插入数据
	 * @param entity
	 * @return Object 主键类型
	 */
	public long insert(T entity){
		return bzzBaseDao.insert(entity);
	}

	/**
	 * 根据属性查询数据列表
	 * @param entity
	 * @return
	 */
	public List<T> findList(T entity){
		return bzzBaseDao.findList(entity);
	}


	/**
	 * 分页查询
	 */
	public Page findPage(Page page,T entity) {
		page = bzzBaseDao.findPage(page, entity);
		return page;
	}

	/**
	 * 查询所有数据列表
	 * @param entity
	 * @return
	 */
	public  List<T> findAllList(T entity){
		return  bzzBaseDao.findAllList(entity);
	}

	/**
	 * 查询总条数
	 * @param entity
	 * @return
	 */
	public  int findCount(T entity){
		return  bzzBaseDao.findCount(entity);
	}



	/**
	 * 批量插入数据
	 * @param list
	 * @return
	 */
	@Transactional(readOnly = false)
	public  int insertBatch(List<T> list){
		return  bzzBaseDao.insertBatch(list);
	}

	/**
	 * 更新单条数据
	 * @param entity
	 * @return
	 */
	@Transactional(readOnly = false)
	public int update(T entity){
		return  bzzBaseDao.update(entity);
	}

	/**
	 * 批量更新数据
	 * @param list
	 * @return
	 */
	@Transactional(readOnly = false)
	public  int updateBatch(List<T> list){
		return  bzzBaseDao.updateBatch(list);
	}



	/**
	 * 删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param entity
	 * @return
	 */
	@Transactional(readOnly = false)
	public int delete(T entity){
		return  bzzBaseDao.delete(entity);
	}

	/**
	 * 批量删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param ids
	 * @return
	 */
	public  int deleteBacth(List<Long> ids){
		return  bzzBaseDao.deleteBacth(ids);
	}


}
