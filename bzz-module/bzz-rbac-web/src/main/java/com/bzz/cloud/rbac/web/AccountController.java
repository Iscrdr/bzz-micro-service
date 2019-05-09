package com.bzz.cloud.rbac.web;

import com.bzz.cloud.rbac.entity.SysUser;
import com.bzz.cloud.rbac.service.SysUserService;
import com.bzz.common.Utils.ResponseData;
import com.bzz.common.Utils.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/currentUser")
    @ResponseBody
    public ResponseResult currentUser(HttpServletRequest request,HttpServletResponse response, SysUser sysUser,  BindingResult result){
        ResponseResult rr = new ResponseResult();
        Map<String,String> msgMap = new HashMap<>();
        if(null != sysUser && StringUtils.isNotBlank(sysUser.getUserName())){
            sysUser = sysUserService.getUserByLoginName(sysUser);
            msgMap.put(ResponseData.DATA_SECCUSS_CODE,"获取用户信息成功");
            rr.setSuccess(true);
        }else {
            msgMap.put(ResponseData.DATA_FAIL_CODE,"获取用户信息失败");
            rr.setSuccess(false);
        }
        rr.setData(sysUser);
        rr.setMsgMap(msgMap);
        return rr;
    }
}
