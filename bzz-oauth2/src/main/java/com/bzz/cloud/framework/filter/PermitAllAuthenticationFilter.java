package com.bzz.cloud.framework.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ：Iscrdr
 * @description：解决PermitAll不生效问题
 * @email ：624003618@qq.com
 * @date ：2020-11-21 14:33
 * @modified By：
 * @version: 1.0.0
 */
@Component("permitAllAuthenticationFilter")
@Slf4j
public class PermitAllAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("当前请求的url:{}", request.getRequestURI());
        if (
                "/oauthservice/test1".equals(request.getRequestURI())

        ) {

            request = new HttpServletRequestWrapper(request) {
                private Set<String> headerNameSet;

                @Override
                public Enumeration<String> getHeaderNames() {
                    if (headerNameSet == null) {
                        // first time this method is called, cache the wrapped request's header names:
                        headerNameSet = new HashSet<>();
                        Enumeration<String> wrappedHeaderNames = super.getHeaderNames();
                        while (wrappedHeaderNames.hasMoreElements()) {
                            String headerName = wrappedHeaderNames.nextElement();
                            if (!"Authorization".equalsIgnoreCase(headerName)) {
                                headerNameSet.add(headerName);
                            }
                        }
                    }
                    return Collections.enumeration(headerNameSet);
                }

                @Override
                public Enumeration<String> getHeaders(String name) {
                    if ("Authorization".equalsIgnoreCase(name)) {
                        return Collections.<String>emptyEnumeration();
                    }
                    return super.getHeaders(name);
                }

                @Override
                public String getHeader(String name) {
                    if ("Authorization".equalsIgnoreCase(name)) {
                        return null;
                    }
                    return super.getHeader(name);
                }
            };

        }
        filterChain.doFilter(request, response);
    }
}
