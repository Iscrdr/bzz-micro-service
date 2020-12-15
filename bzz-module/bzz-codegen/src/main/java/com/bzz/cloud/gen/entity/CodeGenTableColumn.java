package com.bzz.cloud.gen.entity;

import com.bzz.cloud.core.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CodeGenTableColumn extends BaseEntity<CodeGenTableColumn,Long>  {

	private static final long serialVersionUID = -8362254090615111148L;
	/**
	 * 列所属的表
	 */
	private CodeGenTable codeGenTable;
	/**
	 * 列名
	 */
	private String columnName;
	/**
	 * 描述
	 */
	private String comments;
	/**
	 * JDBC类型
	 */
	private String jdbcType;
	/**
	 * 是否是主键
	 */
	private Boolean isPkey;
	/**
	 * 是否是空
	 */
	private Boolean isNull;

	/**
	 * 位置,列排序
	 */
	private int position;
	/**
	 * JAVA类型
	 */
	private String javaType;
	/**
	 * JAVA字段名
	 */
	private String javaField;

	/**
	 * 是否显示
	 */
	private Boolean isShow;
	/**
	 * 是否表单字段
	 */
	private Boolean isForm;
	/**
	 * 是否查询字段
	 */
	private Boolean isQuery;
	/**
	 * 是否列表字段
	 */
	private Boolean isList;

	/**
	 * 查询类型:等于、不等于、大于、小于、范围、左LIKE、右LIKE、左右LIKE
	 */
	private String queryType;

	/**
	 * 字典类型
	 */
	private String dictType;

	/**
	 * 字典类型:字段生成方案（文本框、文本域、下拉框、复选框、单选框、字典选择、人员选择、部门选择、区域选择
	 */
	private String showType;
}


