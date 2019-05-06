package com.bzz.cloud.rbac.entity;

import com.bzz.cloud.core.entity.BaseEntity;
import lombok.*;

/**
 * @desc: 系统权限
 *
 * @Auther: yang qianli
 * @Email: 624003618@qq.com
 * @Date: 2019/2/23 13:47
 * @update:
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SysPermission extends BaseEntity<SysPermission, Long> {


    private SysPermission sysPermission;//父级权限
    private String name;//权限名称
    private int action;//资源类型:1,菜单,2按钮(CURD增删改查等)
    private String url;//跳转地址
    private boolean show;//是否显示
    private String iconUrl;//图标地址
    private String mark;//权限标志
    private int sort;//排序
    /**
     * 1.所有数据
     * 2.公司以及公司以下的数据
     * 3.部门以及部门以下的数据
     * 4.仅个人数据
     */
    private int dataScope;//数据范围


}
