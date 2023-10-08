package com.bzz.cloud.core.utils;

import java.io.Serializable;

/**
 * @description：token工具类
 * @author     ：Iscrdr
 * @email      ：624003618@qq.com
 * @date       ：2020-03-05 02:01
 * @modified By：
 * @version: 1.0.0
 */
public class TokenResult implements Serializable {


    /**
     * token
     */
    private String access_token;
    /**
     * token类型
     */
    private String token_type;
    /**
     * 刷新用的token
     */
    private String refresh_token;
    /**
     * 有效时间
     */
    private int expires_in;
    /**
     * 访问范围
     */
    private String scope;
    /**
     * jti
     */
    private String jti;
    private String error;
    private String error_description;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getJti() {
        return jti;
    }

    public void setJti(String jti) {
        this.jti = jti;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError_description() {
        return error_description;
    }

    public void setError_description(String error_description) {
        this.error_description = error_description;
    }
}
