package com.bzz.common.utils;

/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-二月-23 19-00
 * @Modified by:
 * @Description:
 */

public class MsgData {
    /**
     *  响应代码
     */
    String code;
    /**
     *  响应信息
     */
    Object msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }
}
