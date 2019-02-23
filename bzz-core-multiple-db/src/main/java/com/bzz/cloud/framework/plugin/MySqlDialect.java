/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2017 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.bzz.cloud.framework.plugin;

/*import com.github.pagehelper.Page;
import com.github.pagehelper.dialect.AbstractHelperDialect;
import org.apache.ibatis.cache.CacheKey;*/

/**
 * @author liuzh
 */
public class MySqlDialect /*extends AbstractHelperDialect */{
    /*@Override
    public String getPageSql(String sql, Page page, CacheKey pageKey) {
        StringBuilder sqlBuilder = new StringBuilder(sql.length() + 14);
        sql= sql.toLowerCase();//全部转换成小写形式
        if (page.getStartRow() == 0) {
            sqlBuilder.append(sql);
            sqlBuilder.append(" LIMIT ");
            sqlBuilder.append(page.getPageSize());
        }
        else if(page.getStartRow()>10000&&this.inSingletonTable(sql)){//判断是否是大页码并且单表查询
            String[] tables = this.getTableName(sql);
            String sql1 =sql.split(tables[0])[0];
            sqlBuilder.append(sql1);
            sqlBuilder.append(" (Select id as id2,(@rowNum:=@rowNum+1) as rowNo From ");
            sqlBuilder.append(tables[0]);
            sqlBuilder.append(",(Select (@rowNum :=0) ) b) r ,");
            sqlBuilder.append(tables[0]);
            sqlBuilder.append(" ");
            sqlBuilder.append(tables[1]!=null?tables[1]:" ");
            sqlBuilder.append(" where r.id2= ");
            sqlBuilder.append(tables[1]!=null?tables[1]:tables[0]);
            sqlBuilder.append(".id ");
            sqlBuilder.append(" and r.rowNo> ");
            sqlBuilder.append(page.getStartRow());

            if (sql.contains("where")) {//拼接原来SQL语句中的where语句后面的语句
                sqlBuilder.append(" and ");
                sqlBuilder.append(sql.split("where")[1]);
            }else {
                //拼接原有的SQL表名后面的一段后面
                if (tables[1]!=null) {//表有别名
                    String[] sql2 =sql.split(tables[1]);
                    sqlBuilder.append(" ");
                    sqlBuilder.append(sql2.length>1?sql2[1]:" ");
                }else {
                    String[] sql2 =sql.split(tables[0]);
                    sqlBuilder.append(" ");
                    sqlBuilder.append(sql2.length>1?sql2[1]:" ");
                }
            }
            sqlBuilder.append(" LIMIT ");
            sqlBuilder.append(page.getPageSize());
        }else{
            sqlBuilder.append(sql);
            sqlBuilder.append(" LIMIT ");
            sqlBuilder.append(page.getStartRow());
            sqlBuilder.append(",");
            sqlBuilder.append(page.getPageSize());
            pageKey.update(page.getStartRow());
        }
        pageKey.update(page.getPageSize());
        return sqlBuilder.toString();
    }

    private boolean inSingletonTable(String sql) {
        if (sql.contains("join")||sql.contains("JOIN")) {
            return false;
        }

        if (sql.contains("where")) {
            if (sql.contains("from")) {
                String tables= sql.split("from")[1].split("where")[0];
                if (tables.contains(",")) {
                    return false;
                }
            }
        }
        return true;
    }
    private String[] getTableName(String sql) {
        String[] tables = new String[2];
        if (sql.contains("where")) {
            String tablenames = sql.split("from")[1].split("where")[0];
            tablenames = this.removekg(tablenames);//删除表名前后的空格
            if (tablenames.contains(" ")) {
                tables=tablenames.split(" ");
                return tables;
            }else {
                tables[0]=tablenames;
                return tables;
            }
        } else if (sql.contains("group")&&!sql.contains("order")) {
            String tablenames = sql.split("from")[1].split("group")[0];
            tablenames = this.removekg(tablenames);//删除表名前后的空格
            if (tablenames.contains(" ")) {
                tables=tablenames.split(" ");
                return tables;
            }else {
                tables[0]=tablenames;
                return tables;
            }
        } else if (sql.contains("order")&&!sql.contains("group")) {
            String tablenames = sql.split("from")[1].split("order")[0];
            tablenames = this.removekg(tablenames);//删除表名前后的空格
            if (tablenames.contains(" ")) {
                tables=tablenames.split(" ");
                return tables;
            }else {
                tables[0]=tablenames;
                return tables;
            }
        } else if (sql.contains("order")&&sql.contains("group")) {
            int orderIndex =sql.indexOf("order");
            int groupIndex =sql.indexOf("group");
            if (orderIndex<groupIndex) {
                String tablenames = sql.split("from")[1].split("order")[0];
                tablenames = this.removekg(tablenames);//删除表名前后的空格
                if (tablenames.contains(" ")) {
                    tables=tablenames.split(" ");
                    return tables;
                }else {
                    tables[0]=tablenames;
                    return tables;
                }
            }else {
                String tablenames = sql.split("from")[1].split("group")[0];
                tablenames = this.removekg(tablenames);//删除表名前后的空格
                if (tablenames.contains(" ")) {
                    tables=tablenames.split(" ");
                    return tables;
                }else {
                    tables[0]=tablenames;
                    return tables;
                }
            }
        }else if (!sql.contains("where")&&!sql.contains("order")&&!sql.contains("group")) {
            String tablenames = sql.split("from")[1];
            tablenames = this.removekg(tablenames);//删除表名前后的空格
            if (tablenames.contains(" ")) {
                tables=tablenames.split(" ");
                return tables;
            }else {
                tables[0]=tablenames;
                return tables;
            }
        }
        return tables;
    }
    //删除字符串两头的空格
    private String removekg(String textContent) {
        textContent = textContent.trim();
        while (textContent.startsWith(" ")) {//这里判断是不是全角空格
            textContent = textContent.substring(1, textContent.length()).trim();
        }
        while (textContent.endsWith("　")) {
            textContent = textContent.substring(0, textContent.length() - 1).trim();
        }
        return textContent;
    }*/

}
