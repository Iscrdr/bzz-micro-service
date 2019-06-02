package com.bzz.cloud.rbac.entity;

import com.bzz.cloud.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * @desc: 角色实体类
 *
 * @Auther: yang qianli
 * @Email: 624003618@qq.com
 * @Date: 2019/2/23 13:45
 * @update: yang qianli
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SysRole extends BaseEntity<SysRole, Long> {


    private static final long serialVersionUID = -9149665408422812632L;

    private String name ; // 角色名
    private String roleType ; // 角色类型
    private boolean isSys ; // 是否是系统用户：指开发者
    private boolean enabled ; // 是否可用

    @JsonIgnore
    private List<SysUser> sysUserList ; //一个角色 对应多个用户

    @JsonManagedReference
    private List<SysApi> sysApiList ; // 一个角色 对应多个api

}