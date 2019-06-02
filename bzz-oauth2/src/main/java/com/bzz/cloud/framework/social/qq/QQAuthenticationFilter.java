package com.bzz.cloud.framework.social.qq;

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
 * @Date: 2019-05-30 03-54
 * @Modified by:
 * @Description:
 */
public class QQAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private final static String CODE = "code";

    /**
     * 获取 Token 的 API
     */
    private final static String accessTokenUri = "https://graph.qq.com/oauth2.0/token";

    /**
     * grant_type 由腾讯提供
     */
    private final static String grantType = "authorization_code";



    /**
     * redirect_uri 腾讯回调地址
     */
    private final static String redirectUri = "http://2j499609f3.zicp.vip/api/bzzoauth/social/qq";

    /**
     * 获取 OpenID 的 API 地址
     */
    private final static String openIdUri = "https://graph.qq.com/oauth2.0/me?access_token=";

    /**
     * 获取 token 的地址拼接
     */
    private final static String TOKEN_ACCESS_API = "%s?grant_type=%s&client_id=%s&client_secret=%s&code=%s&redirect_uri=%s";

    public QQAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl, "GET"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String code = request.getParameter(CODE);
        String tokenAccessApi = String.format(TOKEN_ACCESS_API, accessTokenUri, grantType, QQApi.clientId, QQApi.clientSecret, code, redirectUri);
        QQToken qqToken = this.getToken(tokenAccessApi);
        if (qqToken != null) {
            String openId = getOpenId(qqToken.getAccessToken());
            if (openId != null) {
                // 生成验证 authenticationToken
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(qqToken.getAccessToken(), openId);
                authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
                // 返回验证结果
                Authentication authenticate = this.getAuthenticationManager().authenticate(authRequest);
                return authenticate;
            }
        }
        return null;
    }

    private QQToken getToken(String tokenAccessApi) throws IOException {
        Document document = Jsoup.connect(tokenAccessApi).get();
        String tokenResult = document.text();
        String[] results = tokenResult.split("&");
        if (results.length == 3) {
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

    private String getOpenId(String accessToken) throws IOException {
        String url = openIdUri + accessToken;
        Document document = Jsoup.connect(url).get();
        String resultText = document.text();
        Matcher matcher = Pattern.compile("\"openid\":\"(.*?)\"").matcher(resultText);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}

