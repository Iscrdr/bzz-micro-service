package com.bzz.common.database.dialect;
import com.bzz.common.database.DynamicDialect;
/**
 * DB2的分页数据库方言实现.
 *
 * @author poplar.yfyang
 * @version 1.0 2010-10-10 下午12:31
 * @since JDK 1.5
 */
public class DB2Dialect implements DynamicDialect {
    /**
     * 分页限制.
     * @return true：分页sql，false：非分页sql.
     */
    public boolean isSupportsLimit() {
        return false;
    }

    /**
     * 分页sql.待实现.
     * @param sql    SQL语句
     * @param pageNo 开始条数
     * @param pageSize  每页显示多少纪录条数
     * @return null
     */
    @Override
    public String getPageSql(final String sql,
                             final int pageNo,
                             final int pageSize) {
        return null;
    }

    /**
     * sql语句封装.
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
