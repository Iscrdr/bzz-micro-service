package com.bzz.cloud.rbac.entity;

import com.bzz.cloud.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import java.util.List;

/**
 * @desc: 角色实体类
 *
 * @author: yang qianli
 * @email: 624003618@qq.com
 * @date: 2019/2/23 13:45
 * @update: yang qianli
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SysRole extends BaseEntity<Long> {


    private static final long serialVersionUID = -9149665408422812632L;


    /**
     * 角色名,中文名称:管理员
     */
    private String name ;
    /**
     * 角色代码：例如,admin
     */
    private String code;
    /**
     * 角色类型
     */
    private String roleType ;

    /**
     * 是否是系统用户：指开发者
     */
    private boolean isSys ;
    /**
     * 是否可用
     */
    private boolean enabled ;

    /**
     * 一个角色 可以授权给多个用户
     */
    @JsonIgnore
    private List<SysUser> sysUserList ;

    /**
     * 一个角色对应的权限
     */
    @JsonManagedReference
    private List<SysMenu> sysMenuList ;

}
