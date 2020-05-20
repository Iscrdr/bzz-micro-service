package com.bzz.cloud.framework.filter;

import com.bzz.cloud.oauth.entity.Auth2User;
import com.bzz.cloud.rbac.entity.SysUser;
import com.bzz.common.Utils.ResponseData;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

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

        Map<String,Object> dataMap = new HashMap<>();

        if (request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE)
                || request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
            ObjectMapper mapper = new ObjectMapper();

            UsernamePasswordAuthenticationToken authRequest;

            try (InputStream is = request.getInputStream()) {
                SysUser sysUser = mapper.readValue(is, SysUser.class);
                request.setAttribute("loginType", sysUser.getLoginType());
                authRequest = new UsernamePasswordAuthenticationToken(sysUser, sysUser.getPassword());
                authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
                return this.getAuthenticationManager().authenticate(authRequest);
            } catch (JsonMappingException e1) {
                e1.printStackTrace();
            } catch (JsonParseException e2) {
                e2.printStackTrace();
            } catch (IOException e3) {
                e3.printStackTrace();

            }
            //登录状态
            dataMap.put("status","fail");
            dataMap.put("resCode", ResponseData.REQUEST_PARMARS_CODE);
            try {
                String result = mapper.writeValueAsString(dataMap);
                response.setCharacterEncoding("utf-8");
                response.setContentType("application/json; charset=utf-8");
                PrintWriter out = response.getWriter();
                out.write(result);
                out.flush();
                out.close();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            //transmit it to UsernamePasswordAuthenticationFilter
            return super.attemptAuthentication(request, response);
        }
    }
}
