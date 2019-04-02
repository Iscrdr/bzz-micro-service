package com.bzz.cloud.rbac.web;

import com.bzz.cloud.rbac.entity.SysUser;
import com.bzz.cloud.rbac.service.SysUserService;
import com.bzz.common.Utils.AliDaYuSMS;
import com.bzz.common.Utils.IdUtils;
import com.bzz.common.Utils.ResponseData;
import com.bzz.common.Utils.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-04-02 19-11
 * @Modified by:
 * @Description:
 */
@RestController
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(AliDaYuSMS.class);

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * @desc: 用户注册
     * @Param: email 邮箱,mobile 手机号 必须填写
     * @return : json message
     * @Auther: yang qianli
     * @Email: 624003618@qq.com
     * @Date: 2019/3/1 17:27
     * @update:
     */
    @PostMapping(value = "/login/account")
    public ResponseResult register(HttpServletRequest request, @RequestBody @Valid SysUser sysUser, BindingResult result){
        ResponseResult rr = new ResponseResult();
        Map<String,String> msgMap = new HashMap<String,String>();

        return rr;

    }


}
