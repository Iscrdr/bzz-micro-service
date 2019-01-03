package com.bzz.cloud.core.entity;


import java.util.Date;

/**
 *
 * @param <T> 当前实体类
 * @param <U> 当前操作数据的用户
 */
public abstract class DataEntity<T,U> extends BaseEntity {
	
	private static final long serialVersionUID = -6539941424995082924L;
	
	protected Date createDate; //创建时间
	protected Date updateDate; //修改时间
	
	protected U createUser; //创建人
	protected U updateUser; //修改人
	
	protected String remarks; // 备注
	protected String delFlag; //作废标记(逻辑删除)，0正常，1删除、作废
	

}
