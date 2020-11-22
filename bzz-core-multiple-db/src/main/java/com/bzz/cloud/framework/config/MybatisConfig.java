package com.bzz.cloud.framework.config;


import com.bzz.cloud.framework.Interceptor.PageInterceptor;
import com.bzz.cloud.framework.annotations.BzzMyBatisDao;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @author cloud
 */
@Configuration
@MapperScan(basePackages = "com.bzz.cloud.*.dao",sqlSessionFactoryRef = "sqlSessionFactory",
		annotationClass = BzzMyBatisDao.class)
public class MybatisConfig {


	@Autowired
	private PageInterceptor pageInterceptor;

	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactory sqlSessionFactoryBean(@Qualifier(value = "dataSource") DataSource dataSource) {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		bean.setPlugins(new Interceptor[]{pageInterceptor});
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
