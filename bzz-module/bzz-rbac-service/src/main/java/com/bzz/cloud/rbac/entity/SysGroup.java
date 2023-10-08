/**
 * Copyright &copy; 2012-2019 <a href="https://github.com/qianli8811/bzz-cloud">bzz-cloud</a> All rights reserved.
 */
package com.bzz.cloud.rbac.entity;

import com.bzz.cloud.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


/**
 * @desc: 用户组管理
 *
 * @Auther: yang qianli
 * @Email: 624003618@qq.com
 * @createDate: 2019-05-10 00:34:59
 * @updateDate: 2019-05-10 00:34:59
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class SysGroup extends BaseEntity<Long>   {

	private static final long serialVersionUID = 414302461896629392L;


	/**
	 * 父级编号
	 */
	@JsonBackReference
	private SysGroup parentId;

	/**
	 * 名称
	 */
	private String name;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 归属区域
	 */
	private String areaId;
	/**
	 * 区域编码
	 */
	private String code;
	/**
	 * 机构类型
	 */
	private int groupType;
	/**
	 * 机构等级
	 */
	private int grade;
	/**
	 * 联系地址
	 */
	private String address;
	/**
	 * 邮政编码
	 */
	private String zipCode;
	/**
	 * 负责人
	 */
	private String master;
	/**
	 * 电话
	 */
	private String phone;
	/**
	 * 传真
	 */
	private String fax;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 是否可用
	 */
	private boolean enabled;
	/**
	 * 主负责人
	 */
	private String primaryPerson;
	/**
	 * 副负责人
	 */
	private String deputyPerson;
	/**
	 * 用户组与角色关系：多对多
	 */

	@JsonIgnore
	private List<SysUser> sysUserList;


}
