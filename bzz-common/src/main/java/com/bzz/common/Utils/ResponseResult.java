package com.bzz.common.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Desc: HTTP响应结果
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-03-01 22:21
 * @Modified by:
 * @Description:
 */
public class ResponseResult {
    private boolean success; //是否成功

    private Object data;    //响应数据

    private Map<String,String> msgMap = new HashMap<String,String>();//key为代码，value为错误信息


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
