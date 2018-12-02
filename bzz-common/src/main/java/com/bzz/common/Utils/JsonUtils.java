package com.bzz.common.Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtils {
	
	
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

}
