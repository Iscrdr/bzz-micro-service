package com.bzz.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * @author ：Iscrdr
 * @description：Base64编码解码
 * @email ：624003618@qq.com
 * @date ：2020-06-10 02:59
 * @modified By：
 * @version: 1.0.0
 */
public class Base64Utils {
    public static final Base64.Encoder encoder = Base64.getEncoder();
    public static final Base64.Decoder decoder = Base64.getDecoder();

    /**
     * base64加密
     *
     * @param encodeText 明文
     * @return
     */
    public static byte[] encoder(byte[] encodeText) {
        return encoder.encode(encodeText);
    }

    /**
     * base64加密
     *
     * @param decodeText 密文
     */
    public static byte[] decoder(byte[] decodeText) {
        return decoder.decode(decodeText);
    }



    /**
     * base64加密
     *
     * @param encodeText 加密
     * @return
     */
    public static String encoderString(String text,String charset) throws UnsupportedEncodingException {
        if(StringUtils.isBlank(charset)){
            charset = "UTF-8";
        }
        byte[] bytes = text.getBytes(charset);
        return encoder.encodeToString(bytes);
    }

    /**
     * base64加密
     *
     * @param decodeText 解密
     */
    public static String decoderString(String decodeText,String charset) throws UnsupportedEncodingException {
        if(StringUtils.isBlank(charset)){
            charset = "UTF-8";
        }
        return new String(decoder.decode(decodeText),charset);
    }




    public static void main(String[] args) throws UnsupportedEncodingException {
        String text = "d9l41M8fPZV5pV3WjLaGgxZBdISthv";
        String s = encoderString(text,"UTF-8");
        System.out.println(s);
        String s1 = decoderString(s,"UTF-8");
        System.out.println(s1.equals(text));

    }

}
