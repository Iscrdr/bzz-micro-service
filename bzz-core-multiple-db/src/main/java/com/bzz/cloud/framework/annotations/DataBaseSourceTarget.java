package com.bzz.cloud.framework.annotations;



import java.lang.annotation.*;
/**
 * @author Yang qianli
 * @version 1.0.0
 * @ProjectName bzz-cloud
 * @Description: 数据源，用于不同的数据源分页
 * @email 624003618@qq.com
 * @date 2019-01-07 01:20:44
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface DataBaseSourceTarget {
	/**
	 * 数据库名称
	 */

	String dataSourceValue() default "";
	/**
	 * 数据库方言，用户不同的数据库分页
	 */
	String dataBaseDialect() default "";
	
}
