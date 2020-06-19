package com.bzz.cloud.rbac.web;


import com.bzz.cloud.rbac.entity.SysUser;
import com.bzz.cloud.rbac.service.SysUserService;
import com.bzz.common.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-05-06 14-25
 * @Modified by:
 * @Description:
 */

@RestController
public class RegisterController {

    private Logger logger = LoggerFactory.getLogger(RegisterController.class);

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
    public ResponseResult register(HttpServletRequest request, @RequestBody @Valid SysUser sysUser, BindingResult result){

        List<SysUser> userList = sysUserService.selectList(sysUser);
        ResponseResult rr = new ResponseResult();
        Map<String,String> msgMap = new HashMap<String,String>();
        rr.setData(sysUser);
        boolean flag = true;
        if(null != userList && userList.size()>0){
            for (SysUser sysUser1 : userList){
                if(null != sysUser1){
                    //校验邮箱是否重复
                    if(StringUtils.isNotBlank(sysUser1.getEmail()) && sysUser.getEmail().equals(sysUser1.getEmail())){
                        msgMap.put(ResponseData.EMAIL_CODE_ERROR,"此邮箱已经被注册,请重新填写");
                        rr.setSuccess(false);
                        flag = false;
                    }
                    if(StringUtils.isNotBlank(sysUser1.getMobile()) && sysUser.getMobile().equals(sysUser1.getMobile())){
                        //校验手机号是否重复
                        msgMap.put(ResponseData.MOBILE_CODE_ERROR,"此手机号已经被注册,请重新填写");
                        rr.setSuccess(false);
                        flag = false;
                    }
                    if(StringUtils.isNotBlank(sysUser1.getUserName()) && sysUser.getUserName().equals(sysUser1.getUserName())){
                        //校验登录名称是否重复
                        msgMap.put(ResponseData.NAME_CODE_ERROR,"此用户名称已经被注册,请重新填写");
                        rr.setSuccess(false);
                        flag = false;
                    }
                    //校验验证码是否正确
                    String code = stringRedisTemplate.opsForValue().get(sysUser.getMobile());
                    if(StringUtils.isBlank(code) || !code.equals(sysUser.getCaptcha())){
                        //验证码已经
                        rr.setSuccess(false);
                        msgMap.put(ResponseData.SMS_CODE_ERROR,"短信验证码错误，请重新填写");
                        flag = false;
                    }
                }
            }
        }

        if(!flag){
            rr.setMsgMap(msgMap);
            return rr;
        }

        //账号有效
        sysUser.setEnabled(true);
        //密码未过有效期
        sysUser.setCredentialsNonExpired(true);
        //账号没有被锁定
        sysUser.setAccountNonLocked(true);
        //账号没有过有效期
        sysUser.setAccountNonExpired(true);

        sysUser.setId(IdUtils.getLongId());
        sysUser.setCreateTime(new Date());
        sysUser.setUpdateTime(new Date());
        sysUser.setCreateUserId(sysUser.getId());
        sysUser.setUpdateUserId(sysUser.getId());
        sysUser.setVersion(1);

        sysUserService.insert(sysUser);
        rr.setData(sysUser);
        rr.setSuccess(true);
        msgMap.put(ResponseData.REGISTER_CODE,"恭喜您，您已经成功注册");
        rr.setMsgMap(msgMap);
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
    @RequestMapping(value = "/getCaptcha", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult getMobileCode(String mobile){
        ResponseResult rr = new ResponseResult();

        Map<String,String> msgMap = new HashMap<String,String>();
        //校验手机号
        if( ValidatorUtils.isMobile(mobile)){
            rr.setData(mobile);

            //从缓存中获取验证码，key为手机号
            String code = stringRedisTemplate.opsForValue().get(mobile);
            if(StringUtils.isNotBlank(code)){
                //验证码已经
                rr.setSuccess(true);
                msgMap.put(ResponseData.RESPONE_CODE,"验证码已经发送，请您查看手机短信");
            }else {
                //生成验证码
                code = SMSCodeUtils.getRandom();
                AliDaYuSMS aliDaYuSMS = new AliDaYuSMS();
                //发送验证码
                try {
                    String result = aliDaYuSMS.sendSMS(mobile,code);
                    logger.info(result);
                    //放入缓存中，并设置验证码有效期，默认为5分钟
                    stringRedisTemplate.opsForValue().set(mobile, code,60*60*60,TimeUnit.SECONDS);
                    rr.setSuccess(true);
                    msgMap.put(ResponseData.RESPONE_CODE,"验证码已经发送，请您查看手机短信");
                } catch (Exception e) {
                    e.printStackTrace();
                    rr.setSuccess(false);
                    msgMap.put(ResponseData.SMS_CODE_ERROR,"验证码获取失败,请等待5分钟后重新获取");
                }

            }
            rr.setMsgMap(msgMap);
            return rr;
        }
        rr.setSuccess(false);
        msgMap.put(ResponseData.SMS_CODE_ERROR,"手机号填写不正确");
        rr.setMsgMap(msgMap);
        return rr;
    }

}
