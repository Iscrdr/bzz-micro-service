/**
 * Copyright &copy; 2012-2019 <a href="https://github.com/qianli8811/bzz-cloud">bzz-cloud</a> All rights reserved.
 */
package com.bzz.cloud.rbac.entity;

import com.bzz.cloud.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class SysGroup extends BaseEntity<SysGroup,Long>   {

	private static final long serialVersionUID = 414302461896629392L;


	@JsonBackReference
		private SysGroup parentId ;  // 父级编号

		private String name ;  // 名称 
		private Integer sort ;  // 排序 
		private String areaId ;  // 归属区域 
		private String code ;  // 区域编码 
		private int groupType ;  // 机构类型
		private int grade ;  // 机构等级
		private String address ;  // 联系地址 
		private String zipCode ;  // 邮政编码 
		private String master ;  // 负责人 
		private String phone ;  // 电话 
		private String fax ;  // 传真 
		private String email ;  // 邮箱 
		private boolean enabled ;  // 是否可用 
		private String primaryPerson ;  // 主负责人 
		private String deputyPerson ;  // 副负责人

	    @JsonIgnore
		private List<SysUser> sysUserList; //用户组与角色关系：多对多


}
