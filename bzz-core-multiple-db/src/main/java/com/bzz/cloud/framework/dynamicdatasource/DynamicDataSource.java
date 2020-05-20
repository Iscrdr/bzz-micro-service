package com.bzz.cloud.framework.dynamicdatasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-三月-15 22-32
 * @Modified by:
 * @Description: 动态数据源
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
	@Override
	protected Object determineCurrentLookupKey() {
		return DataSourceContextHolder.getDataSourceAndDialect().get("dataSource");
	}
	
}
