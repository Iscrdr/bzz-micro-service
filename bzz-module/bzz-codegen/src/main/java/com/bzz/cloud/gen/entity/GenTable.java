package com.bzz.cloud.gen.entity;

import com.bzz.cloud.core.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * @desc: 表 属性
 *
 * @Auther: cloud
 * @Email: 624003618@qq.com
 * @Date: 2019/2/26 10:46
 * @update:
 */
@Getter
@Setter
@NoArgsConstructor
public class GenTable extends BaseEntity<GenTable,Long> implements Serializable {

	private static final long serialVersionUID = -7171911181062675226L;
	private String tableName; 	// 表名
	private String comments;		// 注释
	private String className;		// 实体类名称
	private String classNameLower;		// 实体类名称 首写字母小写
	private List<GenTableColumn> columnList = new ArrayList();	// 表列

}


