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
    
    private boolean isSupportsPage;//是否支持分页，true支持,false不支持
    
    private int idType;//0为通用分页，1：id是long,int等有序类型分页，2，id为uuid等类型或者sql结果不包含id 的分页
    
    public MySQLDialect(boolean isSupportsPage, int idType) {
        this.isSupportsPage = isSupportsPage;
        this.idType = idType;
    }
    
    public MySQLDialect() {
    }
    
    /**
     * @param sql    SQL语句
     * @param pageNo 开始条数
     * @param pageSize  每页显示多少纪录条数
     * @return
     */
    public String getPageSql(String sql, int pageNo, int pageSize) {
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append(sql);
        sqlBuffer.append(" LIMIT "+pageNo+","+pageSize);
        return sqlBuffer.toString();
    }
    
   
}
