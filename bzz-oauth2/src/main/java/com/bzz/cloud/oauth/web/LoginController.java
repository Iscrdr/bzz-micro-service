package com.bzz.cloud.oauth.web;



import com.bzz.cloud.QQUtils;
import com.bzz.cloud.framework.social.qq.QQApi;
import com.bzz.cloud.framework.social.qq.QQToken;
import com.bzz.cloud.rbac.entity.QQUser;
import com.bzz.cloud.rbac.entity.SysMenu;
import com.bzz.cloud.rbac.entity.SysRole;
import com.bzz.cloud.rbac.entity.SysUser;
import com.bzz.cloud.rbac.service.SysMenuService;
import com.bzz.cloud.rbac.service.SysUserService;
import com.bzz.cloud.rbac.utils.UserUtil;
import com.bzz.common.utils.JsonUtils;
import com.bzz.common.utils.ResponseData;
import com.bzz.common.utils.ResponseResult;
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
    private SysMenuService sysMenuService;
    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthenticationManager oAuthenticationManager;


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(value = "/qq/login",method = {RequestMethod.GET})
    public ResponseResult qqLogin(HttpServletRequest request, HttpServletResponse response){
        ResponseResult rr = new ResponseResult();
        Map<String,String> msgMap = new HashMap<>();
        String code = request.getParameter("");
        if(StringUtils.isBlank(code)){
            msgMap.put(ResponseData.USERNAME_NOT_FOUND,"登录方式不支持");
            rr.setMsgMap(msgMap);
            rr.setData(null);
            rr.setSuccess(false);
            return  rr;
        }

        //获取qq:token
        String tokenAccessApi = String.format(QQApi.TOKEN_ACCESS_API, QQApi.accessTokenUri, QQApi.grantType, QQApi.clientId, QQApi.clientSecret, code, QQApi.redirectUri);
        Map<String,Object> dataMap = new HashMap<>();
        try {
            QQToken qqToken = QQUtils.getToken(tokenAccessApi);
            String openId = QQUtils.getOpenId(qqToken.getAccessToken());
            QQUser userInfo = QQUtils.getUserInfo(qqToken.getAccessToken(), openId);
            //保存用户信息

            //qq登录，给个默认的client:unity_client_qq，此client相当于游客
            ClientDetails clientDetails = clientDetailsService.loadClientByClientId("unity_client_qq");
            Map<String,String> map = new HashMap<>();
            map.put("username",openId);
            map.put("password",qqToken.getAccessToken());

            //构建OAuth2请求，用于创建token
            Set<String> scopes = clientDetails.getScope();
            Set<String> resourceIds = clientDetails.getResourceIds();
            Set<String> registeredRedirectUriSet = clientDetails.getRegisteredRedirectUri();
            String url = "";
            if(null != registeredRedirectUriSet && registeredRedirectUriSet.size()==1){
                url = (String)registeredRedirectUriSet.toArray()[0];
            }
            boolean approve = clientDetails.isAutoApprove("") == true?true:false;

            Set<String> responseTypes = new HashSet<>();
            responseTypes.add("json");
            //构建OAuth2请求，用于创建token
            OAuth2Request oAuth2Request = new OAuth2Request(map,
                    clientDetails.getClientId(),
                    null,
                    approve,
                    scopes,
                    resourceIds,
                    url,
                    responseTypes,
                    null);

            //生成token
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(qqToken.getAccessToken(), openId);

            //SocialAuthenticationToken socialAuthenticationToken = new SocialAuthenticationToken();

            Authentication authenticate = oAuthenticationManager.authenticate(authentication);
            OAuth2AccessToken oAuth2AccessToken = null;
            if(authenticate.isAuthenticated()){
                OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request,authentication);
                oAuth2AccessToken = tokenServices.createAccessToken(oAuth2Authentication);
            }

            response.setHeader("Authorization","Bearer "+oAuth2AccessToken.getValue());
            Cookie cookie = new Cookie("token",oAuth2AccessToken.getValue());
            cookie.setHttpOnly(true);
            cookie.setMaxAge(oAuth2AccessToken.getExpiresIn());
            response.addCookie(cookie);

            SysUser sysUser = new SysUser();
            sysUser.setQqUser(userInfo);

            //查询该qq用户是否已经注册，或者绑定已有的用户
            SysUser user = sysUserService.getUserByLoginName(sysUser);
            List<String> userAuthorityList = null;
            if(null != user || StringUtils.isNotBlank(user.getUserName()) ){
                //查询权限信息
                userAuthorityList = (List<String>)redisTemplate.opsForValue().get(user.getUserName());
                if(null == userAuthorityList || userAuthorityList.size() <=0){

                    SysUser sysUser2 = sysMenuService.getAllMenu(user);
                    userAuthorityList = UserUtil.getUserApiAuthoritiesList(sysUser2);
                    redisTemplate.opsForValue().set(sysUser2.getUserName(), userAuthorityList, oAuth2AccessToken.getExpiresIn(), TimeUnit.SECONDS);
                }
            }
            if(userAuthorityList == null){
                userAuthorityList = new ArrayList<>();
                //游客
                userAuthorityList.add("guest") ;
            }

            //登录类型
            dataMap.put("loginType","account");
            //登录状态
            dataMap.put("status","ok");
            //权限
            dataMap.put("currentAuthority", null);
            //返回代码
            msgMap.put(ResponseData.LOGIN_CODE,"恭喜您，登录成功");
            //返回数据
            rr.setData(dataMap);
            //返回信息
            rr.setMsgMap(msgMap);
            rr.setSuccess(true);
            return rr;
        }catch (Exception e) {
            //登录类型
            dataMap.put("loginType","qq");
            //登录状态
            dataMap.put("status","fail");
            //权限
            dataMap.put("currentAuthority", null);
            //返回代码
            msgMap.put(ResponseData.RESPONE_CODE_ERROR,"使用qq失败，请改用其它方式登录");
            //返回数据
            rr.setData(dataMap);
            //返回信息
            rr.setMsgMap(msgMap);
            rr.setSuccess(false);
            e.printStackTrace();
            return rr;
        }

    }


    @RequestMapping(value = "/login",method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseResult login(HttpServletRequest request, HttpServletResponse response,@RequestBody(required=false) SysUser sysUser){
        ResponseResult rr = new ResponseResult();
        Map<String,String> msgMap = new HashMap<>();

        if(null == sysUser || StringUtils.isBlank(sysUser.getLoginType())){
            msgMap.put(ResponseData.USERNAME_NOT_FOUND,"登录方式不支持");
            rr.setMsgMap(msgMap);
            rr.setData(sysUser);
            rr.setSuccess(false);
            return  rr;
        }
        String loginType = sysUser.getLoginType();

        ClientDetails clientDetails = null;
        SysUser sysUser1 = null;
        if (loginType.equals(sysUser.getLoginType())) {
            if(StringUtils.isBlank(sysUser.getUserName())){
                msgMap.put(ResponseData.USERNAME_NOT_FOUND,"登录正好不能为空");
                rr.setMsgMap(msgMap);
                rr.setData(sysUser);
                rr.setSuccess(false);
                return  rr;
            }
            if (StringUtils.isBlank(sysUser.getPassword().trim())){
                msgMap.put(ResponseData.PASSWORD_ERROR,"登录失败,密码不能为空");
                rr.setMsgMap(msgMap);
                rr.setData(sysUser);
                rr.setSuccess(false);
                return  rr;
            }
            sysUser1 = sysUserService.getUserByLoginName(sysUser);
            if (StringUtils.isBlank(sysUser1.getUserName())){
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
            clientDetails = clientDetailsService.loadClientByClientId(sysUser1.getClientId());
            if(clientDetails == null || StringUtils.isBlank(clientDetails.getClientId()) ){
                msgMap.put(ResponseData.USER_ACCOUNT_NOUNAUTHORIZED,"登录失败，账号没有授权");
                rr.setSuccess(false);
                rr.setMsgMap(msgMap);
                rr.setData(sysUser);
                return  rr;
            }
        }
        /*
         * 手机号登录
         */
        if (loginType.equals(sysUser.getLoginType())){
            //校验验证码是否正确
            String code = stringRedisTemplate.opsForValue().get(sysUser.getMobile());
            if(StringUtils.isBlank(code) || !code.equals(sysUser.getCaptcha())){
                //验证码已经
                rr.setSuccess(false);
                msgMap.put(ResponseData.SMS_CODE_ERROR,"短信验证码错误，请重新填写");
                rr.setMsgMap(msgMap);
                rr.setData(sysUser);
                return  rr;
            }
        }


        /*
         * 校验密码是否正确
         */
        try {
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(sysUser.getUserName(), sysUser.getPassword());
            Authentication authenticate = oAuthenticationManager.authenticate(authentication);
            if (authenticate.isAuthenticated()) {
                //密码正确,生成token

                Set<String> scopes = clientDetails.getScope();
                Set<String> resourceIds = clientDetails.getResourceIds();
                Set<String> registeredRedirectUriSet = clientDetails.getRegisteredRedirectUri();
                String url = "";
                if (null != registeredRedirectUriSet && registeredRedirectUriSet.size() == 1) {
                    url = (String) registeredRedirectUriSet.toArray()[0];
                }
                boolean approve = clientDetails.isAutoApprove("") == true ? true : false;
                Map<String, String> map = new HashMap<>();
                map.put("username", sysUser.getUserName());
                map.put("password", sysUser.getPassword());
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

                OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
                OAuth2AccessToken oAuth2AccessToken = tokenServices.createAccessToken(oAuth2Authentication);
                response.setHeader("Authorization","Bearer "+oAuth2AccessToken.getValue());
                Cookie cookie = new Cookie("token",oAuth2AccessToken.getValue());
                cookie.setHttpOnly(true);
                cookie.setMaxAge(oAuth2AccessToken.getExpiresIn());
                response.addCookie(cookie);

                //查询用户拥有的所有权限
                List userAuthorityList  = (List)redisTemplate.opsForValue().get(sysUser1.getUserName());
                if(null == userAuthorityList || userAuthorityList.size() <=0){
                    SysUser sysUser2 = sysMenuService.getAllMenu(sysUser1);
                    userAuthorityList = UserUtil.getUserApiAuthoritiesList(sysUser2);
                    redisTemplate.opsForValue().set(sysUser2.getUserName(), userAuthorityList, oAuth2AccessToken.getExpiresIn(), TimeUnit.SECONDS);
                }

                Map<String,Object> dataMap = new HashMap<>();
                //登录类型
                dataMap.put("loginType","account");
                //登录状态
                dataMap.put("status","ok");
                //权限
                dataMap.put("currentAuthority", userAuthorityList);
                //返回代码
                msgMap.put(ResponseData.LOGIN_CODE,"恭喜您，登录成功");
                //返回数据
                rr.setData(dataMap);
                //返回信息
                rr.setMsgMap(msgMap);
                rr.setSuccess(true);
            } else {
                msgMap.put(ResponseData.PASSWORD_ERROR, "密码错误，请重新输入密码");
                rr.setSuccess(false);
                rr.setMsgMap(msgMap);
                rr.setData(sysUser);
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
        }
        return rr;
    }
    @PostMapping("/login/fail")
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

    @PostMapping("/getMenuData")
    public String getMenuData(HttpServletRequest request, @RequestBody SysUser sysUser, BindingResult result){
        sysUser.setUserName("admin");
        sysUser = sysMenuService.getAllMenu(sysUser);
        List<SysRole> sysRoleList = sysUser.getSysRoleList();
        List<SysMenu> menuData = new ArrayList<>();
        for(SysRole role:sysRoleList){
            List<SysMenu> sysMenuList = role.getSysMenuList();
            for(SysMenu sysMenu: sysMenuList){
                if(StringUtils.isNotBlank(sysMenu.getAuthorityStr())){
                    sysMenu.setAuthority(role.getRoleType().toUpperCase()+"_"+sysMenu.getAuthorityStr());
                }
            }
            menuData.addAll(sysMenuList);
        }
        //默认根节点
        List<SysMenu> menuList = treeSysMenuList(menuData, 1L);

        JsonUtils.filter(SysMenu.class,null,"delFlag,version,createTime,updateTime,page,menu,component,redirect,sort");
        String menuJson = JsonUtils.object2Json(menuList);
        System.out.println(menuJson);

        return menuJson;
    }
    /**
     * 获取某个父节点下面的所有子节点
     * @param sysMenuList
     * @param rootId
     * @return
     */
    private   List<SysMenu> treeSysMenuList( List<SysMenu> sysMenuList, long rootId){
        List<SysMenu> childMenu = new ArrayList<>();
        for(SysMenu sysMenu: sysMenuList){
            if(sysMenu == null || sysMenu.getId() == null){
                continue;
            }
            SysMenu parentMenu = sysMenu.getParentMenu();

            //遍历出父id等于参数的id，add进子节点集合
            if(parentMenu!=null && parentMenu.getId()!=null && parentMenu.getId().equals(rootId)){
                childMenu.add(sysMenu);
            }
        }
        // 递归子菜单
        for (SysMenu menu : childMenu) {
            menu.setChildren(treeSysMenuList(sysMenuList,menu.getId()));
        } // 递归退出条件
        if (childMenu.size() == 0) {
            return null;
        }
        return childMenu;
    }

}
