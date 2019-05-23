package com.bzz.cloud;

import static org.junit.Assert.assertTrue;

import com.bzz.common.Utils.JsonUtils;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
    public void testJson() {
        Map<String,Object> dataMap = new HashMap<>();

        dataMap.put("loginType","account");//登录类型
        dataMap.put("status","ok");//登录状态
        dataMap.put("currentAuthority", "['admin','user']");//权限

        System.out.println(JsonUtils.object2Json(dataMap));


    }
}
