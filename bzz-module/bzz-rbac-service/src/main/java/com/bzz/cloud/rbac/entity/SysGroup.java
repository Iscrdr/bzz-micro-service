package com.bzz.cloud.rbac.entity;

import com.bzz.cloud.core.entity.BaseEntity;
import lombok.*;

/**
 * @desc: 用户组:公司,部门,岗位等
 *
 * @Auther: yang qianli
 * @Email: 624003618@qq.com
 * @Date: 2019/2/23 13:46
 * @update:
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SysGroup extends BaseEntity<SysUser, Long> {

    private static final long serialVersionUID = -1777363381952778879L;

    private String name;//用户组名称: 公司,部门,岗位
    private SysGroup parentGroup;//父级公司,或父级部门
    private int sort;//排序
    private SysArea sysArea;//归属区域
    private String address;//公司或部门地址



}
