package com.bzz.cloud.web;

import com.bzz.cloud.services.OauthTokenService;
import com.bzz.common.Utils.JsonUtils;
import com.bzz.common.Utils.MsgData;
import com.bzz.common.Utils.ResponeData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-二月-23 18-21
 * @Modified by:
 * @Description:
 */
@RestController
public class TokenController {
    @Autowired
    private OauthTokenService oauthTokenService;

    @PostMapping("/oauth/token")
    public String getToken(@RequestParam(value = "client_id")String client_id,
                           @RequestParam(value = "client_secret")String client_secret,
                           @RequestParam(value = "grant_type")String grant_type,
                           @RequestParam(value = "username")String username,
                           @RequestParam(value = "password")String password,
                           @RequestParam(value = "refresh_token",required = false)String refresh_token){
        String token = oauthTokenService.getToken(client_id, client_secret, grant_type, username, password, refresh_token);
        return token;
    }

    /**
     * 检查token
     * @param token
     * @return
     */
    @PostMapping("/oauth/check_token")
    public MsgData checkToken(String token,String username,String client_id){
        String token1 = oauthTokenService.checkToken(token);


        String username1 = JsonUtils.getFeildFromJson(username, token1);
        String client_id_1 = JsonUtils.getFeildFromJson(client_id, token1);
        boolean flag = false;
        if(( StringUtils.isNotBlank(username1) && username1.equals(username) )
                && (StringUtils.isNotBlank(client_id_1) && client_id_1.equals(client_id))

        ){
            flag = true;
        }
        MsgData msgData = new MsgData();
        if(flag){
            msgData.setCode(ResponeData.RESPONE_CODE);
            msgData.setMsg("token验证正确");
        }else {
            msgData.setCode(ResponeData.RESPONE_CODE_ERROR);
            msgData.setMsg("token验证错误,请重新申请token");
        }

        return msgData;
    }
}
