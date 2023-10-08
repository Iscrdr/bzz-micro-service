/**
 * Copyright &copy; 2012-2019 <a href="https://github.com/qianli8811/bzz-cloud">bzz-cloud</a> All rights reserved.
 */
package com.bzz.cloud.rbac.entity;

import com.bzz.cloud.core.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * @description：功能
 * @author     ：Iscrdr
 * @date       ：2020-03-06 03:01
 * @email      ：624003618@qq.com
 * @modified By：
 * @version:     1.0.0
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class SysAuthority extends BaseEntity<Long>   {


	private static final long serialVersionUID = -8926621434493419399L;



	/**
	 * 功能名称，例如：用户管理
 	 */

	private String name ;
	/**
	 * 权限名称，大写，例如：ADMIN，USER
 	 */
	private String authority ;





}
