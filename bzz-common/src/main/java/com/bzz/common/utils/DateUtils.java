package com.bzz.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: bzz-cloud
 * @description: 日期工具类
 * @author: Yang qianli
 * @create: 2018-10-16 21:14
 * @version: 1.0.0
 */
public class DateUtils {
	
	/**
	 * @param: time 时间 long类型
	 * @return: 时间字符串 “yyyy-MM-dd HH:mm:ss”
	 * @throws:
	 * @description:
	 * @author: Yang qianli
	 * @createTime: 2018-10-16 21:19:59
	 * @updateTime: 2018-10-16 21:19:59
	 * @version: 1.0.0
	 */
	public static String getLongDateToString(long time,SimpleDateFormat sdf){
		Date date = new Date(time);
		try{
			String format = sdf.format(date);
			return format;
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
		
	}

	public static Date getDateToString(String date,SimpleDateFormat sdf){
		Date date1 = null;
		try{
			date1 = sdf.parse(date);
		}catch (Exception e){
			e.printStackTrace();
		}
		return date1;

	}

	public static String getCurrentTime(Date date,SimpleDateFormat sdf){
		String format = null;
		try{
			format = sdf.format(date);
		}catch (Exception e){
			e.printStackTrace();
		}
		return format;

	}
}
