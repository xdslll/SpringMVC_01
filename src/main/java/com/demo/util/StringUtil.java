package com.demo.util;

import java.util.UUID;

public class StringUtil {
	
	public static String getRandomUUID() {
		return UUID.randomUUID().toString().replaceAll("\\-", "");
	}

	public static boolean isEmpty(String val) {
		if(val == null || "".equals(val.trim()))
		{
			return true;
		}
		return false;
	}

}