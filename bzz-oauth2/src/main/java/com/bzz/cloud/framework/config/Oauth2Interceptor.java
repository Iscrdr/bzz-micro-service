package com.bzz.cloud.framework.config;

import com.bzz.cloud.rbac.utils.HttpRequestUitls;
import com.bzz.common.utils.JsonUtils;
import com.bzz.common.utils.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-05-11 00-11
 * @Modified by:
 * @Description:
 */
public class Oauth2Interceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        System.out.println(requestURI);
        if(requestURI.startsWith("/bzzoauth/login")
                || requestURI.startsWith("/bzzoauth/qq/login")
                || requestURI.startsWith("/login")
                || requestURI.startsWith("/qq/login")
                || requestURI.startsWith("/bzzoauth/error")
                || requestURI.startsWith("/bzzoauth/social")

        ){
            return true;
        }

        String token = HttpRequestUitls.getToken(request);
        ResponseResult rr = new ResponseResult();
        if(StringUtils.isBlank(token)){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            Map<String,String> map = new HashMap<>();
            rr.setSuccess(false);
            map.put("401","Unauthorized:token无效，必须经过认证才能访问");
            rr.setMsgMap(map);
            response.setContentType("application/json; charset=utf-8");
            response.getWriter().print(JsonUtils.object2Json(rr));
            return false;
        }
        return true;
    }
}
