package com.bzz.cloud;

import static org.junit.Assert.assertTrue;

import com.bzz.cloud.rbac.entity.SysUser;
import com.bzz.common.Utils.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

       // System.out.println(JsonUtils.object2Json(dataMap));

        String json = "{userName: \"admin\", password: \"admin\", loginType: \"account\"}";
        SysUser sysUser = new SysUser();
        JsonUtils.filter(sysUser.getClass(),null,"sysRoleList");

       /* ObjectMapper objectMapper = new ObjectMapper();
        SysUser sysUser1 = objectMapper.readValue(json, sysUser.getClass());*/



        System.out.println(JsonUtils.json2Object(json, sysUser.getClass()));



    }
}
