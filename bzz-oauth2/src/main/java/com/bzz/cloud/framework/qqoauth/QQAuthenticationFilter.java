package com.bzz.cloud.framework.qqoauth;

import com.bzz.common.Utils.JsonUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-05-22 21-13
 * @Modified by:
 * @Description:
 */
public class QQAuthenticationFilter extends AbstractAuthenticationProcessingFilter {



    public QQAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl, "GET"));
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String code = request.getParameter(QQApi.CODE);
        System.out.println("Code : " + code);
        String tokenAccessApi = String.format(QQApi.TOKEN_ACCESS_API, QQApi.accessTokenUri, QQApi.grantType, QQApi.clientId, QQApi.clientSecret, code, QQApi.redirectUri);
        QQToken qqToken = this.getToken(tokenAccessApi);
        System.out.println(JsonUtils.object2Json(qqToken));
        if (qqToken != null){
            String openId = getOpenId(qqToken.getAccessToken());
            System.out.println(openId);
            if (openId != null){
                // 生成验证 authenticationToken
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(qqToken.getAccessToken(), openId);
                // 返回验证结果
                return this.getAuthenticationManager().authenticate(authRequest);
            }
        }
        return null;
    }

    private QQToken getToken(String tokenAccessApi) throws IOException{
        Document document = Jsoup.connect(tokenAccessApi).get();
        String tokenResult = document.text();
        String[] results = tokenResult.split("&");
        if (results.length == 3){
            QQToken qqToken = new QQToken();
            String accessToken = results[0].replace("access_token=", "");
            int expiresIn = Integer.valueOf(results[1].replace("expires_in=", ""));
            String refreshToken = results[2].replace("refresh_token=", "");
            qqToken.setAccessToken(accessToken);
            qqToken.setExpiresIn(expiresIn);
            qqToken.setRefresh_token(refreshToken);
            return qqToken;
        }
        return null;
    }

    private String getOpenId(String accessToken) throws IOException{
        String url = QQApi.openIdUri + accessToken;
        Document document = Jsoup.connect(url).get();
        String resultText = document.text();
        Matcher matcher = Pattern.compile("\"openid\":\"(.*?)\"").matcher(resultText);
        if (matcher.find()){
            return matcher.group(1);
        }
        return null;
    }




}
