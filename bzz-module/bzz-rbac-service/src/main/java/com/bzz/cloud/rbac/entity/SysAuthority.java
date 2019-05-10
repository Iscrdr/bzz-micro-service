/**
 * Copyright &copy; 2012-2019 <a href="https://github.com/qianli8811/bzz-cloud">bzz-cloud</a> All rights reserved.
 */
package com.bzz.cloud.rbac.entity;

import com.bzz.cloud.core.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @desc: 权限表管理
 *
 * @Auther: yang qianli
 * @Email: 624003618@qq.com
 * @createDate: 2019-05-10 00:35:14
 * @updateDate: 2019-05-10 00:35:14
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class SysAuthority extends BaseEntity<SysAuthority,Long>   {

	private String name ;  // 功能名称，例如：用户管理
	private String code ;  // 权限名称，大写，例如：ADMIN，USER

	private SysApi sysApi; // 权限对应的 api

}
