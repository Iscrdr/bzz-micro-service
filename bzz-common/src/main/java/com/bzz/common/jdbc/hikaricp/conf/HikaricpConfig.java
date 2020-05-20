package com.bzz.common.jdbc.hikaricp.conf;

public class HikaricpConfig {
	public final static String  poolName="BzzHikariCP";
	public final static int  minIdle = 5;
	public final static int  maxPoolSize = 500;
	public final static long  maxLifetime = 180000;
	public final static long  connectionTimeout = 30000;
	public final static long  idleTimeout = 30000;
	
}
