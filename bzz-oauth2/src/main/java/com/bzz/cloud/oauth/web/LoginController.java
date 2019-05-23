package com.bzz.cloud.oauth.web;


import com.bzz.cloud.rbac.entity.SysUser;
import com.bzz.cloud.rbac.service.SysApiService;
import com.bzz.cloud.rbac.service.SysUserService;
import com.bzz.cloud.rbac.utils.UserUtil;
import com.bzz.cloud.utils.HttpRequestUitls;
import com.bzz.common.Utils.ResponseData;
import com.bzz.common.Utils.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
public class LoginController {


    @Autowired
    private DefaultTokenServices tokenServices;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysApiService sysApiService;
    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(value = "/login",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public ResponseResult login(HttpServletRequest request, HttpServletResponse response, SysUser sysUser, BindingResult result){
        ResponseResult rr = new ResponseResult();
        Map<String,String> msgMap = new HashMap<>();
        /**
         * web网页登录方式：
         */
        if (sysUser.getLoginType().equals("account")){

            if(null == sysUser || StringUtils.isBlank(sysUser.getUserName())){
                msgMap.put(ResponseData.USERNAME_NOT_FOUND,"登录失败,账号不能为空");
                rr.setMsgMap(msgMap);
                rr.setData(sysUser);
                rr.setSuccess(false);
                return  rr;
            }
            /**
             * 校验密码是否为空
             */
            if (StringUtils.isBlank(sysUser.getPassword().trim())){
                msgMap.put(ResponseData.PASSWORD_ERROR,"登录失败,密码不能为空");
                rr.setMsgMap(msgMap);
                rr.setData(sysUser);
                rr.setSuccess(false);
                return  rr;
            }
            SysUser sysUser1 = sysUserService.getUserByLoginName(sysUser);
            if (null == sysUser1 || StringUtils.isBlank(sysUser1.getUserName())){
                msgMap.put(ResponseData.USERNAME_NOT_FOUND,"登录失败,账号没有找到");
                rr.setMsgMap(msgMap);
                rr.setData(sysUser);
                rr.setSuccess(false);
                return  rr;
            }

            if(StringUtils.isBlank(sysUser1.getClientId())) {
                msgMap.put(ResponseData.USERNAME_NOT_FOUND,"登录失败,账号授权");
                rr.setMsgMap(msgMap);
                rr.setData(sysUser);
                rr.setSuccess(false);
                return  rr;
            }
            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(sysUser1.getClientId());
            if(clientDetails == null || StringUtils.isBlank(clientDetails.getClientId()) ){
                msgMap.put(ResponseData.USER_ACCOUNT_NOUNAUTHORIZED,"登录失败，账号没有授权");
                rr.setSuccess(false);
                rr.setMsgMap(msgMap);
                rr.setData(sysUser);
                return  rr;
            }

            /**
             * 判断token是否有效，如果有效则返回
             */
            String access_token = HttpRequestUitls.getToken(request);
            OAuth2AccessToken oAuth2AccessToken = null;
            List<String> userAuthorityList = null;
            if(StringUtils.isNotBlank(access_token)){
                oAuth2AccessToken = tokenServices.readAccessToken(access_token);
                System.out.println("oAuth2AccessToken,isExpired()"+oAuth2AccessToken.isExpired());
            }

            if(StringUtils.isBlank(access_token) || null == oAuth2AccessToken || oAuth2AccessToken.isExpired()){
                Set<String> scopes = clientDetails.getScope();
                Set<String> resourceIds = clientDetails.getResourceIds();
                Set<String> registeredRedirectUriSet = clientDetails.getRegisteredRedirectUri();
                String url = "";
                if(null != registeredRedirectUriSet && registeredRedirectUriSet.size()==1){
                    url = (String)registeredRedirectUriSet.toArray()[0];
                }
                boolean approve = clientDetails.isAutoApprove("") == true?true:false;
                Map<String,String> map = new HashMap<>();
                map.put("username",sysUser.getUserName());
                map.put("password",sysUser.getPassword());
                Set<String> responseTypes = new HashSet<>();
                responseTypes.add("json");
                //构建OAuth2请求，用于创建token
                OAuth2Request oAuth2Request = new OAuth2Request(map,
                        sysUser1.getClientId(),
                        null,
                        approve,
                        scopes,
                        resourceIds,
                        url,
                        responseTypes,
                        null
                );
                try {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(sysUser.getUserName(), sysUser.getPassword());
                    Authentication authenticate = authenticationManager.authenticate(authentication);
                    if(authenticate.isAuthenticated()){
                        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request,authentication);
                        oAuth2AccessToken = tokenServices.createAccessToken(oAuth2Authentication);

                    }else {
                        msgMap.put(ResponseData.PASSWORD_ERROR,"密码错误，请重新输入密码");
                        rr.setSuccess(false);
                        rr.setMsgMap(msgMap);
                        rr.setData(sysUser);
                    }
                } catch (AuthenticationException ex) {
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
                }
            }
            response.setHeader("Authorization","Bearer "+oAuth2AccessToken.getValue());
            Cookie cookie = new Cookie("token",oAuth2AccessToken.getValue());
            cookie.setHttpOnly(true);
            cookie.setMaxAge(oAuth2AccessToken.getExpiresIn());
            response.addCookie(cookie);

            //查询用户拥有的所有权限
            userAuthorityList = (List)redisTemplate.opsForValue().get(sysUser1.getUserName());
            if(null == userAuthorityList || userAuthorityList.size() <=0){

                SysUser sysUser2 = sysApiService.getAllApi(sysUser1);
                userAuthorityList = UserUtil.getUserApiAuthoritiesList(sysUser2);
                redisTemplate.opsForValue().set(sysUser2.getUserName(), userAuthorityList, oAuth2AccessToken.getExpiresIn(), TimeUnit.SECONDS);
            }

            Map<String,Object> dataMap = new HashMap<>();

            dataMap.put("loginType","account");//登录类型
            dataMap.put("status","ok");//登录状态
            dataMap.put("currentAuthority", userAuthorityList);//权限
            msgMap.put(ResponseData.LOGIN_CODE,"恭喜您，登录成功");//返回代码
            rr.setData(dataMap);//返回数据
            rr.setMsgMap(msgMap);//返回信息
            rr.setSuccess(true);
            return rr;

        }
        /**
         * Ios登录方式：
         */
        if (sysUser.getLoginType().equals("ios")){

        }
        /**
         * Android登录方式：
         */
        if (sysUser.getLoginType().equals("android")){

        }
        /**
         * 第三方账号：qq 登录
         */
        if (sysUser.getLoginType().equals("qq")){
            System.out.println(sysUser.getLoginType());
        }
        /**
         * 第三方账号：微信 登录
         */
        if (sysUser.getLoginType().equals("wechat")){

        }
        /**
         * 第三方账号：支付宝 登录
         */
        if (sysUser.getLoginType().equals("alipay")){

        }
        /**
         * 第三方账号：新浪微博 登录
         */
        if (sysUser.getLoginType().equals("sinaweibo")){

        }
        /**
         * 短信登录
         */
        if (sysUser.getLoginType().equals("mobile")){

            //校验验证码是否正确
            String code = stringRedisTemplate.opsForValue().get(sysUser.getMobile());
            if(StringUtils.isBlank(code) || !code.equals(sysUser.getCaptcha())){
                //验证码已经
                rr.setSuccess(false);
                msgMap.put(ResponseData.SMS_CODE_ERROR,"短信验证码错误，请重新填写");
            }else{

            }
        }




        msgMap.put(ResponseData.RESPONE_CODE,"登录成功");
        rr.setSuccess(true);
        rr.setMsgMap(msgMap);

        return rr;
    }

    @PostMapping("/login/fail")
    @ResponseBody
    public ResponseResult loginFail(HttpServletRequest request, @RequestBody SysUser sysUser, BindingResult result){
        ResponseResult rr = new ResponseResult();
        Map<String,String> msgMap = new HashMap<String,String>();
        rr.setData(sysUser);
        msgMap.put("","");

        msgMap.put(ResponseData.RESPONE_CODE,"登录成功");
        rr.setSuccess(true);
        rr.setMsgMap(msgMap);

        return rr;
    }
    @PostMapping("/login/out")
    @ResponseBody
    public ResponseResult loginout(HttpServletRequest request, @RequestBody SysUser sysUser, BindingResult result){
        ResponseResult rr = new ResponseResult();
        Map<String,String> msgMap = new HashMap<String,String>();
        rr.setData(sysUser);
        msgMap.put("","");

        msgMap.put(ResponseData.RESPONE_CODE,"登录成功");
        rr.setSuccess(true);
        rr.setMsgMap(msgMap);

        return rr;
    }


}
