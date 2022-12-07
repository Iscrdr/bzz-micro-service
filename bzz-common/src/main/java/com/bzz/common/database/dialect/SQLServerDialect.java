
package com.bzz.common.database.dialect;

import com.bzz.common.database.DynamicDialect;

/**
 * MSSQLServer 数据库实现分页方言.
 *
 * @author poplar.yfyang
 * @version 1.0 2010-10-10 下午12:31
 * @since JDK 1.5
 */
public class SQLServerDialect implements DynamicDialect {
    /**
     * select distinct 索引.
     */
    private static final int SELECTDISTINCT = 15;
    /**
     * select索引.
     */
    private static final int SELECT = 15;
    /**
     * 获取sql中的select索引.
     * @param sql
     * @return
     */
    static int getAfterSelectInsertPoint(final String sql) {
        int selectIndex = sql.toLowerCase().indexOf("select");
        final int selectDistinctIndex =
                sql.toLowerCase().indexOf("select distinct");
        return selectIndex
                + (selectDistinctIndex == selectIndex ? SELECTDISTINCT : SELECT);
    }

    /**
     * 分页sql.
     * @param sql
     * @param offset
     * @param limit
     * @return sql
     */
    public String getLimitString(final String sql,
                                 final int offset, final int limit) {
        return getLimit(sql, offset, limit);
    }


   /**
     * 将sql变成分页sql语句,提供将offset及limit使用占位符号(placeholder)替换.
     * <pre>
     * 如mysql
     * dialect.getLimitString("select * from user", 12, ":offset",0,":limit") 将返回
     * select * from user limit :offset,:limit
     * </pre>
     *
     * @param sql    实际SQL语句
     * @param offset 分页开始纪录条数
     * @param limit  分页每页显示纪录条数
     * @return 包含占位符的分页sql
     */

    public String getLimit(final String sql,
                           final int offset, final int limit) {
        if (offset > 0) {
            throw new UnsupportedOperationException("sql server has no offset");
        }
        return new StringBuffer(sql.length() + 8)
                .append(sql)
                .insert(getAfterSelectInsertPoint(sql), " top " + limit)
                .toString();
    }


    /**
     * 获取分页sql.
     * @param sql    SQL语句
     * @param pageNo 开始条数
     * @param pageSize  每页显示多少纪录条数
     * @return
     */
    @Override
    public String getPageSql(final String sql,
                             final int pageNo, final int pageSize) {
        return getLimitString(sql, pageNo, pageSize);
    }

    /**
     * 封装行数统计.
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
