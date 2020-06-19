package com.bzz.cloud.core.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Date;

/**
 * @description：公共字段
 * @author     ：Iscrdr
 * @date       ：2020/02/25 2:54
 * @email      ：624003618@qq.com
 * @modified By：
 * @version:     1.0.0
 */
public abstract class BaseEntity<T,PK> implements Serializable {


	private static final long serialVersionUID = 3927629227639449292L;
	/**
	 * 实体编号（唯一标识）
	 */
	protected PK id;

	/**
	 * 创建时间
	 */

	protected Long createUserId;
	/**
	 * 创建时间
	 */

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	protected Date createTime;
	/**
	 * 修改人id
	 */

	protected Long updateUserId;
	/**
	 * 修改时间
	 */

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	protected Date updateTime;
	/**
	 * 描述,备忘录
	 */

	protected String remarks;
	/**
	 * 描述,备忘录
	 */

	protected String todo;
	/**
	 * 假删除，1正常，0删除、作废
	 */

	protected int delFlag;
	/**
	 * 版本号，默认从1开始
	 */

	protected int version;

	/**
	 * 开始时间 （时间查询辅助字段）
	 */

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	protected Date beginTime;
	/**
	 * 结束时间（时间查询辅助字段）
	 */

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	protected Date endTime;



	public BaseEntity() {

	}


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
