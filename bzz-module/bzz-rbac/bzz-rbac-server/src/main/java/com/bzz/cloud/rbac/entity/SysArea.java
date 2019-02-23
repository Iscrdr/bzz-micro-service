package com.bzz.cloud.rbac.entity;

import com.bzz.cloud.core.entity.BaseEntity;
import lombok.*;
/**
 * @desc: 区域实体类
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
public class SysArea extends BaseEntity<SysArea, Long> {

    private SysArea sysArea;//父级编号
    private String code;//区域编码
    private String name;// 区域名称
    private int sort;//排序


}
