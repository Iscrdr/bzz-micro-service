
package com.bzz.cloud.rbac.entity;

import com.bzz.cloud.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * @desc: Api表管理
 *
 * @author: yang qianli
 * @email: 624003618@qq.com
 * @createDate: 2019-05-10 00:34:36
 * @updateDate: 2019-05-10 00:34:36
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class SysMenu extends BaseEntity<SysMenu,Long> implements Comparable<SysMenu>  {


	private static final long serialVersionUID = -8909227574776991224L;
	/**
	 * key 前端 ant design pro 菜单要求的key,最好设置
	 */
	private String key;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 路由
	 */
	private String path;


	/**
	 * 父id
	 */
	@JsonBackReference
	private SysMenu parentMenu;
	/**
	 * 是否是菜单
	 */
	private boolean menu;

	/**
	 * 图标：图片地址
	 */
	private String icon;

	/**
	 * 组件
	 */
	private String component;

	/**
	 * 国际化
	 */
	private String locale;

	/**
	 * 子菜单
	 */
	@JsonManagedReference
	private List<SysMenu> children;

	/**
	 * 对应的权限
	 */
	@JsonIgnore
	private List<SysAuthority> authorityList;

	/**
	 * 权限字符串：ADMIN_QUERY,ADMIN_ADD
	 */
	private String authority;

	@JsonIgnore
	public String getAuthorityStr(){
		String authority = null;
		List<SysAuthority> authorityList = this.authorityList;
		for (SysAuthority sysAuthority : authorityList){
			authority = sysAuthority.getAuthority().toUpperCase()+"_";
		}
		if(StringUtils.isNotBlank(authority) && authority.endsWith("_")){
			authority = StringUtils.substring(authority,0,authority.length()-1);
		}
		return authority;
	}



	/**
	 * 跳转到
	 */
	private String redirect;

	/**
	 * 可以在菜单中不展示这个路由，包括子路由。
	 */
	private boolean hideInMenu;
	/**
	 * 用于隐藏不需要在菜单中展示的子路由
	 */
	private boolean hideChildrenInMenu;
	/**
	 * 排序
	 */
	private Integer sort;


	@Override
	public int compareTo(SysMenu o) {
		return this.sort - o.getSort();
	}
}
