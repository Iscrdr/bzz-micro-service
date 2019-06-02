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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @desc: Api表管理
 *
 * @Auther: yang qianli
 * @Email: 624003618@qq.com
 * @createDate: 2019-05-10 00:34:36
 * @updateDate: 2019-05-10 00:34:36
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class SysApi extends BaseEntity<SysApi,Long> implements Comparable<SysApi>  {

	private static final long serialVersionUID = -8909227574776991224L;

	@JsonBackReference
	private SysApi sysApi; //父id
	private boolean menu;	//是否是菜单
	private String name;  // 名称
	private String path; //路由,或者http restful api请求路径
	private String component; //组件
	private String redirect; //跳转到
	private String icon;  // 图标：图片地址
	private boolean hideInMenu;//是否隐藏
	private boolean hideChildrenInMenu;//子菜单是否隐藏
	private Integer sort;  // 排序

	@JsonManagedReference
	private List<SysApi> routes; //子菜单

	@JsonIgnore
	private List<SysAuthority> sysAuthorityList; // 一个api对应国歌权限

	public List<String> getAuthority(){
		List<SysAuthority> sysAuthorities = this.getSysAuthorityList();
		List<String> authority = new ArrayList<>();
		if(sysAuthorities!=null && sysAuthorities.size()>0){
			for (SysAuthority sysAuthority:sysAuthorities) {
				authority.add(sysAuthority.getCode());
			}
		}
		return authority;
	}


	@Override
	public int compareTo(SysApi o) {
		return this.sort - o.getSort();
	}
}
