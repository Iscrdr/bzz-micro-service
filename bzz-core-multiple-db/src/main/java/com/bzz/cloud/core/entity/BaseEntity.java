package com.bzz.cloud.core.entity;


import com.bzz.common.Utils.Page;

import java.io.Serializable;


public abstract class BaseEntity<T, PK extends Serializable> {
	
	/**
	 * 实体编号（唯一标识）
	 */
	PK id;

	/**
	 * 当前实体分页对象
	 */
	Page<T> page;
	
	
	
	/**
	 * 是否是新记录（默认：false），调用setIsNewRecord()设置新记录，使用自定义ID。
	 * 设置为true后强制执行插入语句，ID不会自动生成，需从手动传入。
	 */
	boolean isNewRecord = false;
	
	BaseEntity() {
	
	}
	
	BaseEntity(PK id) {
		this();
		this.id = id;
	}

	public PK getId() {
		return id;
	}

	public void setId(PK id) {
		this.id = id;
	}
	public Page<T> getPage() {
		if (page == null){
			page = new Page<T>();
		}
		return page;
	}
	
	public Page<T> setPage(Page<T> page) {
		this.page = page;
		return page;
	}
	
	
	
	/**
	 * 插入之前执行方法，子类实现
	 */
	/*public abstract void preInsert();
	
	*//**
	 * 更新之前执行方法，子类实现
	 *//*
	public abstract void preUpdate();
	*/
	/**
	 * 是否是新记录（默认：false），调用setIsNewRecord()设置新记录，使用自定义ID。
	 * 设置为true后强制执行插入语句，ID不会自动生成，需从手动传入。
	 * @return
	 */
	public boolean getIsNewRecord() {
		return isNewRecord || null == getId();
	}
	
	/**
	 * 是否是新记录（默认：false），调用setIsNewRecord()设置新记录，使用自定义ID。
	 * 设置为true后强制执行插入语句，ID不会自动生成，需从手动传入。
	 */
	public void setIsNewRecord(boolean isNewRecord) {
		this.isNewRecord = isNewRecord;
	}
	

	/**
	 * 删除标记（0：正常；1：删除；2：审核；）
	 */
	public static final int DEL_FLAG_NORMAL = 0;
	public static final int DEL_FLAG_DELETE = 1;
	public static final int DEL_FLAG_EDIT = 2;
}
