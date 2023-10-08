package com.bzz.cloud;



import com.bzz.cloud.rbac.entity.SysUser;
import com.bzz.common.utils.JsonUtils;
import com.bzz.common.utils.RSAUtils;
import com.fasterxml.jackson.databind.ObjectMapper;


import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;


public class AppTest {

    private final static String RSAKEY="qianli8811";
    

    @Test
    public void rsaEncode() throws Exception {
        PublicKey publicKey = RSAUtils.getPublicKey(RSAKEY);
        String format = publicKey.getFormat();
        byte[] encrypt = RSAUtils.encrypt("admin".getBytes(), format);
        System.out.println(encrypt.toString());
    }

    @Test
    public void rsaDecode() {

    }


    @Test
    public void testPassword() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // 加密
        String password = passwordEncoder.encode("unity_1".trim());
        System.out.println("=================================");
        System.out.println(password);
    }

    @Test
    public void testJson() throws IOException {
        Map<String,Object> dataMap = new HashMap<>();

        dataMap.put("loginType","account");//登录类型
        dataMap.put("status","ok");//登录状态
        dataMap.put("currentAuthority", "['admin','user']");//权限

       // System.out.println(JsonUtils.object2Json(dataMap));

        String json = "{\"userName\":\"admin\",\"password\":\"admin\",\"loginType\":\"account\"}";
        SysUser sysUser = new SysUser();
       /*  JsonUtils.filter(sysUser.getClass(),null,"sysRoleList");*/

       ObjectMapper objectMapper = new ObjectMapper();
        SysUser sysUser1 = objectMapper.readValue(json, sysUser.getClass());

        System.out.println(sysUser1.toString());

    }
}
