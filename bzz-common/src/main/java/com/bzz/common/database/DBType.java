package com.bzz.common.database;

/**
 * @program: bzz-cloud
 * @description: 数据库类型
 * @author: Yang qianli
 * @create: 2018-10-18 16:12
 * @version: 1.0.0
 */
public enum DBType {
	/**
	 * 各种数据库类型
	 */
	MYSQL("mysql", "%s LIKE CONCAT('%%',#{%s},'%%')", "MySql数据库"),
	MARIADB("mariadb", "%s LIKE CONCAT('%%',#{%s},'%%')", "MariaDB数据库"),
	ORACLE("oracle", "%s LIKE CONCAT(CONCAT('%%',#{%s}),'%%')", "Oracle数据库"),
	DB2("db2", "%s LIKE CONCAT(CONCAT('%%',#{%s}),'%%')", "DB2数据库"),
	H2("h2", "%s LIKE CONCAT('%%',#{%s},'%%')", "H2数据库"),
	HSQL("hsql", "%s LIKE CONCAT('%%',#{%s},'%%')", "HSQL数据库"),
	SQLITE("sqlite", "%s LIKE CONCAT('%%',#{%s},'%%')", "SQLite数据库"),
	POSTGRE_SQL("postgresql", "%s LIKE CONCAT('%%',#{%s},'%%')", "Postgre数据库"),
	SQL_SERVER2005("sqlserver2005", "%s LIKE '%%'+#{%s}+'%%'", "SQLServer2005数据库"),
	SQL_SERVER("sqlserver", "%s LIKE '%%'+#{%s}+'%%'", "SQLServer数据库"),
	DM("dm", (String)null, "达梦数据库"),
	OTHER("other", (String)null, "其他数据库");

	private final String db;
	private final String like;
	private final String desc;

	DBType(String db, String like, String desc) {
		this.db = db;
		this.like = like;
		this.desc = desc;
	}

	public static DBType getDbType(String dbType) {
		DBType[] dts = values();
		DBType[] var2 = dts;
		int var3 = dts.length;

		for(int var4 = 0; var4 < var3; ++var4) {
			DBType dt = var2[var4];
			if (dt.getDb().equalsIgnoreCase(dbType)) {
				return dt;
			}
		}

		return OTHER;
	}

	public String getDb() {
		return this.db;
	}

	public String getLike() {
		return this.like;
	}

	public String getDesc() {
		return this.desc;
	}
}
