package com.bzz.cloud.framework.config;


import com.bzz.cloud.framework.plugin.BzzPageInterceptor;
import com.bzz.cloud.framework.plugin.BzzPageInterceptor2;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.bzz.cloud.*.dao",sqlSessionFactoryRef = "sqlSessionFactory")
public class MybatisConfig {
	
	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactory sqlSessionFactoryBean(@Qualifier(value = "dataSource") DataSource dataSource) {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		//MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		//插件
		//Interceptor[] plugins = new Interceptor[1];

		//分页插件
		/*PaginationInterceptor pageInterceptor = new PaginationInterceptor();
		*/
		/*BzzPageInterceptor2 pageInterceptor = new BzzPageInterceptor2();
		plugins[0] = pageInterceptor;
		bean.setPlugins(plugins);*/

		try {
			ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			Resource mybatisConfigXml = resolver.getResource("classpath:MybatisConfig.xml");
			bean.setConfigLocation(mybatisConfigXml);
			bean.setMapperLocations(resolver.getResources("classpath:mappers/**/*.xml"));
			return bean.getObject();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	@Bean
	public SqlSessionTemplate sqlSessionTemplate(@Qualifier(value = "sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	
}
