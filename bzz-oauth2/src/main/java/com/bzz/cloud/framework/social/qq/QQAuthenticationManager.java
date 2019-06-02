package com.bzz.cloud.framework.social.qq;

import com.bzz.cloud.rbac.entity.QQUser;
import com.bzz.common.Utils.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-05-30 04-00
 * @Modified by:
 * @Description:
 */

public class QQAuthenticationManager implements AuthenticationManager {
    private static final List<GrantedAuthority> AUTHORITIES = new ArrayList<>();

    /**
     * 获取 QQ 登录信息的 API 地址
     */
    private final static String userInfoUri = "https://graph.qq.com/user/get_user_info";

    /**
     * 获取 QQ 用户信息的地址拼接
     */
    private final static String USER_INFO_API = "%s?access_token=%s&oauth_consumer_key=%s&openid=%s";

    static {
        AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        if (auth.getName() != null && auth.getCredentials() != null) {
            QQAuth2User qqAuth2User = null;
            String openId = (String) auth.getCredentials();
            try {
                QQUser  user = getUserInfo(auth.getName(), openId);
                qqAuth2User = new QQAuth2User(openId,auth.getName(),AUTHORITIES,user);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new UsernamePasswordAuthenticationToken(qqAuth2User,auth.getName(), AUTHORITIES);
        }
        throw new BadCredentialsException("Bad Credentials");
    }

    private QQUser getUserInfo(String accessToken, String openId) throws Exception {
        String url = String.format(USER_INFO_API, userInfoUri, accessToken, QQApi.clientId, openId);
        Document document;
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new BadCredentialsException("Bad Credentials!");
        }
        String resultText = document.text();
        System.out.println("qq用户信息："+resultText);


        QQUser qqUser = (QQUser) JsonUtils.json2Object(resultText, QQUser.class);

        System.out.println("转换后的qq用户信息："+qqUser.toString());
        return qqUser;
    }
}
