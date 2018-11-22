package com.bzz.common.database;

import com.bzz.common.database.dialect.DB2Dialect;
import com.bzz.common.database.dialect.MySQLDialect;
import com.bzz.common.database.dialect.OracleDialect;
import com.bzz.common.database.dialect.SQLServerDialect;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @program: bzz-cloud
 * @description:
 * @author: Yang qianli
 * @create: 2018-10-15 20:41
 * @version: 1.0.0
 */
public class DBTypetUtils {
	private static final Log logger = LogFactory.getLog(DBTypetUtils.class);
	
	/**
	 * <p>
	 * 根据连接地址判断数据库类型
	 * </p>
	 *
	 * @param jdbcUrl 连接地址
	 * @return
	 */
	public static DBType getDbType(String jdbcUrl) {
		if (jdbcUrl.startsWith("jdbc:mysql:") || jdbcUrl.startsWith("jdbc:cobar:") || jdbcUrl.startsWith("jdbc:log4jdbc:mysql:")) {
			return DBType.MYSQL;
		} else if (jdbcUrl.startsWith("jdbc:mariadb:")) {
			return DBType.MARIADB;
		} else if (jdbcUrl.startsWith("jdbc:oracle:") || jdbcUrl.startsWith("jdbc:log4jdbc:oracle:")) {
			return DBType.ORACLE;
		} else if (jdbcUrl.startsWith("jdbc:sqlserver:") || jdbcUrl.startsWith("jdbc:microsoft:")) {
			return DBType.SQL_SERVER2005;
		} else if (jdbcUrl.startsWith("jdbc:sqlserver2012:")) {
			return DBType.SQL_SERVER;
		} else if (jdbcUrl.startsWith("jdbc:postgresql:") || jdbcUrl.startsWith("jdbc:log4jdbc:postgresql:")) {
			return DBType.POSTGRE_SQL;
		} else if (jdbcUrl.startsWith("jdbc:hsqldb:") || jdbcUrl.startsWith("jdbc:log4jdbc:hsqldb:")) {
			return DBType.HSQL;
		} else if (jdbcUrl.startsWith("jdbc:db2:")) {
			return DBType.DB2;
		} else if (jdbcUrl.startsWith("jdbc:sqlite:")) {
			return DBType.SQLITE;
		} else if (jdbcUrl.startsWith("jdbc:h2:") || jdbcUrl.startsWith("jdbc:log4jdbc:h2:")) {
			return DBType.H2;
		} else if (jdbcUrl.startsWith("jdbc:dm:") || jdbcUrl.startsWith("jdbc:log4jdbc:dm:")) {
			return DBType.DM;
		} else {
			logger.warn("The jdbcUrl is " + jdbcUrl + ",  Cannot Read Database type or The Database's Not Supported!");
			return DBType.OTHER;
		}
	}
	
	/**
	 * <p>
	 * 根据数据库类型选择不同分页方言
	 * </p>
	 *
	 * @param dbType 数据库类型
	 * @return
	 * @throws Exception
	 */
	public static DynamicDialect getDialectByDbType(DBType dbType,boolean isSupport,int idType) throws Exception {
		if (dbType == DBType.MYSQL) {
			return new MySQLDialect(isSupport,idType);
		}
		/*if (dbType == DBType.MARIADB) {
			return new MariaDBDialect();
		}*/
		if (dbType == DBType.ORACLE) {
			return new OracleDialect();
		}
		/*if (dbType == DBType.DB2) {
			return new DB2Dialect();
		}*/
		/*if (dbType == DBType.H2) {
			return new H2Dialect();
		}
		if (dbType == DBType.SQL_SERVER) {
			return new SQLServerDialect();
		}
		if (dbType == DBType.SQL_SERVER2005) {
			return new SQLServer2005Dialect();
		}
		if (dbType == DBType.POSTGRE_SQL) {
			return new PostgreDialect();
		}
		if (dbType == DBType.HSQL) {
			return new HSQLDialect();
		}
		if (dbType == DbType.SQLITE) {
			return new SQLiteDialect();
		}
		if (dbType == DBType.DM) {
			return new DmDialect();
		}*/
		throw new Exception("The Database's Not Supported! DBType:" + dbType);
	}
}
