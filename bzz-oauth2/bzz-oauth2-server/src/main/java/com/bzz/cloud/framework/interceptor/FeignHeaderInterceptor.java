package com.bzz.cloud.framework.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;

//@Component
public class FeignHeaderInterceptor implements RequestInterceptor {
    private final String AUTHORIZATION_HEADER = "Authorization";
    private final String BEARER_TOKEN_TYPE = "Bearer";

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(AUTHORIZATION_HEADER, String.format("%s %s", BEARER_TOKEN_TYPE, "bzz-token"));
    }

}
