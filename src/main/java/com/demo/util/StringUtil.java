package com.demo.util;

import java.util.UUID;

public class StringUtil {
	
	public static String getRandomUUID() {
		return UUID.randomUUID().toString().replaceAll("\\-", "");
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