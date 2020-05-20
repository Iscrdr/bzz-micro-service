package com.bzz.cloud.rbac.entity;

import com.bzz.cloud.core.entity.BaseEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.validation.constraints.NotBlank;

import javax.validation.constraints.Email;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author : yang qianli
 * @email: 624003618@qq.com
 * @date: 2019-05-27 02-06
 * @modified by:
 * @description: 系统用户
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SysUser extends BaseEntity<SysUser, Long> {


	private static final long serialVersionUID = -5623758613315391973L;
	/**
	 * 用户名
	 */
	@NotBlank(message = "用户名不能为空!")
	private String userName;

	/**
	 * 昵称
	 */
	private String nickName;

	/**
	 * 登录类型
	 */
	private String loginType;

	/**
	 * 秘密
	 */
	@Size(min = 6, max = 12, message = "密码长度必须在{min}和{max}之间")
	private String password;

	/**
	 * 新密码
	 */
	private String newPassword;

	/**
	 * 工号
	 */
	private String workNum;

	/**
	 * 真实姓名
	 */
	private String name;
	/**
	 * 邮箱
	 */
	@Email(message = "邮箱格式不正确")
	private String email;

	/**
	 * 电话
	 */
	private String phone;

	/**
	 * 手机
	 */
	@Pattern(regexp = "^((13[0-9])|(15[^4,\\\\D])|(17[0-9])|(18[0-9]))\\\\d{8}$",message = "手机号不正确")
	private String mobile;

	/**
	 * 用户类型
	 */
	private int userType;
	/**
	 * 头像
	 */
	private String photo;
	/**
	 * 头像，化身 @see photo
	 */
	private String avatar ;
	/**
	 * 签名
	 */
	private String signature;
	/**
	 * 头衔，或者职位，例如：开发工程师，交互专家
	 */
	private String title;
	/**
	 * 标签，JSON数据格式：[{key: '0',label: '很有想法的',}]
	 */
	private String tags;
	/**
	 * 详细地址，例如：西湖区工专路 77 号
	 */
	private String address;
	/**
	 * {province: {label: '浙江省',key: '330000',},city: {label: '杭州市',key: '330100',}}
	 */
	private String geographic;

	/**
	 * 账户是否过期,过期无法验证 ，true:未过期，false:过期
	 */
	private boolean accountNonExpired;
	/**
	 * 指定用户是否被锁定或者解锁,锁定的用户无法进行身份验证 true:未被锁定，false:被锁定
	 */
	private boolean accountNonLocked;
	/**
	 * 指示是否已过期的用户的凭据(密码),过期的凭据防止认证 true:未过期，false:过期
	 */
	private boolean credentialsNonExpired;
	/**
	 * 是否被禁用,禁用的用户不能身份验证，true：未被禁用，false:禁用
	 */
	private boolean enabled;

	/**
	 * 短信验证码,不到数据库，用户web层校验
	 */
	private String captcha;
	/**
	 * Oauth2客户端id
	 */
	private String clientId;
	/**
	 * 国家，china
	 */
	private SysArea country;
	/**
	 * 省或州或者直辖市
	 */
	private SysArea province;
	/**
	 * 市
	 */
	private SysArea city;
	/**
	 * 区、县
	 */
	private SysArea area;
	/**
	 * qq账号信息
	 */
	private QQUser qqUser;

	/**
	 * 用户组（部门）
	 */
	@JsonIgnore
	private List<SysGroup> sysGroupList;
	/**
	 * 用户（角色）
	 */
	@JsonIgnore
	private List<SysRole> sysRoleList;


	public String getUserid(){
		return this.getId()+"";
	}

	public String getAvatar(){
		return this.getPhoto();
	}

	@JsonIgnore
	public Map<String, Map<String,String>> getGeographic(){
		Map<String, Map<String,String>> geographic = new HashMap<>(3);

		Map<String,String> provinceMap = new HashMap<>(2);
		provinceMap.put("key",this.province!=null?this.province.getId()+"":"");
		provinceMap.put("label",this.province!=null?this.province.getName()+"":"");

		Map<String,String> cityMap = new HashMap<>(2);
		cityMap.put("key",this.city!=null?this.city.getId()+"":"");
		cityMap.put("label",this.city!=null?this.city.getName()+"":"");

		Map<String,String> areaMap = new HashMap<>(2);
		areaMap.put("key",this.area!=null?this.area.getId()+""+"":"");
		areaMap.put("label",this.area!=null?this.area.getName()+"":"");

		geographic.put("province",provinceMap);
		geographic.put("city",cityMap);
		geographic.put("area",areaMap);

		return geographic;
	}


}
