package com.bzz.cloud.framework.aspect;

import com.bzz.common.Utils.DateUtils;
import com.bzz.common.Utils.JsonUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

@EnableAspectJAutoProxy(proxyTargetClass = true)
@Aspect
@Component
public class ReqLoggerAspect {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 在这里定义切面的点，Pointcut的表达式语法需要匹配到你调用的方法中
	 */
	@Pointcut("within(com.bzz.cloud.*.web.*)")
	public void declareJoinPointExpression() {
	}
	
	/*@Before("declareJoinPointExpression()")
	public void addBeforeLogger(JoinPoint joinPoint) {
		try {
			RequestAttributes ra = RequestContextHolder.getRequestAttributes();
			ServletRequestAttributes sra = (ServletRequestAttributes) ra;
			HttpServletRequest request = sra.getRequest();
			
			String url = request.getRequestURL().toString();
			String method = request.getMethod();
			String uri = request.getRequestURI();
			String userAgent = request.getHeader("User-Agent");
			Cookie cookie = null;
			*//*Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie c : cookies) {
					if (c.getName().endsWith("Token")) {
						cookie = c;
						break;
					}
				}
			}*//*
			String CookieString=cookie==null?"": JsonUtils.object2Json(cookie);
			String params = JsonUtils.object2Json(request.getParameterMap());
			
			String apiName = "";
			String targetName = joinPoint.getTarget().getClass().getName();
			String methodName = joinPoint.getSignature().getName();
			Object[] arguments = joinPoint.getArgs();
			Class targetClass = Class.forName(targetName);
			Method[] methods = targetClass.getMethods();
			
			logger.info("请求url: {},uri: {}, method: {}, params: {},Cookie:{},UserAgent:{}",
					url,uri, method, params, CookieString,userAgent);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}*/

	@Around(value = "declareJoinPointExpression()")
	public Object doAround(ProceedingJoinPoint proceeding) throws Throwable {
		long beforeTime = System.currentTimeMillis();
		String startTime = DateUtils.getLongDateToString(beforeTime,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S"));
		
		//执行被拦截的方法 result是返回结果
		Object result = proceeding.proceed();
		long afterTime = System.currentTimeMillis();
		String endTime = DateUtils.getLongDateToString(afterTime,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S"));
		
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		ServletRequestAttributes sra = (ServletRequestAttributes) ra;
		HttpServletRequest request = sra.getRequest();
		String url = request.getRequestURL().toString();
		String method = request.getMethod();
		Cookie[] cookies = request.getCookies();
		String userAgent = request.getHeader("User-Agent");
		String params = JsonUtils.object2Json(request.getParameterMap());

		logger.info("请求url: {},开始时间：{},结束时间：{}, 耗时：{}ms, method: {}, params: {},Cookie:{},UserAgent:{}",url,startTime,endTime,afterTime - beforeTime, method, params,cookies==null?"": cookies.toString(),userAgent);
		//此处可以在log输出result，依据业务要求处理
		return result;
	}
}
