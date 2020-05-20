package com.bzz.cloud.framework.security;



import java.nio.charset.StandardCharsets;
import java.util.Base64;
/**
 * @PACKAGE_NAME: com.bzz.cloud.framework.security
 * @CLASS_NAME: DeAndEncrpt
 * @Description:
 * @Author : yang qianli
 * @Date: 2017-11-24 23:20
 * @Modified by:
 * @Date:
 */
public class DeAndEncrpt {

    /* @author: yang qianli
     * @date: 2017-11-24 23:18
     * @param:  String str
     * @return:   String
     * @description: encryption
     */
    public static String enCode(String str){
        return Base64.getEncoder().encodeToString(str.getBytes(StandardCharsets.UTF_8));
    }
    /* @author: yang qianli
     * @date: 2017-11-24 23:19
     * @param:  string
     * @return:   String
     * @description: Decrypt
     */
    public static String deCode(String str){
        return new String(Base64.getDecoder().decode(str),StandardCharsets.UTF_8);
    }
    /* @author: yang qianli
     * @date: 2017-11-24 23:20
     * @param:  * @param null
     * @return:
     * @description: Test
     */
    public static void main(String[] args) {
        String str="admin";
        String encode = DeAndEncrpt.enCode(str);
        System.out.println(encode);
        String deCode = DeAndEncrpt.deCode(encode);
        System.out.println(deCode);

    }
}
