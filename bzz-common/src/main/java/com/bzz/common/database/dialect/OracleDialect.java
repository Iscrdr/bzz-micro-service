
package com.bzz.common.database.dialect;
import com.bzz.common.database.DynamicDialect;

/**
 * Oracle的方言实现.
 * @author poplar.yfyang
 * @version 1.0 2010-10-10 下午12:31
 * @since JDK 1.5
 */
public class OracleDialect implements DynamicDialect {

    /**
     * SQUELCH 多余的sql字符.
     */
    private static final int SQUELCH = 11;
    /**
     * SQUELCHIER 拼接后的sql.
     */
    private static final int SQUELCHIER = 100;
    /**
     * 将sql变成分页sql语句,提供将offset及limit使用占位符号(placeholder)替换.
     * @param sql               实际SQL语句
     * @param offset            分页开始纪录条数
     * @param offsetPlaceholder 分页开始纪录条数－占位符号
     * @param limitPlaceholder  分页纪录条数占位符号
     * @return 包含占位符的分页sql
     */
    public String getLimitString(final String sql,
                                 final int offset,
                                 final String offsetPlaceholder,
                                 final String limitPlaceholder) {
        String sqlOracle = sql.trim();
        boolean isForUpdate = false;
        if (sqlOracle.toLowerCase().endsWith(" for update")) {
            sqlOracle = sqlOracle.substring(0, sqlOracle.length() - SQUELCH);
            isForUpdate = true;
        }
        StringBuilder pagingSelect = new StringBuilder(
                sql.length() + SQUELCHIER);
        if (offset > 0) {
            pagingSelect.append(
                    "select * from ( select row_.*, rownum rownum_ from ( ");
        } else {
			pagingSelect.append("select * from ( ");
		}
		pagingSelect.append(sql);

		if (offset > 0) {
            String endString = offsetPlaceholder + "+" + limitPlaceholder;
            pagingSelect.append(" ) row_ where rownum <= " + endString
                            + ") where rownum_ > "
            ).append(offsetPlaceholder);
		} else {
			pagingSelect.append(" ) where rownum <= "
                    + limitPlaceholder);
        }

        if (isForUpdate) {
            pagingSelect.append(" for update");
        }

        return pagingSelect.toString();
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
        return getLimitString(sql,
                pageNo,
                Integer.toString(pageNo),
                Integer.toString(pageSize));
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
        sqlBuffer.append(" )");
        return sqlBuffer.toString();
    }
}
