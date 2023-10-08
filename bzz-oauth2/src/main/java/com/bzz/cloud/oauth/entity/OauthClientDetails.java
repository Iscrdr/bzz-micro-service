package com.bzz.cloud.oauth.entity;

import com.bzz.cloud.core.entity.BaseEntity;
import com.bzz.cloud.rbac.entity.SysUser;
import lombok.*;

import java.util.Date;

/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-04-05 11-47
 * @Modified by:
 * @Description:
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OauthClientDetails extends SysUser {
    private String clientId;
    private String resourceIds;
    private String clientSecret;
    private String scope;
    private String authorizedGrantTypes;
    private String webServerRedirectUrl;
    private String authorities;
    private String accessTokenValidity;
    private String refreshTokenValidity;
    private String additionalInformation;
    private Date createTime;
    private boolean archived;
    private boolean trusted;
    private String autoapprove;

}
