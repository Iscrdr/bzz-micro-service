package com.bzz.common.Utils;

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
    private String message; //响应信息
    private Object data;    //响应数据
    private String errorCode; //错误代码

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
