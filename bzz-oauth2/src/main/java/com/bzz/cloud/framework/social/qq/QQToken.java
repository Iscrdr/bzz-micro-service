package com.bzz.cloud.framework.social.qq;

/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-05-30 03-59
 * @Modified by:
 * @Description:
 */

class QQToken {

    /**
     * token
     */
    private String accessToken;

    /**
     * 有效期
     */
    private int expiresIn;

    /**
     * 刷新时用的 token
     */
    private String refresh_token;

    String getAccessToken() {
        return accessToken;
    }

    void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

}