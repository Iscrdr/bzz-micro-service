package com.bzz.cloud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.client.Netty4ClientHttpRequestFactory;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import java.util.LinkedHashSet;
import java.util.Set;

//@Configuration
public class Oauth2FeignConfig {

/*


    @Bean
    public ResourceOwnerPasswordResourceDetails resourceDetails() {
        ResourceOwnerPasswordResourceDetails details = new ResourceOwnerPasswordResourceDetails();
        details.setClientId("bzz-cloud");
        details.setClientSecret("bzz-cloud");
        Set<String> scopes = new LinkedHashSet<>();
        scopes.add("read");
        scopes.add("write");
        details.setUsername("admin");
        details.setPassword("admin");

        return details;
    }

    @Bean("practiceOAuth2RestTemplate")
    public OAuth2RestTemplate oAuth2RestTemplate() {
        final OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(resourceDetails(), new DefaultOAuth2ClientContext());
        oAuth2RestTemplate.setRequestFactory(new Netty4ClientHttpRequestFactory());

        return oAuth2RestTemplate;

    }*/
}
