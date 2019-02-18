package com.bzz.cloud.framework.interceptor;


import com.bzz.cloud.framework.annotations.IgnoreOauthToken;
import com.bzz.cloud.oauth2.client.service.Oath2FeignClientService;
import com.bzz.common.Utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RestApiOauth2Interceptor extends HandlerInterceptorAdapter {

    @Autowired
    private Oath2FeignClientService oath2FeignClientService;

    /**
     * 在MVC方法执行之前拦截 request,判断token有效性
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        //标有IgnoreOauthToken 注解的方法不拦截
        IgnoreOauthToken annotation = handlerMethod.getBeanType().getAnnotation(IgnoreOauthToken.class);
        if (annotation == null) {
            annotation = handlerMethod.getMethodAnnotation(IgnoreOauthToken.class);
        } else {
            return super.preHandle(request, response, handler);
        }
        //从http请求header或者cookie中获取Token
        String token = request.getHeader("Authorization");
        if (StringUtils.isBlank(token)) {
            Cookie[] cookies = request.getCookies();
            if(null != cookies){
                for (Cookie c : cookies) {
                    String name = c.getName();
                    if ("token".equals(name)) {
                        token = c.getValue();
                        break;
                    }
                }
            }else{
                throw new Exception("Client is Forbidden! Error Code : 110011");
            }

        }else{
            //来自认证中心的请求不认证
            if(token.equals("Bearer bzz-token")){
                return super.preHandle(request, response, handler);
            }else{
                //获取客户端信息
                String authJson = oath2FeignClientService.checkToken(token);
                String client_id = JsonUtils.getFeildFromJson("client_id", authJson);
                String user_name = JsonUtils.getFeildFromJson("user_name", authJson);

                //获取所有的客户端
                String allClient = oath2FeignClientService.getAllClient();
                String client_id_1 = JsonUtils.getFeildFromJson("client_id", allClient);
                String user_name_1 = JsonUtils.getFeildFromJson("user_name", allClient);


                if ((StringUtils.isNotBlank(client_id) && client_id.equals(client_id_1)) &&
                        (StringUtils.isNotBlank(user_name) && client_id.equals(user_name_1))
                ) {
                    return super.preHandle(request, response, handler);
                }
                throw new Exception("Client is Forbidden! Error Code : 110011");
            }
        }
        throw new Exception("Client is Forbidden! Error Code : 110011");

    }


}
