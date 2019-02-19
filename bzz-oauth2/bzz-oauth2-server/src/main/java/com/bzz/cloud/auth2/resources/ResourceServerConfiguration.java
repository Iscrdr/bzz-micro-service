package com.bzz.cloud.auth2.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

/**
 * 资源服务
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Value("${resource.id:spring-boot-application}")
    private String resourceId;


    @Autowired
    private DataSource dataSource;


    /**
     * TokenStore:使用数据库存储token，jdbc
     */
    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }
    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    @Bean
    public ApprovalStore approvalStore(){
        return new JdbcApprovalStore(dataSource);
    }


    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("bzzoauth");
        resources.tokenServices(defaultTokenServices());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated();
        /*http.requestMatcher(new OAuthRequestedMatcher())
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .requestMatchers()
                .antMatchers("/**")
                .and()
                .authorizeRequests()
                .filterSecurityInterceptorOncePerRequest(true)
                .antMatchers("/oauth/**").permitAll()
                .and().headers().frameOptions().disable();
*/
    }

    /**
     * 创建一个默认的资源服务token
     *
     * @return
     */
    @Bean
    public ResourceServerTokenServices defaultTokenServices() {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenEnhancer(accessTokenConverter());
        defaultTokenServices.setTokenStore(tokenStore());

        return defaultTokenServices;
    }


    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter() {
//			/***
//			 * 重写增强token方法,用于自定义一些token返回的信息
//			 */
//			@Override
//			public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
//				String userName = authentication.getUserAuthentication().getName();
//				User user = (User) authentication.getUserAuthentication().getPrincipal();// 与登录时候放进去的UserDetail实现类一直查看link{SecurityConfiguration}
//				/** 自定义一些token属性 ***/
//				final Map<String, Object> additionalInformation = new HashMap<>();
//				additionalInformation.put("userName", userName);
//				additionalInformation.put("roles", user.getAuthorities());
//				((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
//				OAuth2AccessToken enhancedToken = super.enhance(accessToken, authentication);
//				return enhancedToken;
//			}

        };
        accessTokenConverter.setSigningKey("123");// 测试用,授权服务使用相同的字符达到一个对称加密的效果,生产时候使用RSA非对称加密方式
        return accessTokenConverter;
    }
    class OAuthRequestedMatcher implements RequestMatcher {
        @Override
        public boolean matches(HttpServletRequest request) {
            String auth = request.getHeader("Authorization");
            // Determine if the client request contained an OAuth Authorization
            boolean haveOauth2Token = (auth != null) && auth.startsWith("Bearer");
            boolean haveAccessToken = request.getParameter("access_token") != null;
            return haveOauth2Token || haveAccessToken;
        }


    }


}
