package com.bzz.cloud.framework.aspect;

import com.bzz.cloud.framework.annotations.DataBaseSourceTarget;
import com.bzz.cloud.framework.dynamicdatasource.DataSourceContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-03-30 18-22
 * @Modified by:
 * @Description: 数据源切换
 */
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Aspect
@Order(-999)
@Component
public class DataSourceAspect {
	
	private static final Logger logger = LoggerFactory.getLogger(DataSourceAspect.class);
	
	
	/**
	 * 在这里定义切面的点，Pointcut的表达式语法需要匹配到你调用的方法中
	 */
	@Pointcut("within(com.bzz.cloud.*.service.*)")
	public void declareJoinPointExpression() {
	}
	
	@Before("declareJoinPointExpression()")
	public void before(JoinPoint joinPoint) {

		Map<String,Object> map = DataSourceContextHolder.getDataSourceAndDialect();
		Object target = joinPoint.getTarget();
		String method = joinPoint.getSignature().getName();
		Class<?> classz = target.getClass();
		Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getMethod().getParameterTypes();
		/**
		 *
		 */
		Map<String,Object> dataSourceMap = new HashMap<>(3);
		try {

			Method m = classz.getMethod(method, parameterTypes);

			DataBaseSourceTarget dataSource = null;
			//获取service类或者类中方法上的数据源注解
			if (null != m && m.isAnnotationPresent(DataBaseSourceTarget.class)) {
				dataSource = m.getDeclaredAnnotation(DataBaseSourceTarget.class);
			}else if(classz.isAnnotationPresent(DataBaseSourceTarget.class)){
				dataSource = classz.getDeclaredAnnotation(DataBaseSourceTarget.class);
			}
			//切换数据源
			if (dataSource != null) {
				logger.info("数据源由{},==> 切换为：{},方言由{},==> 切换为：{}",map.get("dataSource"), dataSource.dataSourceValue(),map.get("dialect"),dataSource.dataBaseDialect());
				//数据源名称
				dataSourceMap.put("dataSource",dataSource.dataSourceValue());
				//数据源方言
				dataSourceMap.put("dialect",dataSource.dataBaseDialect());
			}else {
				//默认数据源名称
				dataSourceMap.put("dataSource","dataSourceA");
				//默认数据源方言
				dataSourceMap.put("dialect","mysql");
			}

			DataSourceContextHolder.setDataSourceAndDialect(dataSourceMap);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * AbstractRoutingDataSource 底层实现了 方法执行后的数据源切换，此处可以不处理
	 * @param proceeding
	 */
	/*@Around("declareJoinPointExpression()")
	public void doAround(ProceedingJoinPoint proceeding)throws Throwable  {
		long beforeTime = System.currentTimeMillis();
		String startTime = DateUtils.getLongDateToString(beforeTime,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S"));

		proceeding.proceed();
		
		long afterTime = System.currentTimeMillis();
		String endTime = DateUtils.getLongDateToString(afterTime,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S"));

		Map<String,Object>  dataSourceAndDialect = DataSourceContextHolder.getDataSourceAndDialect();
		
		*//*HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		StringBuffer requestURL = request.getRequestURL();
		if(null != dataSourceAndDialect || dataSourceAndDialect.size() > 0){
			logger.info("请求requestURL:{},开始时间:{},结束时间:{},耗时:{}毫秒,移除数据源：{}",requestURL.toString(),startTime,endTime,(afterTime-beforeTime),dataSourceAndDialect.get(0));
			DataSourceContextHolder.removeDataSource();
		}*//*

		logger.info("方法:{},开始时间:{},结束时间:{},耗时:{}毫秒,移除数据源：{}",
				proceeding.getTarget().getClass().getName(),
				startTime,endTime,(afterTime-beforeTime),
				dataSourceAndDialect.get("dataSource"));
		DataSourceContextHolder.removeDataSource();
	}*/
	
}
