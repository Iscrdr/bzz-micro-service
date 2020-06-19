package com.bzz.cloud.framework.Interceptor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;


import com.bzz.cloud.framework.dynamicdatasource.DataSourceContextHolder;
import com.bzz.common.utils.Page;
import com.bzz.common.database.DynamicDialect;
import com.bzz.common.database.dialect.MySQLDialect;
import com.bzz.common.database.dialect.OracleDialect;
import com.bzz.common.database.dialect.PostgreSQLDialect;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * @program: bzz-cloud
 * @description: mybatis动态物理分页，根据不同的数据源实现不同的物理分页
 * @author: 624003618@qq.com
 * @create: 2019-08-23 16:03
 */
@Component
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class,Integer.class}),
        @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})})
public class PageInterceptor implements Interceptor {


    private static Logger logger = LoggerFactory.getLogger(PageInterceptor.class);

    private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
    private static final ReflectorFactory DEFAULT_REFLECTOR_FACTORY= new DefaultReflectorFactory();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Map<String,Object> dataSourceAndDialect = DataSourceContextHolder.getDataSourceAndDialect();
        /*
         * sql执行前拦截，生成分页sql
         */
        try {
            if(invocation.getTarget() instanceof StatementHandler){
                StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
                ParameterHandler parameterHandler = statementHandler.getParameterHandler();
                Object parameterObject = parameterHandler.getParameterObject();
                Page page = null;
                if(parameterObject instanceof MapperMethod.ParamMap){
                    MapperMethod.ParamMap paramMapObject = (MapperMethod.ParamMap)parameterObject ;
                    if(paramMapObject != null){
                        for(Object key : paramMapObject.keySet()){
                            if(paramMapObject.get(key) instanceof  Page){
                                page = (Page) paramMapObject.get(key);
                                break;
                            }
                        }
                    }
                }
                if (page != null) {
                    MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY,DEFAULT_REFLECTOR_FACTORY);
                    Configuration configuration = (Configuration) metaStatementHandler.getValue("delegate.configuration");
                    String originalSql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");

                    String dataSourceName = (String)dataSourceAndDialect.get("dataSource");
                    String dialect = (String)dataSourceAndDialect.get("dialect");

                    if(StringUtils.isBlank(dialect)){
                        dialect = "mysql";
                    }
                    dialect = dialect.toLowerCase();
                    DynamicDialect dynamicDialect = null;
                    String pageSql ;
                    switch(dialect){
                        case "mysql":
                            dynamicDialect = new MySQLDialect();
                            break;
                        case "oracle":
                            dynamicDialect = new OracleDialect();
                            break;
                        case "postgresql":
                            dynamicDialect = new PostgreSQLDialect();
                            break;
                        default:
                            break;
                    }
                    //将originalSql放入本地线程中，以便在结果中计算count，封装为对象
                    dataSourceAndDialect.put("originalSql",originalSql);

                    pageSql = dynamicDialect.getPageSql(originalSql, page.getCurrent()-1, page.getPageSize());
                    metaStatementHandler.setValue("delegate.boundSql.sql", pageSql);
                    if (logger.isDebugEnabled()) {
                        BoundSql boundSql = statementHandler.getBoundSql();
                        logger.debug("Generate SQL : " + boundSql.getSql());
                    }
                    return invocation.proceed();
                }

            }else if(invocation.getTarget() instanceof DefaultResultSetHandler){
                DefaultResultSetHandler resultSetHandler = (DefaultResultSetHandler) invocation.getTarget();
                MetaObject metaResultSetHandler = MetaObject.forObject(resultSetHandler, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY,DEFAULT_REFLECTOR_FACTORY);
                ParameterHandler parameterHandler = (ParameterHandler) metaResultSetHandler.getValue("parameterHandler");
                Object parameterObject = parameterHandler.getParameterObject();
                Page page = null;
                if(parameterObject instanceof MapperMethod.ParamMap){
                    MapperMethod.ParamMap paramMapObject = (MapperMethod.ParamMap)parameterObject ;
                    if(paramMapObject != null){
                        for(Object key : paramMapObject.keySet()){
                            if(paramMapObject.get(key) instanceof  Page){
                                page = (Page) paramMapObject.get(key);
                                break;
                            }
                        }
                    }
                }
                if (page != null) {
                    BoundSql boundSql = (BoundSql) metaResultSetHandler.getValue("parameterHandler.boundSql");
                    Configuration configuration = (Configuration) metaResultSetHandler.getValue("configuration");
                    Object originalSql1 = dataSourceAndDialect.get("originalSql");

                    String dataSourceName = (String)dataSourceAndDialect.get("dataSource");
                    String dialect = (String)dataSourceAndDialect.get("dialect");

                    if(StringUtils.isBlank(dialect)){
                        dialect = "mysql";
                    }
                    dialect = dialect.toLowerCase();
                    DynamicDialect dynamicDialect = null;
                    String pageSql ;
                    switch(dialect){
                        case "mysql":
                            dynamicDialect = new MySQLDialect();
                            break;
                        case "oracle":
                            dynamicDialect = new OracleDialect();
                            break;
                        case "postgresql":
                            dynamicDialect = new PostgreSQLDialect();
                            break;
                        default:
                            break;
                    }
                    String countSqlString = dynamicDialect.getCountSqlString(originalSql1.toString());
                    // 修改sql，用于返回总记录数
                    Long totalRecord = getTotalRecord(configuration, countSqlString, parameterHandler);
                    page.setTotal(totalRecord.intValue());

                    //metaResultSetHandler.setValue("mappedStatement.resultMaps[0].type.name", Page.class.getName());
                    Object result = invocation.proceed();
                    List list = new ArrayList();
                    list.addAll((List)result);
                    page.setData(list);
                    page.setSuccess(true);
                    page.init();

                    // 设置返回值
                    List<Page> pageList = new ArrayList<>();
                    pageList.add(page);
                    return pageList;
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return   invocation.proceed();

    }

    @Override
    public Object plugin(Object target) {

        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {


    }

    private Long getTotalRecord(Configuration configuration, String sql, ParameterHandler parameterHandler){

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        SqlSession session = sqlSessionFactory.openSession();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Long result = 0L;
        try {
            preparedStatement = session.getConnection().prepareStatement(sql);
            parameterHandler.setParameters(preparedStatement);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                result = resultSet.getLong(1);
            }
            return  result;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            session.close();
        }
        return 0L;
    }


}
