package com.bzz.cloud;


import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.bzz.common.Utils.AliDaYuSMS;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-三月-03 13-29
 * @Modified by:
 * @Description:
 */
public class SMSTest {
    public static void main(String[] args) throws Exception {

        //发送验证码
        try {
            AliDaYuSMS aliDaYuSMS = new AliDaYuSMS();
            String result = aliDaYuSMS.sendSMS("15501236689", "889911");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
