package com.bzz.common.Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
}
