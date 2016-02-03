package com.demo.util;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Config {
	
	private static final Logger logger = Logger.getLogger(Config.class);
	
	private static Properties prop = null;
	public static void init()
	{
		 prop = new Properties();
		FileInputStream fis = null;
		try
		{
			 fis = new FileInputStream(Config.class.getResource("/").getPath()+"/config.properties");
			 prop.load(fis);
		}catch(Exception ex)
		{
			logger.error(ex);
			
		}
		finally
		{
			try{
				fis.close();
			}catch(Exception ex)
			{
				logger.error(ex);
			}
			
		}
	}
	
	public static String getProperty(String key)
	{
		if(prop == null)
		{
			init();
		}
		return prop.getProperty(key);
	}

}
