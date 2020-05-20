/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.bzz.common.database.dialect;


import com.bzz.common.database.DynamicDialect;

/**
 * @description: Mysql Dialect分页实现
 * @author: Yang qianli
 * @createTime: 2018-10-15 17:53:18
 * @updateTime: 2018-10-15 17:53:18
 * @version: 1.0.0
 */
public class MySQLDialect implements DynamicDialect {

    //是否支持分页，true支持,false不支持
    private boolean isSupportsPage;

    //0为通用分页，1：id是long,int等有序类型分页，2，id为uuid等类型或者sql结果不包含id 的分页
    private int idType;
    
    public MySQLDialect(boolean isSupportsPage, int idType) {
        this.isSupportsPage = isSupportsPage;
        this.idType = idType;
    }
    
    public MySQLDialect() {
    }

    @Override
    public String getPageSql(String sql, int pageNo, int pageSize){
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append(sql);
        sqlBuffer.append(" LIMIT ");
        sqlBuffer.append(pageNo);
        sqlBuffer.append(",");
        sqlBuffer.append(pageSize);
        return sqlBuffer.toString();
    }

    @Override
    public String getCountSqlString(String sql) {
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("SELECT COUNT(*) FROM ( ");
        sqlBuffer.append(sql);
        sqlBuffer.append(" ) AS aa ");
        return sqlBuffer.toString();
    }

    /**
     * @param sql    SQL语句
     * @param pageNo 开始条数
     * @param pageSize  每页显示多少纪录条数
     *  @param totalCount  总条数
     * @return
     */
    public String getPageSql(String sql, int pageNo, int pageSize,int totalCount) {

        sql= sql.toLowerCase();
        StringBuffer sqlBuffer = new StringBuffer();

        if (pageNo == 0) {
            sqlBuffer.append(sql);
            sqlBuffer.append(" LIMIT ");
            sqlBuffer.append(pageSize);
        } else if(totalCount>10000 &&this.inSingletonTable(sql)){
            String[] tables = this.getTableName(sql);
            String sql1 =sql.split(tables[0])[0];
            sqlBuffer.append(sql1);
            sqlBuffer.append(" (Select id as id2,(@rowNum:=@rowNum+1) as rowNo From ");
            sqlBuffer.append(tables[0]);
            sqlBuffer.append(",(Select (@rowNum :=0) ) b) r ,");
            sqlBuffer.append(tables[0]);
            sqlBuffer.append(" ");
            sqlBuffer.append(tables[1]!=null?tables[1]:" ");
            sqlBuffer.append(" where r.id2= ");
            sqlBuffer.append(tables[1]!=null?tables[1]:tables[0]);
            sqlBuffer.append(".id ");
            sqlBuffer.append(" and r.rowNo> ");
            sqlBuffer.append(pageNo);

            if (sql.contains("where")) {//拼接原来SQL语句中的where语句后面的语句
                sqlBuffer.append(" and ");
                sqlBuffer.append(sql.split("where")[1]);
            }else {
                //拼接原有的SQL表名后面的一段后面
                if (tables[1]!=null) {//表有别名
                    String[] sql2 =sql.split(tables[1]);
                    sqlBuffer.append(" ");
                    sqlBuffer.append(sql2.length>1?sql2[1]:" ");
                }else {
                    String[] sql2 =sql.split(tables[0]);
                    sqlBuffer.append(" ");
                    sqlBuffer.append(sql2.length>1?sql2[1]:" ");
                }
            }
            sqlBuffer.append(" LIMIT ");
            sqlBuffer.append(pageSize);
        }else{
            sqlBuffer.append(sql);
            sqlBuffer.append(" LIMIT ");
            sqlBuffer.append(pageNo);
            sqlBuffer.append(",");
            sqlBuffer.append(pageSize);
        }

        return sqlBuffer.toString();
    }


    /**
     * 删除两端的空格
     * @param textContent
     * @return
     */
    private String removekg(String textContent) {
        textContent = textContent.trim();
        while (textContent.startsWith(" ")) {//这里判断是不是全角空格
            textContent = textContent.substring(1, textContent.length()).trim();
        }
        while (textContent.endsWith("　")) {
            textContent = textContent.substring(0, textContent.length() - 1).trim();
        }
        return textContent;
    }

    /**
     * 是否是简单sql
     * @param sql
     * @return
     */
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
    /**
     * 解析sql中的表名
     * @param sql
     * @return
     */
    private String[] getTableName(String sql) {
        String[] tables = new String[2];
        if (sql.contains("where")) {
            String tablenames = sql.split("from")[1].split("where")[0];
            //删除表名前后的空格
            tablenames = this.removekg(tablenames);
            if (tablenames.contains(" ")) {
                tables=tablenames.split(" ");
                return tables;
            }else {
                tables[0]=tablenames;
                return tables;
            }
        } else if (sql.contains("group")&&!sql.contains("order")) {
            String tablenames = sql.split("from")[1].split("group")[0];
            //删除表名前后的空格
            tablenames = this.removekg(tablenames);
            if (tablenames.contains(" ")) {
                tables=tablenames.split(" ");
                return tables;
            }else {
                tables[0]=tablenames;
                return tables;
            }
        } else if (sql.contains("order")&&!sql.contains("group")) {
            String tablenames = sql.split("from")[1].split("order")[0];
            //删除表名前后的空格
            tablenames = this.removekg(tablenames);
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
                //删除表名前后的空格
                tablenames = this.removekg(tablenames);
                if (tablenames.contains(" ")) {
                    tables=tablenames.split(" ");
                    return tables;
                }else {
                    tables[0]=tablenames;
                    return tables;
                }
            }else {
                String tablenames = sql.split("from")[1].split("group")[0];
                //删除表名前后的空格
                tablenames = this.removekg(tablenames);
                if (tablenames.contains(" ")) {
                    tables=tablenames.split(" ");
                    return tables;
                }else {
                    tables[0]=tablenames;
                    return tables;
                }
            }
        }else if (!sql.contains("where")&&!sql.contains("order")&&!sql.contains("group")) {
            //删除表名前后的空格
            String tablenames = sql.split("from")[1];
            tablenames = this.removekg(tablenames);
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
}
