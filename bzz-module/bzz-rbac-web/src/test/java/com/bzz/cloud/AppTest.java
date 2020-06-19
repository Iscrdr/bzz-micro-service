package com.bzz.cloud;

import com.bzz.cloud.rbac.entity.SysMenu;
import com.bzz.cloud.rbac.entity.SysUser;
import com.bzz.cloud.rbac.uitls.RequestParams;
import com.bzz.common.utils.JsonUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest {


    @Test
    public void testJson2Object() throws JsonProcessingException {
        String json = "{requestParams: {\"ids\": [4, 5]}}";
        SysUser sysUser = new SysUser();

        ObjectMapper mapper = new ObjectMapper();

        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        //设置为true时，属性名称不带双引号
        mapper.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, true);
        //反序列化是是否允许属性名称不带双引号
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        RequestParams sysUser1 = mapper.readValue(json, RequestParams.class);
        System.out.println(sysUser1.getIds());

        RequestParams requestParams = new RequestParams();
        ArrayList<Long> longs = new ArrayList<>();
        longs.add(4L);
        longs.add(5L);
        requestParams.setIds(longs);
        String s = mapper.writeValueAsString(requestParams);
        System.out.println(s);

    }

}
