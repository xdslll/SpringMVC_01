package com.demo.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author xiads
 * @date 16/1/11
 */
public class Tools {

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
        }
        catch (NoSuchAlgorithmException e) {
            System.out.println("Invalid algorithm.");
            return null;
        }
        return strDes;
    }

    public static String bytes2Hex(byte[]bts) {
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

    public static boolean isEmpty(String str) {
        return str == null || str.trim().equals("");
    }

    public static int[] convert(String[] array) {
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Integer.parseInt(array[i]);
        }
        return result;
    }
}