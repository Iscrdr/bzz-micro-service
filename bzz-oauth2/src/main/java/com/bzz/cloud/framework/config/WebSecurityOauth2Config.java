package com.bzz.cloud.framework.config;

import com.bzz.cloud.framework.filter.JsonPayloadUserNamePasswordAuthenticationFilter;
import com.bzz.cloud.framework.handler.CustomAuthenticationFailHandler;
import com.bzz.cloud.framework.handler.CustomAuthenticationSuccessHandler;

import com.bzz.cloud.framework.social.qq.QQAuthenticationFilter;
import com.bzz.cloud.framework.social.qq.QQAuthenticationManager;
import com.bzz.cloud.framework.social.qq.QQAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class WebSecurityOauth2Config extends WebSecurityConfigurerAdapter {


    @Autowired
    private Oauth2AuthenticationProvider oauth2AuthenticationProvider;

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    @Autowired
    private CustomAuthenticationFailHandler customAuthenticationFailHandler;

    @Autowired
    private QQAuthenticationSuccessHandler qqAuthenticationSuccessHandler;


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }



    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(oauth2AuthenticationProvider);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/bzzoauth/login/**").permitAll()
                .antMatchers("/login/**").permitAll()//登用户录url不拦截
                //.antMatchers("/qq/login/**").permitAll()//登用户录url不拦截
                .antMatchers("/bzzoauth/social/qq").permitAll()
                .antMatchers("/social/qq").permitAll()
                /*.antMatchers("/login/**").permitAll()//登用户录url不拦截
               .antMatchers("/qq/login/**").permitAll()//登用户录url不拦截
               .antMatchers("/oauth/**").permitAll()*///oauth认证不拦截*/
                .anyRequest().authenticated()
                .and()

              .addFilterAt(jsonPayloadUserNamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
                http.addFilterAt(qqAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    /**
     * 注册自定义的UsernamePasswordAuthenticationFilter
     */

    @Bean
    public JsonPayloadUserNamePasswordAuthenticationFilter jsonPayloadUserNamePasswordAuthenticationFilter() throws Exception {
        JsonPayloadUserNamePasswordAuthenticationFilter filter = new JsonPayloadUserNamePasswordAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(customAuthenticationFailHandler);
        filter.setFilterProcessesUrl("/login/**");
        filter.setUsernameParameter("userName");
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }
    /**
     * 自定义 QQ登录 过滤器
     */
    @Bean
    public QQAuthenticationFilter qqAuthenticationFilter() throws Exception {
        QQAuthenticationFilter authenticationFilter = new QQAuthenticationFilter("/social/qq/**");
        authenticationFilter.setAuthenticationSuccessHandler(qqAuthenticationSuccessHandler);
        authenticationFilter.setAuthenticationFailureHandler(customAuthenticationFailHandler);
        authenticationFilter.setAuthenticationManager(new QQAuthenticationManager());

        return authenticationFilter;
    }



}
