package com.bzz.cloud;

import com.bzz.cloud.rbac.entity.SysMenu;
import com.bzz.cloud.rbac.entity.SysAuthority;
import com.bzz.cloud.rbac.entity.SysRole;
import com.bzz.cloud.rbac.entity.SysUser;
import com.bzz.common.Utils.JsonUtils;
import com.bzz.common.filter.BzzJsonFilter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.gson.Gson;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue( true );
    }
    @Test
    public void testPassword() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // 加密
        String password = passwordEncoder.encode("unity_1".trim());
        System.out.println(password);


    }

    @Test
    public void testJson() throws IOException {
        Map<String,Object> dataMap = new HashMap<>();

        dataMap.put("loginType","account");//登录类型
        dataMap.put("status","ok");//登录状态
        dataMap.put("currentAuthority", "['admin','user']");//权限

        System.out.println(JsonUtils.object2Json(dataMap));

        String json1 = "{\"userName\": \"admin\", \"password\": \"admin\", \"loginType\": \"account\"}";
        String json2 = "{\"name\": \"admin\", \"path\": \"admin\", \"component\": \"account\"}";
        SysUser sysUser = new SysUser();

        ObjectMapper mapper = new ObjectMapper();

        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        //设置为true时，属性名称不带双引号
        mapper.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, true);
        //反序列化是是否允许属性名称不带双引号
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SysMenu sysApi = mapper.readValue(json2, SysMenu.class);
        System.out.println(sysApi.toString());

        //JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, SysAuthority.class);
        SysUser user = mapper.readValue(json1, sysUser.getClass());
        System.out.println(user.toString());


        /*SysUser user1 = mapper.readValue(json1,  new TypeReference<List<List<List<SysAuthority>>>>() {
        });
        System.out.println(user1.toString());

        JsonUtils.filter(sysUser.getClass(),null,"sysApiList,sysApi,routes,sysAuthorityList,sysAuthority");
        System.out.println(JsonUtils.json2Object(json1, sysUser.getClass()));
*/


    }
}
