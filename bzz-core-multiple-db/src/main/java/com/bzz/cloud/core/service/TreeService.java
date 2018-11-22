
package com.bzz.cloud.core.service;


import com.bzz.cloud.core.dao.TreeDao;
import com.bzz.cloud.core.entity.TreeEntity;
import org.springframework.transaction.annotation.Transactional;



/**
 * Service基类
 * @author yang-ql
 * @version 2014-05-16
 */
@Transactional(readOnly = true)
public abstract class TreeService<D extends TreeDao<T>, T extends TreeEntity<T>> extends CrudService<D, T> {
	
	@Transactional(readOnly = false)
	public void save(T entity) {
		// 保存或更新实体
		super.save(entity);
	}
	/**
	 * 预留接口，用户更新子节前调用
	 * @param childEntity
	 */
	protected void preUpdateChild(T entity, T childEntity) {
	}

}
