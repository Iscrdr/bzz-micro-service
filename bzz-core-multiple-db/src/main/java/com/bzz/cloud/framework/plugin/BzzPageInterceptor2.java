package com.bzz.cloud.framework.plugin;

import com.bzz.cloud.core.entity.BaseEntity;
import com.bzz.cloud.framework.dynamicdatasource.DataSourceContextHolder;
import com.bzz.common.Utils.Page;
import com.bzz.common.database.dialect.MySQLDialect;
import com.bzz.common.database.dialect.OracleDialect;
import com.bzz.common.database.dialect.PostgreSQLDialect;
import com.bzz.common.database.dialect.SQLServerDialect;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * @author Yang qianli
 * @version 1.0.0
 * @ProjectName bzz-cloud
 * @Description: mybaits 分页拦截器
 * @email 624003618@qq.com
 * @date 2018-12-31 22:29:27
 */
@Intercepts( {
    @Signature(method = "prepare", type = StatementHandler.class, args = {Connection.class,Integer.class}) })
@Deprecated
public class BzzPageInterceptor2 implements Interceptor, Serializable {


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //
        RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
        //RoutingStatementHandler实现的所有StatementHandler接口方法里面都是调用的delegate对应的方法。
        StatementHandler delegate = (StatementHandler)ReflectUtil.getFieldValue(handler, "delegate");

        BoundSql boundSql = delegate.getBoundSql();
        //拿到当前绑定Sql的参数对象，就是我们在调用对应的Mapper映射语句时所传入的参数对象
        Object obj = boundSql.getParameterObject();
        BaseEntity baseEntity = null;
        Page<?> page = null;
        if(obj == null){
            page = (Page<?>) boundSql.getAdditionalParameter("page");
        }else{
            if(obj instanceof BaseEntity<?,?>){
                baseEntity = (BaseEntity) obj;
                page = baseEntity.getPage();
            }else {
                page = (Page<?>) obj;
            }
        }
        if (page != null ) {
            //通过反射获取delegate父类BaseStatementHandler的mappedStatement属性
            MappedStatement mappedStatement = (MappedStatement)ReflectUtil.getFieldValue(delegate, "mappedStatement");
            //拦截到的prepare方法参数是一个Connection对象
            Connection connection = (Connection)invocation.getArgs()[0];
            //获取当前要执行的Sql语句，也就是我们直接在Mapper映射语句中写的Sql语句
            String sql = boundSql.getSql();
            //给当前的page参数对象设置总记录数
            this.setTotalRecord(page,mappedStatement, connection);

            //判断当前数据库类型
            List<String> dataSourceAndDialect = DataSourceContextHolder.getDataSourceAndDialect();
            String dbType = dataSourceAndDialect.get(1);
            String pageSql = "";
            if(dbType.equals("oracle")){
                //oracle:获取分页Sql语句
                OracleDialect oracleDialect = new OracleDialect();
                pageSql = oracleDialect.getPageSql(sql, page.getPageNum(), page.getPageSize());
            }else if(dbType.equals("sqlserver")){
                SQLServerDialect sqlServerDialect = new SQLServerDialect();
                pageSql = sqlServerDialect.getPageSql(sql, page.getPageNum(), page.getPageSize());
            }else if(dbType.equals("postgresql")){
                PostgreSQLDialect postgreSQLDialect = new PostgreSQLDialect();
                pageSql = postgreSQLDialect.getPageSql(sql, page.getPageNum(), page.getPageSize());
            }else {
                //Mysql:获取分页Sql语句
                MySQLDialect mySQLDialect = new MySQLDialect();
                pageSql = mySQLDialect.getPageSql(sql, page.getPageNum(), page.getPageSize(),page.getTotalCount());

            }
            //利用反射设置当前BoundSql对应的sql属性为我们建立好的分页Sql语句
            ReflectUtil.setFieldValue(boundSql, "sql", pageSql);
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }



    /**
     * 给当前的参数对象page设置总记录数
     *
     * @param page Mapper映射语句对应的参数对象
     * @param mappedStatement Mapper映射语句
     * @param connection 当前的数据库连接
     */
    private void setTotalRecord(Page<?> page,
                                MappedStatement mappedStatement, Connection connection) {
        //获取对应的BoundSql，这个BoundSql其实跟我们利用StatementHandler获取到的BoundSql是同一个对象。
        //delegate里面的boundSql也是通过mappedStatement.getBoundSql(paramObj)方法获取到的。
        BoundSql boundSql = mappedStatement.getBoundSql(page);
        //获取到我们自己写在Mapper映射语句中对应的Sql语句
        String sql = boundSql.getSql();
        //通过查询Sql语句获取到对应的计算总记录数的sql语句
        String countSql = this.getCountSql(sql);
        //通过BoundSql获取对应的参数映射
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        //利用Configuration、查询记录数的Sql语句countSql、参数映射关系parameterMappings和参数对象page建立查询记录数对应的BoundSql对象。
        BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(), countSql, parameterMappings, page);
        //通过mappedStatement、参数对象page和BoundSql对象countBoundSql建立一个用于设定参数的ParameterHandler对象
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, page, countBoundSql);
        //通过connection建立一个countSql对应的PreparedStatement对象。
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = connection.prepareStatement(countSql);
            //通过parameterHandler给PreparedStatement对象设置参数
            parameterHandler.setParameters(pstmt);
            //之后就是执行获取总记录数的Sql语句和获取结果了。
            rs = pstmt.executeQuery();
            if (rs.next()) {
                int totalCount = rs.getInt(1);
                //给当前的参数page对象设置总记录数
                page.setTotalCount(totalCount);
            }
        }  catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据原Sql语句获取对应的查询总记录数的Sql语句
     * @param sql
     * @return
     */
    private String getCountSql(String sql) {
        return "select count(*) from ( " + sql +" ) tt";
    }





    /**
     * 利用反射进行操作的一个工具类
     *
     */
    private static class ReflectUtil {
        /**
         * 利用反射获取指定对象的指定属性
         * @param obj 目标对象
         * @param fieldName 目标属性
         * @return 目标属性的值
         */
        public static Object getFieldValue(Object obj, String fieldName) {
            Object result = null;
            Field field = ReflectUtil.getField(obj, fieldName);
            if (field != null) {
                field.setAccessible(true);
                try {
                    result = field.get(obj);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return result;
        }

        /**
         * 利用反射获取指定对象里面的指定属性
         * @param obj 目标对象
         * @param fieldName 目标属性
         * @return 目标字段
         */
        private static Field getField(Object obj, String fieldName) {
            Field field = null;
            for (Class<?> clazz=obj.getClass(); clazz != Object.class; clazz=clazz.getSuperclass()) {
                try {
                    field = clazz.getDeclaredField(fieldName);
                    break;
                } catch (NoSuchFieldException e) {
                    //这里不用做处理，子类没有该字段可能对应的父类有，都没有就返回null。
                }
            }
            return field;
        }

        /**
         * 利用反射设置指定对象的指定属性为指定的值
         * @param obj 目标对象
         * @param fieldName 目标属性
         * @param fieldValue 目标值
         */
        public static void setFieldValue(Object obj, String fieldName,
                                         String fieldValue) {
            Field field = ReflectUtil.getField(obj, fieldName);
            if (field != null) {
                try {
                    field.setAccessible(true);
                    field.set(obj, fieldValue);
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}
