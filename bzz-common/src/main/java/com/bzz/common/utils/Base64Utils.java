package com.bzz.common.utils;

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

}
