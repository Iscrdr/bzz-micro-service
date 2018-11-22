package com.bzz.cloud.framework.dynamicdatasource;

import java.util.ArrayList;
import java.util.List;

public class DataSourceContextHolder {
	
	//ArrayList<String>,第一个元素为datasource,第一个元素为dialect
	private static final ThreadLocal<List<String>> contextHolder = new ThreadLocal<List<String>>();
	
	public static void setDataSourceAndDialect(List<String> list) {
		contextHolder.set(list);
	}
	
	public static List<String> getDataSourceAndDialect() {
		List<String> dataSourceAndDialect = contextHolder.get();
		if(null == dataSourceAndDialect || dataSourceAndDialect.size() <= 0){
			//设置默认的数据库名称和方言
			List<String> list = new ArrayList<String>(2);
			list.add("dataSourceA");
			list.add("mysql");
			contextHolder.set(list);
		}
		
		return contextHolder.get();
	}
	
	
	public static void removeDataSource() {
		contextHolder.remove();
	}
	
}