package com.bzz.common.utils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.http.MethodType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Properties;

/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-三月-03 13-12
 * @Modified by:
 * @Description:
 */
public class AliDaYuSMS {

    private static  Logger logger = LoggerFactory.getLogger(AliDaYuSMS.class);

    public static  String sendSMS(String mobile,String code) throws ClientException, IOException {


        InputStream resource = AliDaYuSMS.class.getResource("/common.properties").openStream();
        Properties prop = new Properties();
        prop.load(resource);
        String access_key_id = prop.getProperty("ACCESS_KEY_ID");
        String access_secret = prop.getProperty("ACCESS_SECRET");


        String s1 = Base64Utils.decoderString(access_key_id,"UTF-8");
        String s2 = Base64Utils.decoderString(access_secret,"UTF-8");

        DefaultProfile profile = DefaultProfile.getProfile("default", s1, s2);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("PhoneNumbers", mobile);
        request.putQueryParameter("SignName", "注册验证");
        request.putQueryParameter("TemplateCode", "SMS_5006841");
        request.putQueryParameter("TemplateParam", "{\"code\":\""+code+"\",\"product\":\"BZZ服务平台\"}");
        CommonResponse response = client.getCommonResponse(request);
        String result = response.getData();
        System.out.println("==================");
        //打印返回数据
        logger.info(result);
        return result;
    }

    public static void main(String[] args) throws IOException, ClientException {
        String s = sendSMS("15501236689", "231778");
        System.out.println(s);
    }
}
