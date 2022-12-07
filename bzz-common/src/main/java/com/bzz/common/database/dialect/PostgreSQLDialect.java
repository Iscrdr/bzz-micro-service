/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.bzz.common.database.dialect;


import com.bzz.common.database.DynamicDialect;

/**
 * Postgre Sql的方言实现.
 * @author poplar.yfyang
 * @version 1.0 2010-10-10 下午12:31
 * @since JDK 1.5
 */
public class PostgreSQLDialect implements DynamicDialect {


   /**
     * 将sql变成分页sql语句,提供将offset及limit使用占位符号(placeholder)替换.
     * <pre>
     * 如mysql
     * dialect.getLimitString("select * from user", 12, ":offset",0,":limit") 将返回
     * select * from user limit :offset,:limit
     * </pre>
     *
     * @param sql               实际SQL语句
     * @param offset            分页开始纪录条数
     * @param offsetPlaceholder 分页开始纪录条数－占位符号
     * @param limitPlaceholder  分页纪录条数占位符号
     * @return 包含占位符的分页sql
     */
    public String getLimitString(final String sql, final int offset,
                                 final String offsetPlaceholder,
                                 final String limitPlaceholder) {
        StringBuilder pageSql = new StringBuilder().append(sql);
        pageSql = offset <= 0
                ? pageSql.append(" limit ")
                .append(limitPlaceholder)
                : pageSql.append(" limit ")
                .append(limitPlaceholder)
                .append(" offset ")
                .append(offsetPlaceholder);
        return pageSql.toString();
    }


    /**
     * 分页sql.
     * @param sql    SQL语句
     * @param pageNo 开始条数
     * @param pageSize  每页显示多少纪录条数
     * @return
     */
    @Override
    public String getPageSql(final String sql,
                             final int pageNo,
                             final int pageSize) {
        return getLimitString(sql, pageNo, Integer.toString(pageNo),
                Integer.toString(pageSize));
    }

    /**
     * 共有多少行.
     * @param sql    SQL语句
     * @return
     */
    @Override
    public String getCountSqlString(final String sql) {
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("SELECT COUNT(*) FROM ( ");
        sqlBuffer.append(sql);
        sqlBuffer.append(" ) AS aa");
        return sqlBuffer.toString();
    }
}
