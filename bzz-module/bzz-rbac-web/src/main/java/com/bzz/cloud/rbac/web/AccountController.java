package com.bzz.cloud.rbac.web;

import com.bzz.cloud.rbac.service.SysUserService;
import com.bzz.common.Utils.ResponseData;
import com.bzz.common.Utils.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-05-06 14-35
 * @Modified by:
 * @Description:
 */
@RestController
public class AccountController {

    private Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/account/settings/base")
    @ResponseBody
    public ResponseResult loginout(HttpServletRequest request, HttpServletResponse response, BindingResult result){
        ResponseResult rr = new ResponseResult();


        return rr;
    }
}
