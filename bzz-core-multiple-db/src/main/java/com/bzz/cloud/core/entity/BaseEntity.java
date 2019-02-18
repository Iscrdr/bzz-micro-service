package com.bzz.cloud.core.entity;


import com.bzz.common.Utils.Page;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @param <T> 当前实体类泛型
 * @param <PK> 主键
 */

public abstract class BaseEntity<T,PK extends Serializable> implements Serializable {

	private static final long serialVersionUID = 3927629227639449292L;
	protected PK id;//实体编号（唯一标识）
	protected Page<T> page;//当前实体分页对象
	protected Long createUserId; //创建时间
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	protected Date createTime; //创建时间
	protected Long updateUserId; //修改时间
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	protected Date updateTime; //修改时间
	protected String remarks; // 描述,备忘录
	protected String todo; // 描述,备忘录
	protected int delFlag; //假删除，0正常，1删除、作废
	protected int version;//版本号，默认从1开始

	public BaseEntity() {

	}


	@JsonIgnore
	@XmlTransient
	public PK getId() {
		return id;
	}
	@JsonIgnore
	@XmlTransient
	public void setId(PK id) {
		this.id = id;
	}

	//protected String dbType;//数据库类型,如果为null,默认为mysql,目前支持oracle,mysql,mssql
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	/*public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}*/

	public int getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(long createUserId) {
		this.createUserId = createUserId;
	}

	public Long getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(long updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getTodo() {
		return todo;
	}

	public void setTodo(String todo) {
		this.todo = todo;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}
}
