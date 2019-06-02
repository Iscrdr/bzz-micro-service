package com.bzz.cloud.framework.filter;

import com.bzz.cloud.oauth.entity.Auth2User;
import com.bzz.cloud.rbac.entity.SysUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-05-27 03-15
 * @Modified by:
 * @Description:
 */
public class JsonPayloadUserNamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //attempt Authentication when Content-Type is json
        System.out.println(request.getContentType());
        if(request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE)
                ||request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)){

            ObjectMapper mapper = new ObjectMapper();
            UsernamePasswordAuthenticationToken authRequest ;
            try (InputStream is = request.getInputStream()){
                SysUser sysUser = mapper.readValue(is, SysUser.class);
                request.setAttribute("loginType",sysUser.getLoginType());
                authRequest = new UsernamePasswordAuthenticationToken(sysUser.getUserName(), sysUser.getPassword());
                authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
                return this.getAuthenticationManager().authenticate(authRequest);
            }catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }else {
            //transmit it to UsernamePasswordAuthenticationFilter
            return super.attemptAuthentication(request, response);
        }
    }
}
