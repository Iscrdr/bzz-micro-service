package com.bzz.cloud.auth2.authentication;

import com.bzz.cloud.services.Auth2DetailsService;
import com.bzz.common.Utils.BzzStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.ClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.builders.JdbcClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import sun.security.util.SecurityConstants;

import javax.sql.DataSource;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 授权服务
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private DataSource dataSource;

    /**
     * 获取用户信息
     */
    @Autowired
    private Auth2DetailsService userDetailsService;

    /**
     * 加密方式
     */
   /* @Autowired
    private PasswordEncoder passwordEncoder;*/

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

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
    /**
     *  ClientDetails实现
     * @return
     */
    @Bean
    public ClientDetailsService clientDetails() {
        return new JdbcClientDetailsService(dataSource);
    }
    /**
     * 配置AuthorizationServer安全认证的相关信息，配置令牌端点(Token Endpoint)的安全约束.
     * @param security
     * @throws Exception
     */
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //security.realm(REALM);
        security.passwordEncoder(passwordEncoder());
        security.allowFormAuthenticationForClients();
        security.checkTokenAccess("permitAll()");
        security.tokenKeyAccess("permitAll()");
        //security.checkTokenAccess("isAuthenticated()");
    }

    /**
     * 配置OAuth2的客户端相关信息
     * @param clients
     * @throws Exception
     */
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        clients.withClientDetails(clientDetails());

        /*//InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
        JdbcClientDetailsServiceBuilder jdbc = clients.jdbc(dataSource);

        JdbcClientDetailsService jcds = (JdbcClientDetailsService) clientDetails();
        List<ClientDetails> clientDetails = jcds.listClientDetails();

        if(null != clientDetails && clientDetails.size()>0){
            for (ClientDetails client : clientDetails) {
                jdbc.withClient(client.getClientId())
                        .secret(passwordEncoder().encode(client.getClientSecret()))
                        .authorizedGrantTypes("authorization_code","password","refresh_token","client_credentials","implicit")
                        .scopes("read","write")
                        .redirectUris(StringUtils.join(client.getRegisteredRedirectUri(),","));
                      *//*  .accessTokenValiditySeconds(client.getAccessTokenValiditySeconds())
                        .refreshTokenValiditySeconds(client.getRefreshTokenValiditySeconds());*//*

            }
        }*/
        System.out.println(clients.toString());
    }


    /**
     * 配置AuthorizationServerEndpointsConfigurer，包括配置身份认证器，配置认证方式，TokenStore，TokenGranter，OAuth2RequestFactory
     * @param endpoints
     * @throws Exception
     */
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
        endpoints.tokenStore(tokenStore());
        endpoints.userDetailsService(userDetailsService);

        //配置TokenServices参数
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(endpoints.getTokenStore());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
        tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
        tokenServices.setAccessTokenValiditySeconds( (int) TimeUnit.DAYS.toSeconds(1)); // 1天
        endpoints.tokenServices(tokenServices);
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setSupportRefreshToken(true); // support refresh token
        tokenServices.setTokenStore(tokenStore()); // use jdbc token store
        return tokenServices;
    }
    /**
     * 使用非对称加密算法来对Token进行签名，通过 JDK 工具生成 JKS 证书文件，并将 keystore.jks 放入resource目录下 keytool -genkeypair -alias mytest -keyalg RSA -keypass mypass -keystore keystore.jks -storepass mypass
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        final JwtAccessTokenConverter converter = new JwtAccessToken();
        // 导入证书
        KeyStoreKeyFactory keyStoreKeyFactory =
                new KeyStoreKeyFactory(new ClassPathResource("keystore.jks"), "mypass".toCharArray());
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("mytest"));
        return converter;
    }

}
