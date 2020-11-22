package com.bzz.cloud.rbac.entity;

import com.bzz.cloud.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc: 区域实体类
 *
 * @author: yang qianli
 * @email: 624003618@qq.com
 * @date: 2019/2/23 13:46
 * @update:
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class SysArea extends BaseEntity<SysArea, Long> {


    private static final long serialVersionUID = 8177559273616895780L;
    /**
     * 城市名称
     */
    private String name ;
    /**
     * 上级地区
     */
    private SysArea sysArea ;
    /**
     * 城市短名称
     */
    private String shortName ;
    /**
     * 级别
     */
    private Integer levelType ;
    /**
     * 城市编码
     */
    private String cityCode ;
    /**
     * 邮编
     */
    private String zipCode ;
    /**
     * 所属行政区域
     */
    private String mergerName ;
    /**
     * 维度
     */
    private Double lng ;
    /**
     * 经度
     */
    private Double lat ;
    /**
     * 全拼每个汉字首字母大写
     */
    private String pinyin ;
    /**
     * 简拼大写
     */
    private String jianpin ;

    /**
     * 所有的下级行政区
     */
    private List<SysArea> childSysAreaList ;

    /**
     * @desc: 获取所有下级行政区域
     *
     * @Auther: cloud
     * @Email: 624003618@qq.com
     * @Date: 2019/5/20 17:05
     * @update:
     */
    public List<SysArea> getSysAreaList(List<SysArea> sysAreas,Long parentId){
        if(null == sysAreas && sysAreas.size()<=0){
            return null;
        }
        List<SysArea> childSysArea = new ArrayList<SysArea>();
        for (SysArea sysArea:sysAreas){
            SysArea parentArea = sysArea.getSysArea();
            //遍历出父id等于参数的id，add进子节点集合
            if(parentArea.getId() == parentId){
                childSysArea.add(sysArea);
                //递归遍历下一级
                getSysAreaList(childSysArea,sysArea.getId());
            }
        }
        return childSysArea;
    }

}
