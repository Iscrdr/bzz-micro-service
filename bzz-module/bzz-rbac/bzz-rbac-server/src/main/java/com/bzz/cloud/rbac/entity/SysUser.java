package com.bzz.cloud.rbac.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bzz.common.page.Page;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_user")
public class SysUser  implements Serializable {
	
	private static final long serialVersionUID = -6539941424995082924L;
	@TableId
	private String id;// id
	private String loginName;// 登录名
	private String password;// 密码
	private String workNum;	// 工号
	private String name;	// 姓名
	private String email;	// 邮箱
	private String phone;	// 电话
	private String mobile;	// 手机
	private String userType;// 用户类型

	private String loginFlag;	// 是否允许登陆
	private String photo;	// 头像
	
	private String oldLoginName;// 原登录名
	private String newPassword;	// 新密码


	
}
