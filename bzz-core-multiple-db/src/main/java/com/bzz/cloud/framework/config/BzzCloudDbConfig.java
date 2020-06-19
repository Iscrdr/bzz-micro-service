package com.bzz.cloud.framework.config;

import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import com.bzz.cloud.framework.dynamicdatasource.DynamicDataSource;
import com.mysql.cj.jdbc.MysqlXADataSource;
import oracle.jdbc.xa.client.OracleXADataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @PACKAGE_NAME: com.bzz.cloud.framework.DBSource.dbxa
 * @CLASS_NAME: MyBatisDbConfig
 * @Description:
 * @Author : yang qianli
 * @Date: 2017-11-26 21:49
 * @Modified by:
 * @Date:
 */
@Configuration
public class BzzCloudDbConfig{
    
    private Properties build(Environment env, String prefix) {
        
        Properties prop = new Properties();
        prop.put("druid.url", env.getProperty(prefix + "url"));
        prop.put("druid.username", env.getProperty(prefix + "username"));
        prop.put("druid.password", env.getProperty(prefix + "password"));
        prop.put("druid.driverClassName", env.getProperty(prefix + "driverClassName", ""));
        prop.put("druid.initialSize", env.getProperty(prefix + "initialSize", Integer.class));
        prop.put("druid.maxActive", env.getProperty(prefix + "maxActive", Integer.class));
        prop.put("druid.minIdle", env.getProperty(prefix + "minIdle", Integer.class));
        prop.put("druid.maxWait", env.getProperty(prefix + "maxWait", Integer.class));
        prop.put("druid.poolPreparedStatements", env.getProperty(prefix + "poolPreparedStatements", Boolean.class));


        prop.put("druid.maxPoolPreparedStatementPerConnectionSize",
                env.getProperty(prefix + "maxPoolPreparedStatementPerConnectionSize", Integer.class));
        
        prop.put("druid.maxPoolPreparedStatementPerConnectionSize",
                env.getProperty(prefix + "maxPoolPreparedStatementPerConnectionSize", Integer.class));
        prop.put("druid.validationQuery", env.getProperty(prefix + "validationQuery"));
        prop.put("druid.validationQueryTimeout", env.getProperty(prefix + "validationQueryTimeout", Integer.class));
        prop.put("druid.testOnBorrow", env.getProperty(prefix + "testOnBorrow", Boolean.class));
        prop.put("druid.testOnReturn", env.getProperty(prefix + "testOnReturn", Boolean.class));
        prop.put("druid.testWhileIdle", env.getProperty(prefix + "testWhileIdle", Boolean.class));
        prop.put("druid.timeBetweenEvictionRunsMillis",
                env.getProperty(prefix + "timeBetweenEvictionRunsMillis", Integer.class));
        prop.put("druid.minEvictableIdleTimeMillis", env.getProperty(prefix + "minEvictableIdleTimeMillis", Integer.class));
        prop.put("druid.filters", env.getProperty(prefix + "filters"));
       
        return prop;
    }
    @Autowired
    @Primary
    @Bean(name = "dataSourceA")
    public AtomikosDataSourceBean  dataSourceA(Environment env) throws SQLException {

        Properties prop = build(env, "spring.datasource.dataSourceA.druid.");
        MysqlXADataSource mysqlXADataSource = new MysqlXADataSource();
        mysqlXADataSource.setUrl(prop.getProperty("druid.url"));
        mysqlXADataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXADataSource.setUser(prop.getProperty("druid.username"));
        mysqlXADataSource.setPassword(prop.getProperty("druid.password"));

        AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
        ds.setXaDataSourceClassName("com.mysql.cj.jdbc.MysqlXADataSource");
        ds.setUniqueResourceName("dataSourceA");
        ds.setXaDataSource(mysqlXADataSource);

        //ds.setPoolSize(1);
        //获取连接失败重新获等待最大时间，在这个时间内如果有可用连接，将返回
        ds.setBorrowConnectionTimeout(30);
        //是否设置并发连接验证，默认为true
        ds.setConcurrentConnectionValidation(true);
        //ds.setDefaultIsolationLevel();
        //获取连接失败重新获等待最大时间，在这个时间内如果有可用连接，将返回
        ds.setBorrowConnectionTimeout(1800);
        try {
            //最大可等待获取datasouce的时间
            ds.setLoginTimeout(30);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //连接回收时间
        ds.setMaintenanceInterval(30);
        //最大闲置时间，超过最小连接池连接的连接将将关闭
        ds.setMaxIdleTime(30);
        ds.setMaxLifetime(120);//
        //最大连接
        ds.setMaxPoolSize(20);
        //最小连接
        ds.setMinPoolSize(5);
        ds.setTestQuery("SELECT 1");
        //最大获取数据时间，如果不设置这个值，Atomikos使用默认的5分钟，那么在处理大批量数据读取的时候，一旦超过5分钟，就会抛出类似 Resultset is close 的错误
        ds.setReapTimeout(10);

        return ds;
    }
    @Autowired
    @Bean(name = "dataSourceB")
    public AtomikosDataSourceBean dataSourceB(Environment env) throws SQLException {
        AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
        ds.setXaDataSourceClassName("oracle.jdbc.xa.client.OracleXADataSource");

        ds.setUniqueResourceName("dataSourceB");
        Properties prop2 = build(env, "spring.datasource.dataSourceB.druid.");
        ds.setPoolSize(5);
        OracleXADataSource oracleXADataSource = new OracleXADataSource();
        oracleXADataSource.setURL(prop2.getProperty("druid.url"));
        oracleXADataSource.setUser(prop2.getProperty("druid.username"));
        oracleXADataSource.setPassword(prop2.getProperty("druid.password"));
        ds.setXaDataSource(oracleXADataSource);


        ds.setBorrowConnectionTimeout(180);//获取连接失败重新获等待最大时间，在这个时间内如果有可用连接，将返回
        ds.setConcurrentConnectionValidation(true);//是否设置并发连接验证，默认为true
        //ds.setDefaultIsolationLevel();
        try {
            ds.setLoginTimeout(30);//最大可等待获取datasouce的时间
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ds.setMaintenanceInterval(30);//连接回收时间
        ds.setMaxIdleTime(30);//最大闲置时间，超过最小连接池连接的连接将将关闭
        ds.setMaxLifetime(120);//
        ds.setMaxPoolSize(20);//最大连接
        ds.setMinPoolSize(5);//最小连接
        ds.setTestQuery("SELECT 'HELLO' FROM DUAL");
        ds.setReapTimeout(10);//最大获取数据时间，如果不设置这个值，Atomikos使用默认的5分钟，那么在处理大批量数据读取的时候，一旦超过5分钟，就会抛出类似 Resultset is close 的错误
        return ds;
    }
   
    
    @Bean(name = "dataSource")
    public DynamicDataSource dataSource(@Qualifier("dataSourceA") DataSource dataSourceA, @Qualifier("dataSourceB")DataSource dataSourceB) {
        DynamicDataSource dataSource = new DynamicDataSource();
        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
        targetDataSources.put("dataSourceA", dataSourceA);
        targetDataSources.put("dataSourceB", dataSourceB);
        dataSource.setTargetDataSources(targetDataSources);
        dataSource.setDefaultTargetDataSource(dataSourceA);
        return dataSource;
    }
    
    /**
     * 注册一个StatViewServlet
     * @return
     */
    @Bean
    public ServletRegistrationBean DruidStatViewServle(){
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
        
        //添加初始化参数：initParams
        /** 白名单，如果不配置或value为空，则允许所有 */
        servletRegistrationBean.addInitParameter("allow","127.0.0.1,192.0.0.1");
        /** 黑名单，与白名单存在相同IP时，优先于白名单 */
        servletRegistrationBean.addInitParameter("deny","192.0.0.1");
        /** 用户名 */
        servletRegistrationBean.addInitParameter("loginUsername","admin");
        /** 密码 */
        servletRegistrationBean.addInitParameter("loginPassword","admin");
        /** 禁用页面上的“Reset All”功能 */
        servletRegistrationBean.addInitParameter("resetEnable","false");
        return servletRegistrationBean;
    }
    
    /**
     * 注册一个：WebStatFilter
     * @return
     */
    @Bean
    public FilterRegistrationBean druidStatFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        
        /** 过滤规则 */
        filterRegistrationBean.addUrlPatterns("/*");
        /** 忽略资源 */
        filterRegistrationBean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }
    @Bean
    public WallFilter wallFilter(){
        WallFilter wallFilter = new WallFilter();
        wallFilter.setConfig(wallConfig());
        return wallFilter;
    }
    
    @Bean
    public WallConfig wallConfig(){
        WallConfig wallConfig = new WallConfig();
        wallConfig.setMultiStatementAllow(true);//允许一次执行多条语句
        wallConfig.setNoneBaseStatementAllow(true);//允许一次执行多条语句
        return wallConfig;
    }

    
   
}
