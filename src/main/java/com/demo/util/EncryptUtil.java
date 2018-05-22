package com.demo.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author xiads
 * @date 16/1/11
 */
public class EncryptUtil {

    public static String sha1(String str) {
        return encrypt(str, "SHA-1");
    }

    public static String aes(String str) {
        return encrypt(str, "AES");
    }

    public static String md5(String str) {
        return encrypt(str, null);
    }

    public static String encrypt(String strSrc, String encName) {
        //parameter strSrc is a string will be encrypted,
        //parameter encName is the algorithm name will be used.
        //encName dafault to "MD5"
        MessageDigest md = null;
        String strDes = null;

        byte[] bt = strSrc.getBytes();
        try {
            if (encName == null || encName.equals("")) {
                encName = "MD5";
            }
            md = MessageDigest.getInstance(encName);
            md.update(bt);
            strDes = bytes2Hex(md.digest());  //to HexString
        } catch (NoSuchAlgorithmException e) {
            // System.out.println("Invalid algorithm.");
            return null;
        }
        return strDes;
    }

    public static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }

    // 加密
    public static String encodeBase64(String str) {
        byte[] b = null;
        String s = null;
        try {
            b = str.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (b != null) {
            s = new BASE64Encoder().encode(b);
        }
        return s;
    }

    // 解密
    public static String decodeBase64(String str) {
        byte[] b = null;
        String result = null;
        if (str != null) {
            BASE64Decoder decoder = new BASE64Decoder();
            try {
                b = decoder.decodeBuffer(str);
                result = new String(b, "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}