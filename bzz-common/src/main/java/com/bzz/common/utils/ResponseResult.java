package com.bzz.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Desc: HTTP响应结果
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-03-01 22:21
 * @Modified by:
 * @Description:
 */
public class ResponseResult {
    /**
     * 是否成功,
     */
    private boolean success;

    /**
     * 响应数据
     */
    private Object data;
    /**
     * key为代码，value为错误信息
     */
    private Map<String,String> msgMap = new HashMap<String,String>();


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Map<String, String> getMsgMap() {
        return msgMap;
    }
    public void setMsgMap(Map<String, String> msgMap) {
        this.msgMap = msgMap;
    }
}
