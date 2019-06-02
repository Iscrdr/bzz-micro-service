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
            String loginType = request.getParameter("loginType");
            Map<String,Object> dataMap = new HashMap<>();
            if(StringUtils.isNotBlank(loginType) && loginType.equals("account")){
                dataMap.put("loginType","account");//登录类型
            }
            dataMap.put("status","fail");//登录状态
            dataMap.put("currentAuthority", null);//权限

            if (e instanceof UsernameNotFoundException){
                dataMap.put("errCode", ResponseData.USERNAME_NOT_FOUND);//用户找不到
            }else if(e instanceof AccountExpiredException){
                dataMap.put("errCode", ResponseData.USERNAME_ACCOUNT_EXPIRED);//账号过期,过期无法验证
            }else if(e instanceof LockedException){
                dataMap.put("errCode", ResponseData.USERNAME_ACCOUNT_LOCKED);//账号被锁定
            }else if(e instanceof DisabledException){
                dataMap.put("errCode", ResponseData.USERNAME_ENABLED);//账号被禁用
            }else if(e instanceof CredentialsExpiredException){
                dataMap.put("errCode", ResponseData.USERNAME_CREDENTIALS_EXPIRED);//密码或者凭证过期
            }else if(e instanceof BadCredentialsException){
                dataMap.put("errCode", ResponseData.PASSWORD_ERROR);//密码错误
            }else if(e instanceof UnapprovedClientAuthenticationException){
                dataMap.put("errCode", ResponseData.USER_CLIENT_NOUNAUTHORIZED);//客户端没有授权
            }else {
                dataMap.put("errCode", ResponseData.LOGIN_ERROR_CODE);//登录失败
            }

            ObjectMapper objectMapper = new ObjectMapper();
            response.getWriter().write(objectMapper.writeValueAsString(dataMap));
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        //如果不是json格式，返回view
        //super.onAuthenticationFailure(request,response,e);
    }

}
