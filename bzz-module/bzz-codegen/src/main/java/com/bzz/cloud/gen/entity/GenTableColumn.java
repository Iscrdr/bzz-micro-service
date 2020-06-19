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
public class GenTableColumn extends BaseEntity<GenTableColumn,Long> implements Serializable {

	private static final long serialVersionUID = -8362254090615111148L;
	private GenTable genTable;	// 归属表
	private String columnName; 		// 列名
	private String comments;	// 描述
	private String jdbcType;	// JDBC类型
	private String javaType;	// JAVA类型
	private String javaField;	// JAVA字段名


	
}


