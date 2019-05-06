package com.bzz.common.Utils;

/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-二月-23 19-02
 * @Modified by:
 * @Description:
 */
public class ResponseData {

    /**
     * 注册相关状态码
     */
    public final static String  RESPONE_CODE = "2000"; //返回2000代表正确信息
    public final static String  RESPONE_CODE_ERROR = "2001"; //返回2000代表正确信息
    public final static String  MOBILE_CODE_ERROR = "2002"; //手机号不正确,或者已经被注册
    public final static String  EMAIL_CODE_ERROR = "2003"; //邮箱不正确,或者已经被注册
    public final static String  NAME_CODE_ERROR = "2004"; //登录名不正确,或者已经被注册
    public final static String  REGISTER_CODE = "2005"; //注册成功
    public final static String  SMS_CODE_ERROR = "2006"; //短信验证码错误


    /**
     * 登录相关状态码
     */
    public final static String  LOGIN_CODE = "3000"; //登录成功
    public final static String  LOGIN_ERROR_CODE = "3001"; //登录失败
    public final static String  USERNAME_NOT_FOUND = "3002"; //账号没有找到
    public final static String  PASSWORD_ERROR = "3004"; //密码不正确
    public final static String  USERNAME_ACCOUNT_EXPIRED = "3005"; //账号过期,过期无法验证
    public final static String  USERNAME_ACCOUNT_LOCKED = "3006"; //账号被锁定
    public final static String  USERNAME_CREDENTIALS_EXPIRED = "3007"; //密码或者凭证过期
    public final static String  USERNAME_ENABLED = "3008"; //账号禁用



}
