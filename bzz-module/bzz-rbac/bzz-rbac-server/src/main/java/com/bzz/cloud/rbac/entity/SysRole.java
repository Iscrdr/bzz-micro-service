package com.bzz.cloud.rbac.entity;

import com.bzz.cloud.core.entity.BaseEntity;
import lombok.*;

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

    private String name;//角色名称
    private String roleType;//角色类型

}
