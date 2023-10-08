package com.bzz.cloud.core.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @param <PK> 主键类型
 */


public abstract class BaseEntity<PK extends Serializable> {

	private PK id;//实体编号（唯一标识）
	//protected Page<T> page;//当前实体分页对象
	public Long createUserId; //创建时间
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Date createTime; //创建时间
	public Long updateUserId; //修改时间
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Date updateTime; //修改时间
	public String remarks; // 描述,备忘录
	public String todo; // 描述,备忘录
	public int delFlag; //假删除，0正常，1删除、作废
	public int version;//版本号，默认从1开始


	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Date beginTime; //开始时间
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Date endTime; //结束时间


	@XmlTransient
	public PK getId() {
		return id;
	}

	@XmlTransient
	public void setId(PK id) {
		this.id = id;
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



	public int getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}



	public String getTodo() {
		return todo;
	}

	public void setTodo(String todo) {
		this.todo = todo;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public Long getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}


}
