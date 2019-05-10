/**
 * Copyright &copy; 2012-2019 <a href="https://github.com/qianli8811/bzz-cloud">bzz-cloud</a> All rights reserved.
 */
package com.bzz.cloud.rbac.entity;

import com.bzz.cloud.core.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @desc: Api表管理
 *
 * @Auther: yang qianli
 * @Email: 624003618@qq.com
 * @createDate: 2019-05-10 00:34:36
 * @updateDate: 2019-05-10 00:34:36
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class SysApi extends BaseEntity<SysApi,Long>  {

		private String name ;  // 名称 
		private String url ;  // 请求URL 
		private Integer sort ;  // 排序 
		private String icon ;  // 图标：图片地址 
		private boolean isShow ;  // 是否显示




}
