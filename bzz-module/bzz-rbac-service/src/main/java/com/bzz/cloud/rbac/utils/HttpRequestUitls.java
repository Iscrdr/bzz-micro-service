package com.bzz.cloud.rbac.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-05-19 00-31
 * @Modified by:
 * @Description:
 */
public class HttpRequestUitls {

    public static String getToken(HttpServletRequest request){

        String token = request.getParameter("access_token");
        if(StringUtils.isBlank(token)){
            String header = request.getHeader("Authorization");
            if(StringUtils.isNotBlank(header) && header.startsWith("Bearer")){
                String[] s = header.split(" ");
                token = s[1];
            }else {
                Cookie[] cookies = request.getCookies();
                if(null != cookies && cookies.length>0){
                    for (Cookie c:cookies){
                        System.out.println(c.getName());
                        if(c.getName().equals("token")){
                            token = c.getValue();
                            return token;
                        }
                    }
                }

            }
            return token;
        }
        return token;
    }
}
