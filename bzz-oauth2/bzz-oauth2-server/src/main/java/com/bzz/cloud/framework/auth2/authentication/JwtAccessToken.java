package com.bzz.cloud.framework.auth2.authentication;

import com.bzz.cloud.module.entity.Auth2User;
import com.bzz.common.Utils.JsonUtils;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Map;

/*import com.bzz.cloud.rbac.entity.SysUser;*/

public class JwtAccessToken extends JwtAccessTokenConverter {
    /**
     * 生成token
     * @param accessToken
     * @param authentication
     * @return
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        DefaultOAuth2AccessToken defaultOAuth2AccessToken = new DefaultOAuth2AccessToken(accessToken);

        Auth2User auth2User = (Auth2User)authentication.getPrincipal();
        // 设置额外用户信息
        //SysUser sysUser = ((Auth2User) authentication.getPrincipal()).getSysUser();
        //sysUser.setPassword(null);
        // 将用户信息添加到token额外信息中
        defaultOAuth2AccessToken.getAdditionalInformation().put("bzz_user", auth2User);

        return super.enhance(defaultOAuth2AccessToken, authentication);
    }

    /**
     * 解析token
     * @param value
     * @param map
     * @return
     */
    @Override
    public OAuth2AccessToken extractAccessToken(String value, Map<String, ?> map){
        OAuth2AccessToken oauth2AccessToken = super.extractAccessToken(value, map);
        convertData(oauth2AccessToken, oauth2AccessToken.getAdditionalInformation());
        return oauth2AccessToken;
    }

    private void convertData(OAuth2AccessToken accessToken,  Map<String, ?> map) {
        accessToken.getAdditionalInformation().put("bzz_user",convertUserData(map.get("bzz_user")));

    }

   private Auth2User convertUserData(Object map) {
        String userInfoJson = JsonUtils.object2Json(map);
       Auth2User user = (Auth2User)JsonUtils.json2Object(userInfoJson, Auth2User.class);
        return user;
    }
}
