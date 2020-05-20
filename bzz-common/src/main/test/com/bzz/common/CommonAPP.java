package com.bzz.common;

import com.bzz.common.Utils.PinYin4jUtils;


import java.sql.*;


/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-05-20 02-49
 * @Modified by:
 * @Description:
 */
public class CommonAPP {
    // 数据库配置
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String url = "jdbc:mysql://192.168.132.150:3306/bzz?useUnicode=true&characterEncoding=utf-8&rewriteBatchedStatements=true&useSSL=false";
    private static final String username = "root";
    private static final String password = "root";

//	private static final String driver = "com.mysql.cj.jdbc.Driver";
//	private static final String url = "jdbc:mysql://127.0.0.1:3305/rcsjfx?useUnicode=true&characterEncoding=utf-8&rewriteBatchedStatements=true&useSSL=false&serverTimezone=GMT%2B8";
//	private static final String username = "root";
//	private static final String password = "root";

	/*private static final String url = "jdbc:mysql://118.89.237.130:3306/rcsjfx?useUnicode=true&characterEncoding=utf-8&rewriteBatchedStatements=true&useSSL=false";
	private static final String username = "root";
	private static final String password = "kaqkwgisshwqhs9wh";*/

    // 定义一个用于放置数据库连接的局部线程变量（使每个线程都拥有自己的连接）
    private static ThreadLocal<Connection> connContainer = new ThreadLocal<Connection>();

    // 获取连接
    public static Connection getConnection() {
        Connection conn = connContainer.get();
        try {
            if (conn == null) {
                Class.forName(driver);
                conn = DriverManager.getConnection(url, username, password);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connContainer.set(conn);
        }
        return conn;
    }

    // 关闭连接
    public static void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connContainer.remove();
        }
    }
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstm =null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            String sql = "select  * from sheet2";
            pstm = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            conn.setAutoCommit(false);
            long startTime2 = System.currentTimeMillis();//所有事务的开始时间
            rs = pstm.executeQuery();
            while(rs.next()){
                String short_name = rs.getString("short_name");
                String jx = PinYin4jUtils.getJX(short_name, true);
                rs.updateString("jianpin",jx);

                /*String pinyinUpper = PinYin4jUtils.getHeaderUpperQuanpinString(short_name, true);
                rs.updateString("pinyin",pinyinUpper);*/

                rs.updateRow();
            }
            rs.close();
            conn.commit();
            long endTime2 = System.currentTimeMillis();
            System.out.println("更新完毕,共耗时："+(endTime2-startTime2));
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            closeConnection(conn);
        }
    }
}
