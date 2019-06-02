package com.bzz.common.Utils;

import com.bzz.common.filter.BzzJsonFilter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class JsonUtils {

	private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static final ObjectMapper mapper;

	private static BzzJsonFilter jacksonFilter = new BzzJsonFilter();

	static {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
		mapper = new ObjectMapper();

		mapper.setDateFormat(dateFormat);
		// 允许对象忽略json中不存在的属性
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// 允许出现特殊字符和转义符
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		// 允许出现单引号
		mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		// 忽视为空的属性
		//mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
	}
	public static void filter(Class<?> clazz, String include, String exclude) {
		if (clazz == null)
			return;
		if (StringUtils.isNotBlank(exclude)) {
			jacksonFilter.exclude(clazz, exclude);
		}
		mapper.addMixIn(clazz, jacksonFilter.getClass());
	}
	/**
	 * 将javabean转换为json字符串
	 * @param bean
	 * @return
	 */
	public static String object2Json(Object bean){
		
		String beanStr = null;

		try {

			mapper.setFilterProvider(jacksonFilter);
			beanStr = mapper.writeValueAsString(bean);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return beanStr;
	}

	/**
	 * 将json字符串转换为javabean
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static Object json2Object(String json,Class clazz){

		Object obj = null;
		try {
			obj = mapper.readValue(json, clazz);

		}  catch (IOException e) {
			e.printStackTrace();
		}
		return obj;
	}
	/**
	 *
	 */
	public static String getFeildFromJson(String feild,String json){
		String result = null;
		try {
			JsonNode node = mapper.readTree(json);
			result = node.get(feild).asText();
		}  catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
