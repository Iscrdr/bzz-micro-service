package com.bzz.common.Utils;

/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-二月-23 19-02
 * @Modified by:
 * @Description:
 */
public class ResponseData {
    public final static Integer  RESPONE_CODE = 2000; //返回2000代表正确信息
    public final static Integer  RESPONE_CODE_ERROR = 2001; //返回2000代表正确信息
    public final static String  MOBILE_CODE_ERROR = "2002"; //手机号不正确,或者已经被注册
    public final static String  EMAIL_CODE_ERROR = "2003"; //邮箱不正确,或者已经被注册
    public final static String  NAME_CODE_ERROR = "2004"; //登录名不正确,或者已经被注册
    public final static String  REGISTER_CODE = "2005"; //注册成功
    public final static String  SMS_CODE_ERROR = "2006"; //短信验证码错误
}
