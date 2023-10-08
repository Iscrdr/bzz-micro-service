package com.bzz.cloud.framework.social.qq;

import com.bzz.cloud.rbac.entity.QQUser;
import com.bzz.common.utils.DateUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-05-30 04-49
 * @Modified by:
 * @Description:
 */
@Slf4j
@Component
public class QQAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
/*
    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;
    @Autowired
    private ClientDetailsService clientDetailsService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth)
            throws IOException, ServletException {


        QQAuth2User qqAuth2User = (QQAuth2User)auth.getPrincipal();
        QQUser qqUser = qqAuth2User.getQqUser();
        String username = qqUser.getNickname();

        //QQAuth2User auth2User = new QQAuth2User("bzz_qq",auth.getCredentials().toString(),auth.getAuthorities());


        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("currentAuthority","admin");//权限
        dataMap.put("userName", username);//权限
        dataMap.put("loginType", "qq");//登录类型
        dataMap.put("status","ok");//登录状态

        log.info("账号：{}，在{}登录成功",username, DateUtils.getCurrentTime(new Date(),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));

        ClientDetails clientDetails = clientDetailsService.loadClientByClientId("unity_client_1");

        TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP, clientDetails.getClientId(), clientDetails.getScope(), "admin");
        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, auth);
        OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);

        response.setHeader("Authorization","Bearer "+token.getValue());
        response.setContentType("application/json;charset=UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write( objectMapper.writeValueAsString(dataMap));


    }*/

}
