package com.bzz.cloud.gen.entity;

import com.bzz.cloud.core.entity.BaseEntity;
import com.bzz.cloud.gen.vo.ICodeGenTable;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * @desc: 表 属性
 * @Auther: cloud
 * @Email: 624003618@qq.com
 * @Date: 2019/2/26 10:46
 * @update:
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CodeGenTable extends BaseEntity<CodeGenTable, Long>  {

	private static final long serialVersionUID = -7171911181062675226L;
	/**
	 * 表明
	 */
	@JsonView(ICodeGenTable.class)
	private String tableName;
	/**
	 * 注释
	 */
	@JsonView(ICodeGenTable.class)
	private String comments;
	/**
	 * 实体类名称 首写字母大写
	 */
	@JsonView(ICodeGenTable.class)
	private String className;
	/**
	 * 实体类名称 首写字母小写
	 */
	@JsonView(ICodeGenTable.class)
	private String classNameLower;
	 /**
	 * 表列
	 */
	private List<CodeGenTableColumn> columnList = new ArrayList();

}


