package com.bzz.common.Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public class JsonUtils {

	/**
	 * 将javabean转换为json字符串
	 * @param bean
	 * @return
	 */
	public static String object2Json(Object bean){
		
		String beanStr = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
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
		ObjectMapper mapper = new ObjectMapper();
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
		ObjectMapper mapper = new ObjectMapper();
		try {

			JsonNode node = mapper.readTree(json);

			result = node.get(feild).asText();
		}  catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
