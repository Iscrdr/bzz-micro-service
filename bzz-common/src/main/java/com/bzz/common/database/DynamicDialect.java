package com.bzz.common.database;

public interface DynamicDialect {
	
	/**
	 * 将sql转换为分页SQL，分别调用分页sql
	 * @param sql    SQL语句
	 * @param pageNo 开始条数
	 * @param pageSize  每页显示多少纪录条数
	 * @return 分页查询的sql
	 */
	String getPageSql(String sql, int pageNo, int pageSize);

	/**
	 * 将sql转换为获取总条数sql
	 * @param sql    SQL语句
	 * @return 总条数sql
	 */
	String getCountSqlString(String sql);

}
