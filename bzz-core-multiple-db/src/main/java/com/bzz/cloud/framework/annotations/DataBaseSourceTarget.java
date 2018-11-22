package com.bzz.cloud.framework.annotations;



import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface DataBaseSourceTarget {
	String dataSourceValue() default "";//数据库名称
	String dataBaseDialect() default "";//数据库方言，用户不同的数据库分页
	
}
