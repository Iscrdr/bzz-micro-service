package com.bzz.common.Utils;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * @PACKAGE_NAME: com.bzz.common.Utils
 * @CLASS_NAME: IdUtils
 * @Description:
 * @Author : yang qianli 
 * @Date: 2017-11-19 21:01
 * @Modified by: 
 * @Date:
 */
public class IdUtils  {
    private static SecureRandom random = new SecureRandom();

    /*
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /*
     * 使用SecureRandom随机生成Long.
     */
    public static long randomLong() {
        return Math.abs(random.nextLong());
    }



    /*
     * Activiti ID 生成
     */
    public String getNextId() {
        return IdUtils.uuid();
    }


    public static void main(String[] args) {
        System.out.println(IdUtils.uuid());
        System.out.println(IdUtils.uuid().length());
        System.out.println(new IdUtils().getNextId());
        /*for (int i=0; i<1000; i++){
            System.out.println(IdUtils.randomLong() + "  " + IdUtils.randomBase62(5));
        }*/
    }
    

}
