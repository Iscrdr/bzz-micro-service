package com.bzz.cloud.rbac.web;


import com.bzz.cloud.rbac.entity.SysUser;
import com.bzz.cloud.rbac.service.SysUserService;
import com.bzz.common.Utils.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019/3/1 17:27
 * @Modified by:
 * @Description:
 */
@RestController
@RequestMapping("/api")
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
    @PostMapping(value = "/register")
    public ResponseResult register(@Validated SysUser sysUser){
        SysUser sysUser1 = sysUserService.selectUser(sysUser);

        ResponseResult rr = new ResponseResult();
        if(null != sysUser1){
            //校验邮箱是否重复
            if(BzzStringUtils.isNotBlank(sysUser1.getEmail())){
                rr.setMessage(sysUser1.getEmail()+",此邮箱已经被注册,请重新填写");
                rr.setSuccess(false);
                rr.setErrorCode(ResponseData.EMAIL_CODE_ERROR);
                return rr;
            }else if(BzzStringUtils.isNotBlank(sysUser1.getMobile())){
                //校验手机号是否重复
                rr.setMessage(sysUser1.getMobile()+",此手机号已经被注册,请重新填写");
                rr.setSuccess(false);
                rr.setErrorCode(ResponseData.MOBILE_CODE_ERROR);
                return rr;
            }else if(BzzStringUtils.isNotBlank(sysUser1.getLoginName())){
                //校验登录名称是否重复
                rr.setMessage(sysUser1.getLoginName()+",此用户名称已经被注册,请重新填写");
                rr.setSuccess(false);
                rr.setErrorCode(ResponseData.NAME_CODE_ERROR);
                return rr;
            }else {
                sysUserService.insert(sysUser);
                rr.setMessage("恭喜您，您已经成功注册");
                rr.setSuccess(true);
                rr.setErrorCode(ResponseData.REGISTER_CODE);
            }
            rr.setData(sysUser1);
        }
        return rr;
    }


    /**
     * @desc: 用户信息设置
     * @Param: SysUser
     * @return : json message
     * @Auther: yang qianli
     * @Email: 624003618@qq.com
     * @Date: 2019/3/1 17:27
     * @update:
     */
    @PostMapping(value = "/account/settings")
    public String settingAccount(){

        return null;
    }

    /**
     * 获取短信验证码
     * @param mobile 手机号
     * @return
     */
    @PostMapping(value = "getCaptcha")
    public ResponseResult getMobileCode(String mobile){
        ResponseResult rr = new ResponseResult();
        //校验手机号
        if(ValidatorUtils.isMobile(mobile)){
            //从缓存中获取验证码，key为手机号
            String code = stringRedisTemplate.opsForValue().get(mobile);
            if(StringUtils.isNotBlank(code)){
                //验证码已经
                rr.setSuccess(true);
                rr.setMessage("验证码已经发送，请您查看手机短信");
                rr.setErrorCode("");
                return rr;
            }else {
                //生成验证码
                code = SMSCodeUtils.getRandom();
                AliDaYuSMS aliDaYuSMS = new AliDaYuSMS();
                //发送验证码
                try {
                    String result = aliDaYuSMS.sendSMS(mobile,code);
                    logger.info(result);
                    //放入缓存中，并设置验证码有效期，默认为5分钟
                    stringRedisTemplate.opsForValue().set(mobile, code,60*5,TimeUnit.SECONDS);
                    rr.setSuccess(true);
                    rr.setMessage("验证码已经发送，请您查看手机短信");
                    rr.setErrorCode("");
                } catch (Exception e) {
                    e.printStackTrace();
                    rr.setSuccess(false);
                    rr.setMessage("验证码已经失败，请稍后在获取验证码");
                    rr.setErrorCode(ResponseData.SMS_CODE_ERROR);
                }
                return rr;
            }

        }
        rr.setSuccess(false);
        rr.setMessage("手机号填写不正确");
        rr.setErrorCode(ResponseData.SMS_CODE_ERROR);
        return rr;
    }

}
