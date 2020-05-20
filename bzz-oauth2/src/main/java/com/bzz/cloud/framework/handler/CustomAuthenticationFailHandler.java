package com.bzz.cloud.framework.handler;

import com.bzz.common.Utils.DateUtils;
import com.bzz.common.Utils.ResponseData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-05-27 02-06
 * @Modified by:
 * @Description:
 */
@Component
public class CustomAuthenticationFailHandler  extends SimpleUrlAuthenticationFailureHandler {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(CustomAuthenticationFailHandler.class);


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)  {

        logger.info("登录失败:{},时间：{}",e.getMessage(),DateUtils.getCurrentTime(new Date(),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));

        try {
            response.setContentType("application/json;charset=UTF-8");

            Map<String,Object> dataMap = new HashMap<>(3);
            //登录类型
            dataMap.put("loginType","account");
            //登录状态
            dataMap.put("status","error");
            //权限
            dataMap.put("currentAuthority", "");

            if (e instanceof UsernameNotFoundException){
                //用户找不到
                dataMap.put("resCode", ResponseData.USERNAME_NOT_FOUND);
            }else if(e instanceof AccountExpiredException){
                //账号过期,过期无法验证
                dataMap.put("resCode", ResponseData.USERNAME_ACCOUNT_EXPIRED);
            }else if(e instanceof LockedException){
                //账号被锁定
                dataMap.put("resCode", ResponseData.USERNAME_ACCOUNT_LOCKED);
            }else if(e instanceof DisabledException){
                //账号被禁用
                dataMap.put("resCode", ResponseData.USERNAME_ENABLED);
            }else if(e instanceof CredentialsExpiredException){
                //密码或者凭证过期
                dataMap.put("resCode", ResponseData.USERNAME_CREDENTIALS_EXPIRED);
            }else if(e instanceof BadCredentialsException){
                //密码错误
                dataMap.put("resCode", ResponseData.PASSWORD_ERROR);
            }else if(e instanceof UnapprovedClientAuthenticationException){
                //客户端没有授权
                dataMap.put("resCode", ResponseData.USER_CLIENT_NOUNAUTHORIZED);
            }else {
                //登录失败
                dataMap.put("resCode", ResponseData.LOGIN_ERROR_CODE);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(dataMap));
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        //如果不是json格式，返回view
        //super.onAuthenticationFailure(request,response,e);
    }

}
