package com.bzz.cloud.framework.qqoauth;

/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-05-22 21-14
 * @Modified by:
 * @Description:
 */
public class QQApi {

    public final static String CODE = "code";

    /**
     * 获取 Token 的 API
     */
    public final static String accessTokenUri = "https://graph.qq.com/oauth2.0/token";

    /**
     * grant_type 由腾讯提供
     */
    public final static String grantType = "authorization_code";

    /**
     * client_id 由腾讯提供
     */
    public  static String clientId = "100687189";

    /**
     * client_secret 由腾讯提供
     */
    public  static String clientSecret = "aa28fa1f6ca61c2f8a4a25df78fe2ca9";

    /**
     * redirect_uri 腾讯回调地址
     */
    public final static String redirectUri = "http://www.ictgu.cn/login/qq";

    /**
     * 获取 OpenID 的 API 地址
     */
    public final static String openIdUri = "https://graph.qq.com/oauth2.0/me?access_token=";

    /**
     * 获取 token 的地址拼接
     */
    public final static String TOKEN_ACCESS_API = "%s?grant_type=%s&client_id=%s&client_secret=%s&code=%s&redirect_uri=%s";

}
