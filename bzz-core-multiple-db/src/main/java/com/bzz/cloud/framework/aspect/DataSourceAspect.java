package com.bzz.cloud.framework.aspect;

import com.bzz.cloud.framework.annotations.DataBaseSourceTarget;
import com.bzz.cloud.framework.dynamicdatasource.DataSourceContextHolder;
import com.bzz.common.Utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
		
		String  srcDataSource = DataSourceContextHolder.getDataSourceAndDialect().get(0);
		String  srcDialect = DataSourceContextHolder.getDataSourceAndDialect().get(1);
		Object target = joinPoint.getTarget();
		String method = joinPoint.getSignature().getName();
		Class<?> classz = target.getClass();
		Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getMethod().getParameterTypes();
		List<String> list = new ArrayList<String>(2);
		try {
			Method m = classz.getMethod(method, parameterTypes);
			DataBaseSourceTarget dataSource = null;
			if (null != m && m.isAnnotationPresent(DataBaseSourceTarget.class)) {
				dataSource = m.getAnnotation(DataBaseSourceTarget.class);
				
			}else if(classz.isAnnotationPresent(DataBaseSourceTarget.class)){
				dataSource = classz.getAnnotation(DataBaseSourceTarget.class);
			}
			//切换数据源
			if (dataSource != null && !srcDataSource.equals(dataSource.dataSourceValue())) {
				logger.info("数据源由{},==> 切换为：{},方言由{},==> 切换为：{}",srcDataSource, dataSource.dataSourceValue(),srcDialect,dataSource.dataBaseDialect());
				list.add(dataSource.dataSourceValue());//数据源名称
				list.add(dataSource.dataBaseDialect());//数据源方言
				DataSourceContextHolder.setDataSourceAndDialect(list);
			}
			
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
		
		List<String>  dataSourceAndDialect = DataSourceContextHolder.getDataSourceAndDialect();
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		StringBuffer requestURL = request.getRequestURL();
		if(null != dataSourceAndDialect || dataSourceAndDialect.size() > 0){
			logger.info("请求requestURL:{},开始时间:{},结束时间:{},耗时:{}毫秒,移除数据源：{}",requestURL.toString(),startTime,endTime,(afterTime-beforeTime),dataSourceAndDialect.get(0));
			DataSourceContextHolder.removeDataSource();
		}

	}*/
	
}
