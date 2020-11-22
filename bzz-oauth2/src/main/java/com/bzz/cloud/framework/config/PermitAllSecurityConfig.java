package com.bzz.cloud.framework.config;

import com.bzz.cloud.framework.filter.PermitAllAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.stereotype.Component;
import javax.servlet.Filter;
/**
 * @author ：Iscrdr
 * @description：解决PermitAll问题配置
 * @email ：624003618@qq.com
 * @date ：2020-11-21 14:46
 * @modified By：
 * @version: 1.0.0
 */
@Component("permitAllSecurityConfig")
public class PermitAllSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private PermitAllAuthenticationFilter permitAuthenticationFilter;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(permitAuthenticationFilter, OAuth2AuthenticationProcessingFilter.class);
    }
}
