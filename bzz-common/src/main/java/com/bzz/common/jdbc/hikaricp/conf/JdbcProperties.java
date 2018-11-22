package com.bzz.common.jdbc.hikaricp.conf;

public class JdbcProperties {
	
	/**
	 * mysql
	 */
	public final static String mysqlDriverClassName="com.mysql.jdbc.Driver";
	//database address
	public final static String  mysqlJdbcUrl="jdbc:mysql://127.0.0.1:3306/rcsjfx?useUnicode=true&characterEncoding=utf-8&rewriteBatchedStatements=true&useSSL=false";
	//useName 用户名
	public final static String mysqlUsername="root";
	//password
	public final static String  mysqlPassword="root";
	
	/**
	 * oracle
	 */
	public final static String oracleDriverClassName="";
	//database address
	public final static String  oracleJdbcUrl="";
	//useName 用户名
	public final static String oracleUsername="";
	//password
	public final static String  oraclePassword="";
	
	/**
	 * 默认属性
	 */
	public final static String  poolName="BzzHikariCP";
	public final static int  minIdle = 5;
	public final static int  maxPoolSize = 500;
	public final static long  maxLifetime = 180000;
	public final static long  connectionTimeout = 30000;
	public final static long  idleTimeout = 30000;
	public final static String  testQuery = "SELECT 1";
	
	
	
	public final static String  initializationFailTimeout="";
	public final static String  validationTimeout="";
	public final static String  loginTimeout="";
	
	
	
	
	
}
