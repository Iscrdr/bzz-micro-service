package com.bzz.cloud.oauth.web;

import com.bzz.cloud.annotation.FieldsExclude;
import com.bzz.cloud.oauth.entity.Auth2User;
import com.bzz.cloud.rbac.entity.SysUser;
import com.bzz.cloud.utils.HttpRequestUitls;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@RestController
public class ClientController {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private TokenStore tokenStore;


    @GetMapping("/getClientsById")
    public ClientDetails getClient(String clientId){
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
        return clientDetails;
    }

    @GetMapping("/getPrincipal")
    public String getClient(HttpServletRequest request, HttpServletResponse response){
        String token = HttpRequestUitls.getToken(request);
        OAuth2Authentication oAuth2Authentication = tokenStore.readAuthentication(token);
        Auth2User auth2User = (Auth2User)oAuth2Authentication.getPrincipal();
        return auth2User.getUsername();


    }
}
