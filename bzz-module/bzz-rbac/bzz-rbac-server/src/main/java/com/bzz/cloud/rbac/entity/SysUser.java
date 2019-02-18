package com.bzz.cloud.rbac.entity;

import com.bzz.cloud.core.entity.BaseEntity;
import lombok.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SysUser extends BaseEntity<SysUser, Long> {

	private static final long serialVersionUID = 5683080187082892863L;

	private String loginName;// 登录名
	private String oldLoginName;// 原登录名
	private String password;// 密码
	private String newPassword;	// 新密码
	private String workNum;	// 工号
	private String name;	// 姓名
	private String email;	// 邮箱
	private String phone;	// 电话
	private String mobile;	// 手机
	private int userType;// 用户类型
	private String photo;	// 头像
	private boolean accountNonExpired;	// 账户是否过期,过期无法验证
	private boolean accountNonLocked;	// 指定用户是否被锁定或者解锁,锁定的用户无法进行身份验证
	private boolean credentialsNonExpired;	// 指示是否已过期的用户的凭据(密码),过期的凭据防止认证
	private boolean enabled;	// 是否被禁用,禁用的用户不能身份验证


}
