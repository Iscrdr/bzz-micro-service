package com.bzz.cloud.rbac.web;

import com.bzz.cloud.core.utils.TokenResult;
import com.bzz.cloud.rbac.entity.SysMenu;
import com.bzz.cloud.rbac.entity.SysUser;
import com.bzz.cloud.rbac.service.Oauth2FeignClientService;
import com.bzz.cloud.rbac.service.SysMenuService;
import com.bzz.cloud.rbac.service.SysUserService;
import com.bzz.cloud.rbac.utils.UserUtil;
import com.bzz.common.utils.ResponseData;
import com.bzz.common.utils.ResponseResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @description：用户登录
 * @author     ：Iscrdr
 * @email      ：624003618@qq.com
 * @date       ：2020-03-02 03:50
 * @modified By：
 * @version: 1.0.0$
 */
@RestController
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private SysUserService sysUserService;


    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private RedisTemplate redisTemplate;


    private JdbcClientDetailsService jdbcClientDetailsService;


    public void setJdbcClientDetailsService(JdbcClientDetailsService jdbcClientDetailsService) {
        this.jdbcClientDetailsService = jdbcClientDetailsService;
    }

    @Autowired
    private Oauth2FeignClientService oauth2FeignClientService;



    @RequestMapping(value = "/login",method = {RequestMethod.POST})
    @ResponseBody
    public ResponseResult login(HttpServletRequest request, HttpServletResponse response, @RequestBody(required=false) SysUser sysUser){

        ResponseResult rr = new ResponseResult();
        Map<String,String> msgMap = new HashMap<>();
        if(null == sysUser || StringUtils.isBlank(sysUser.getLoginType())){
            msgMap.put(ResponseData.USERNAME_NOT_FOUND,"登录方式不支持");
            rr.setMsgMap(msgMap);
            rr.setData(sysUser);
            rr.setSuccess(false);
            return  rr;
        }
        /*
         * web网页登录方式：
         */
        if (sysUser.getLoginType().equals("account")) {
            if(StringUtils.isBlank(sysUser.getUserName())){
                msgMap.put(ResponseData.USERNAME_NOT_FOUND,"登录正好不能为空");
                rr.setMsgMap(msgMap);
                rr.setData(sysUser);
                rr.setSuccess(false);
                return  rr;
            }
            /*
             * 校验密码是否为空
             */
            if (StringUtils.isBlank(sysUser.getPassword().trim())){
                msgMap.put(ResponseData.PASSWORD_ERROR,"登录失败,密码不能为空");
                rr.setMsgMap(msgMap);
                rr.setData(sysUser);
                rr.setSuccess(false);
                return  rr;
            }
            /*
             * 账号不存在
             */
            SysUser sysUser1 = sysUserService.getUserByLoginName(sysUser);
            if (StringUtils.isBlank(sysUser1.getUserName())){
                msgMap.put(ResponseData.USERNAME_NOT_FOUND,"登录失败,账号没有找到");
                rr.setMsgMap(msgMap);
                rr.setData(sysUser);
                rr.setSuccess(false);
                return  rr;
            }
            /*
             * 账号未授权
             */
            if(StringUtils.isBlank(sysUser1.getClientId())) {
                msgMap.put(ResponseData.USERNAME_NOT_FOUND,"登录失败,账号授权");
                rr.setMsgMap(msgMap);
                rr.setData(sysUser);
                rr.setSuccess(false);
                return  rr;
            }


            ClientDetails clientDetails = jdbcClientDetailsService.loadClientByClientId(sysUser1.getClientId());




          try{
              try {
                  /*
                   *从认证服务器获取token
                   */
                  String jsonToken = oauth2FeignClientService.getToken(clientDetails.getClientId(),
                          clientDetails.getClientSecret(),
                          "password",
                          sysUser.getUserName(),
                          sysUser.getPassword(),
                          "");

                  Map<String,Object> dataMap = new HashMap<>();
                  /*
                   * 解析token
                   */
                  ObjectMapper objectMapper = new ObjectMapper();
                  TokenResult tokenResult = objectMapper.readValue(jsonToken, TokenResult.class);
                  if(tokenResult !=null && StringUtils.isNotBlank(tokenResult.getError())){

                      //登录状态
                      dataMap.put("status","false");

                      //返回代码
                      msgMap.put(ResponseData.PASSWORD_ERROR,"密码错误");
                      //返回数据
                      rr.setData(dataMap);
                      //返回信息
                      rr.setMsgMap(msgMap);
                      rr.setSuccess(false);
                      return rr;
                  }

                  response.setHeader("Authorization","Bearer "+tokenResult.getAccess_token());
                  Cookie cookie = new Cookie("token",tokenResult.getAccess_token());
                  cookie.setHttpOnly(true);
                  cookie.setMaxAge(tokenResult.getExpires_in());
                  response.addCookie(cookie);
                  //查询用户拥有的所有权限
                  List<SysMenu> userRoleMenuList  = (List<SysMenu>)redisTemplate.opsForValue().get(sysUser1.getUserName());
                  if(null == userRoleMenuList || userRoleMenuList.size() <=0){
                      SysUser sysUser2 = sysMenuService.getAllMenu(sysUser1);
                      userRoleMenuList = UserUtil.getUserAuthorityList(sysUser2);
                      redisTemplate.opsForValue().set(sysUser2.getUserName(), userRoleMenuList, tokenResult.getExpires_in(), TimeUnit.SECONDS);
                  }


                  //登录类型
                  dataMap.put("loginType","account");
                  //登录状态
                  dataMap.put("status","ok");
                  //权限
                  dataMap.put("currentAuthority", userRoleMenuList);
                  //返回代码
                  msgMap.put(ResponseData.LOGIN_CODE,"恭喜您，登录成功");
                  //返回数据
                  rr.setData(dataMap);
                  //返回信息
                  rr.setMsgMap(msgMap);
                  rr.setSuccess(true);
                  return rr;
              } catch (IOException e) {
                  Map<String,Object> dataMap = new HashMap<>(2);
                  //登录类型
                  dataMap.put("loginType","account");
                  //登录状态
                  dataMap.put("status","error");
                  //返回代码
                  msgMap.put(ResponseData.USER_CLIENT_NOUNAUTHORIZED,"账号未受权");
                  //返回数据
                  rr.setData(dataMap);
                  //返回信息
                  rr.setMsgMap(msgMap);
                  rr.setSuccess(true);
                  e.printStackTrace();
                  return rr;
              }
        }catch (AuthenticationException ex) {
            if(ex instanceof BadCredentialsException){
                msgMap.put(ResponseData.PASSWORD_ERROR,"密码错误，请重新输入密码");
            }else if(ex instanceof AccountExpiredException){
                msgMap.put(ResponseData.USERNAME_ACCOUNT_EXPIRED,"账号已经过期");
            }else if(ex instanceof LockedException){
                msgMap.put(ResponseData.USERNAME_ACCOUNT_LOCKED,"账号已经被锁定,请先解锁");
            }else if(ex instanceof CredentialsExpiredException){
                msgMap.put(ResponseData.USERNAME_CREDENTIALS_EXPIRED,"密码已经过期，请重新设置密码");
            }else if(ex instanceof DisabledException){
                msgMap.put(ResponseData.USERNAME_ENABLED,"账号被禁用,请联系管理员");
            }else {
                msgMap.put(ResponseData.LOGIN_ERROR_CODE,"抱歉，您输入的账号不正确");
            }
            rr.setMsgMap(msgMap);
            rr.setSuccess(false);
            rr.setData(sysUser);
            ex.printStackTrace();
              return rr;
        }

        }

        msgMap.put(ResponseData.RESPONE_CODE,"登录成功");
        rr.setSuccess(true);
        rr.setMsgMap(msgMap);

        return rr;
    }
}
