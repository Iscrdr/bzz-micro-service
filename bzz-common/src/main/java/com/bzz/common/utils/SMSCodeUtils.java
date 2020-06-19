package com.bzz.common.utils;

import java.util.Random;

/**
 * @DESC: 验证码工具类
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-三月-03 14-25
 * @Modified by:
 * @Description:
 */
public class SMSCodeUtils {
    /**
     * 随机生成随机验证码
     * @return
     */
    public static String getRandom(){
        Random random = new Random();
        int max = 999999;// 最大值
        int min = 100000;// 最小值
        int code = random.nextInt(max);// 随机生成
        if(code < min){// 低于6位数，加上最小值，补上
            code = code + min;
        }
        return code+"";
    }

    public static void main(String[] args) {
        System.out.println(getRandom());
    }


}
