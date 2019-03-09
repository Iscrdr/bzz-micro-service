package com.bzz.cloud.rbac.entity;

import com.bzz.cloud.core.entity.BaseEntity;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SysUser extends BaseEntity<SysUser, Long> {

	private static final long serialVersionUID = 5683080187082892863L;


	private String loginName;// 登录名
	private String oldLoginName;// 原登录名
	@NotNull(message = "密码不能为空!")
	@Size(min = 6, max = 12, message = "密码长度必须在{min}和{max}之间")
	private String password;// 密码
	private String newPassword;	// 新密码
	private String workNum;	// 工号
	private String name;	// 姓名
	@NotNull(message = "邮件不能为空!")
	@Email(message = "邮件格式不正确")
	private String email;	// 邮箱
	private String phone;	// 电话
	@NotNull(message = "手机号不能为空!")
	@Pattern(regexp = "^((13[0-9])|(15[^4,\\\\D])|(17[0-9])|(18[0-9]))\\\\d{8}$",message = "手机号不正确")
	private String mobile;	// 手机
	private int userType;// 用户类型
	private String photo;	// 头像
	private boolean accountNonExpired;	// 账户是否过期,过期无法验证
	private boolean accountNonLocked;	// 指定用户是否被锁定或者解锁,锁定的用户无法进行身份验证
	private boolean credentialsNonExpired;	// 指示是否已过期的用户的凭据(密码),过期的凭据防止认证
	private boolean enabled;	// 是否被禁用,禁用的用户不能身份验证


}
