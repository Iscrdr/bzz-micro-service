package com.bzz.cloud.core.entity;

public abstract class TreeEntity<T> extends DataEntity<T> {
	
	private static final long serialVersionUID = -378912226267025855L;
	
	protected T parent;	// 父级编号
	protected String parentIds; // 所有父级编号
	protected String name; 	// 机构名称
	protected Integer sort;		// 排序
}
