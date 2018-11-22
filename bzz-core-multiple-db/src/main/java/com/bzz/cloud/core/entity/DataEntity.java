package com.bzz.cloud.core.entity;


import com.bzz.common.Utils.IdUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

public abstract class DataEntity<T> extends BaseEntity<T> {
	
	private static final long serialVersionUID = -6539941424995082924L;
	
	protected Date createDate; //创建时间
	protected Date updateDate; //修改时间
	
	protected Object createUser; //创建人
	protected Object updateUser; //修改人
	
	protected String remarks;	// 备注
	protected String delFlag; //作废标记(逻辑删除)，0正常，1删除、作废
	
	
	@Override
	public void preInsert() {
		// 不限制ID为UUID，调用setIsNewRecord()使用自定义ID
		if (!this.isNewRecord){
			if(StringUtils.isBlank(this.id)){
				this.setId(IdUtils.uuid());
			}
		}
		this.createUser = currentUser;
		this.updateUser = currentUser;
		
		this.updateDate = new Date();
		this.createDate = this.updateDate;
	}
	
	@Override
	public void preUpdate() {
		this.updateDate = new Date();
		this.updateUser = createUser;
	}
}
