package com.bzz.common.filter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-05-21 00-04
 * @Modified by:
 * @Description:
 */
@com.fasterxml.jackson.annotation.JsonFilter("BzzJsonFilter")
public class BzzJsonIncludeFilter extends FilterProvider {

    private Map<Class<?>, Set<String>> includeMap = new HashMap<>();



    public void include(Class<?> clazz,String str){
        includeMap.put(clazz,getFiledSet(str));
    }

    private Set<String> getFiledSet(String str){
        Set<String> filedSet=new HashSet<>();
        if(StringUtils.isNotBlank(str)){
            String[] fields = str.split(",");
            for(String s:fields){
                filedSet.add(s);
            }
        }
        return filedSet;
    }


    @Override
    public BeanPropertyFilter findFilter(Object filterId) {
        throw new UnsupportedOperationException("Access to deprecated filters not supported");
    }

    @Override
    public PropertyFilter findPropertyFilter(Object filterId, Object valueToFilter) {
        return new SimpleBeanPropertyFilter() {
            @Override
            public void serializeAsField(Object pojo, JsonGenerator jgen, SerializerProvider prov, PropertyWriter writer)
                    throws Exception {
                if (isContainFiled(pojo.getClass(), writer.getName())) {
                    writer.serializeAsField(pojo, jgen, prov);
                } else if (!jgen.canOmitFields()) {
                    writer.serializeAsOmittedField(pojo, jgen, prov);
                }
            }
        };
    }

    public boolean isContainFiled(Class<?> type, String name) {
        Set<String> excludeFields = includeMap.get(type);

        boolean flag = false;
        //不需要转为json的字段
        if (excludeFields != null && excludeFields.contains(name)) {
            flag = true;
        }
        return flag;
    }

}
