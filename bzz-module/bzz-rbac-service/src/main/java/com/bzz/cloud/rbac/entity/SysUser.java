package com.bzz.cloud.rbac.entity;

import com.bzz.cloud.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFilter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import javax.validation.constraints.NotBlank;

import javax.validation.constraints.Email;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SysUser extends BaseEntity<SysUser, Long> {


	@NotBlank(message = "用户名不能为空!")
	private String userName;// 用户名

	private String nickName;// 昵称

	private String loginType; //登录类型

	@Size(min = 6, max = 12, message = "密码长度必须在{min}和{max}之间")
	private String password;// 密码

	private String newPassword;	// 新密码

	private String workNum;	// 工号

	private String name;	// 姓名

	@Email(message = "邮箱格式不正确")
	private String email;	// 邮箱

	private String phone;	// 电话

	@Pattern(regexp = "^((13[0-9])|(15[^4,\\\\D])|(17[0-9])|(18[0-9]))\\\\d{8}$",message = "手机号不正确")
	private String mobile;	// 手机

	private int userType;// 用户类型

	private String photo;	// 头像

	private String avatar ; //头像，化身
	private String signature; //签名
	private String title; //头衔，或者职位，例如：开发工程师，交互专家
	private String tags;//标签，JSON数据格式：[{key: '0',label: '很有想法的',}]
	private String address;//详细地址，例如：西湖区工专路 77 号
	private String geographic;// {province: {label: '浙江省',key: '330000',},city: {label: '杭州市',key: '330100',}}


	private boolean accountNonExpired;	// 账户是否过期,过期无法验证
	private boolean accountNonLocked;	// 指定用户是否被锁定或者解锁,锁定的用户无法进行身份验证
	private boolean credentialsNonExpired;	// 指示是否已过期的用户的凭据(密码),过期的凭据防止认证
	private boolean enabled;	// 是否被禁用,禁用的用户不能身份验证

	private String captcha;//短信验证码,不到数据库，用户web层校验
	private String clientId;//Oauth2客户端id

	private SysArea country; //国家，china
	private SysArea province; //省或州或者直辖市
	private SysArea city; //市
	private SysArea area; //区、县

	private QQUser qqUser; //区、县

	@JsonIgnore
	private List<SysGroup> sysGroupList;//用户组（部门）

	@JsonIgnore
	private List<SysRole> sysRoleList;//用户（角色）


	public String getUserid(){
		return this.getId()+"";
	}

	public String getAvatar(){
		return this.getPhoto();
	}

	public Map<String, Map<String,String>> getGeographic(){
		Map<String, Map<String,String>> geographic = new HashMap<>();

		Map<String,String> provinceMap = new HashMap<>();
		provinceMap.put("key",this.province!=null?this.province.getId()+"":"");
		provinceMap.put("label",this.province!=null?this.province.getName()+"":"");

		Map<String,String> cityMap = new HashMap<>();
		cityMap.put("key",this.city!=null?this.city.getId()+"":"");
		cityMap.put("label",this.city!=null?this.city.getName()+"":"");

		Map<String,String> areaMap = new HashMap<>();
		areaMap.put("key",this.area!=null?this.area.getId()+""+"":"");
		areaMap.put("label",this.area!=null?this.area.getName()+"":"");

		geographic.put("province",provinceMap);
		geographic.put("city",cityMap);
		geographic.put("area",areaMap);

		return geographic;
	}


}
