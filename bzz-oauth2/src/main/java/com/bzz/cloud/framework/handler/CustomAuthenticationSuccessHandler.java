package com.bzz.cloud.framework.handler;

import com.bzz.cloud.oauth.entity.Auth2User;
import com.bzz.cloud.rbac.entity.SysUser;
import com.bzz.common.Utils.DateUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-05-27 01-51
 * @Modified by:
 * @Description:
 */
@Component
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    @Autowired
    private ClientDetailsService clientDetailsService;
    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth)  {
        ObjectMapper objectMapper = new ObjectMapper();
        try {

            String username = auth.getName();
            logger.info("账号：{}，在{}登录成功",username, DateUtils.getCurrentTime(new Date(),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));

            Auth2User details = (Auth2User)auth.getPrincipal();


            String clientId = details.getSysUser().getClientId();
            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);




            Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
            List<String> authoritys = new ArrayList<>();

            String authorityStr = "";
            for(Iterator<? extends GrantedAuthority> iterator = authorities.iterator();iterator.hasNext();){
                GrantedAuthority next = iterator.next();
                String authority = next.getAuthority();
                authoritys.add(authority);
                authorityStr = authorityStr +authority+",";
            }
            authorityStr = authorityStr.substring(0,authorityStr.length()-1);
            Map<String,Object> dataMap = new HashMap<>();
            dataMap.put("currentAuthority",authoritys);//权限
            dataMap.put("userName", username);//权限
            dataMap.put("loginType", request.getAttribute("loginType").toString());//登录类型
            dataMap.put("status","ok");//登录状态


            TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP, clientDetails.getClientId(), clientDetails.getScope(), authorityStr);
            OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
            OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, auth);
            OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);

            response.setHeader("Authorization","Bearer "+token.getValue());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(dataMap));

        } catch (IOException e) {
            e.printStackTrace();
        }
        //返回view
        //super.onAuthenticationSuccess(request,response,auth);
    }

}
