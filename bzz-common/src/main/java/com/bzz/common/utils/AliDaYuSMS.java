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

/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-三月-03 13-12
 * @Modified by:
 * @Description:
 */
public class AliDaYuSMS {

    private  Logger logger = LoggerFactory.getLogger(AliDaYuSMS.class);

    public final static String ACCESS_KEY_ID = "LTAIbaQCshoFTh0b";
    public final static String ACCESS_SECRET = "d9l41M8fPZV5pV3WjLaGgxZBdISthv";


    public  String sendSMS(String mobile,String code) throws ClientException {
        DefaultProfile profile = DefaultProfile.getProfile("default", AliDaYuSMS.ACCESS_KEY_ID, AliDaYuSMS.ACCESS_SECRET);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
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
        logger.info(result);//打印返回数据
        return result;
    }
}
