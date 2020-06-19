package com.bzz.cloud.framework.dynamicdatasource;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-三月-15 22-32
 * @Modified by:
 * @Description: 数据源线程类，存储的是数据源
 */
public class DataSourceContextHolder {
	
	/**
	 * ArrayList<String>,第一个元素为datasource,第一个元素为dialect
	 */
	private static final ThreadLocal<Map<String,Object>> contextHolder = new ThreadLocal<>();
	
	public static void setDataSourceAndDialect(Map<String,Object> map) {
		contextHolder.set(map);
	}
	
	public static Map<String,Object> getDataSourceAndDialect() {
		Map<String,Object> dataSourceAndDialect = contextHolder.get();
		if(null == dataSourceAndDialect || dataSourceAndDialect.size() <= 0){
			//设置默认的数据库名称，方言，分页参数
			Map<String,Object> map = new HashMap<>(3);
			map.put("dataSource","dataSourceA");
			map.put("dialect","mysql");
			contextHolder.set(map);
		}
		
		return contextHolder.get();
	}

	public static void removeDataSource() {
		contextHolder.remove();
	}
	
}