package com.bzz.common.Utils;

/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-二月-23 19-00
 * @Modified by:
 * @Description:
 */

public class MsgData {
    Integer code;//错误代码
    Object msg;//信息

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }
}
