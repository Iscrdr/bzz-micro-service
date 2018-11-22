package com.bzz.cloud.framework.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
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
        prop.put("driverClassName", env.getProperty(prefix + "driver-class-name"));
        prop.put("jdbcUrl", env.getProperty(prefix + "jdbc-url"));
        prop.put("username", env.getProperty(prefix + "username"));
        prop.put("password", env.getProperty(prefix + "password"));
    
        
        
        prop.put("maximumPoolSize", env.getProperty(prefix + "maximum-pool-size"));
        prop.put("poolName", env.getProperty(prefix + "pool-name"));
       
        prop.put("connectionTimeout", env.getProperty(prefix + "connection-timeout"));
        prop.put("validationTimeout", env.getProperty(prefix + "validation-timeout"));
    
        prop.put("maxLifetime", env.getProperty(prefix + "max-lifetime"));
        prop.put("idleTimeout", env.getProperty(prefix + "idle-timeout"));
        
        
        prop.put("connectionTestQuery", env.getProperty(prefix + "connection-test-query"));
        prop.put("minimumIdle", env.getProperty(prefix + "minimum-idle"));
        prop.put("initializationFailTimeout", env.getProperty(prefix + "initialization-fail-timeout"));
        return prop;
    }
    
    @Autowired
    @Bean(name = "dataSource")
    public DataSource dataSource(Environment env) {
        Properties properties = build(env, "spring.datasource.hikari.");
        HikariConfig hikariConfig = new HikariConfig(properties);
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        return dataSource;
    }
    
    
   
   
    
   
}
