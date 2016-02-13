package com.demo.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import com.demo.model.EnfordProductImportHistory;
import org.apache.log4j.Logger;

public class Config {
	
	private static final Logger logger = Logger.getLogger(Config.class);
	
	private static Properties prop = null;

	public static void init() {
        prop = new Properties();
		FileInputStream fis = null;
		try {
			 fis = new FileInputStream(Config.class.getResource("/").getPath()+"/config.properties");
			 prop.load(fis);
		} catch(Exception ex) {
			logger.error(ex);
			
		}
		finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception ex) {
                    logger.error(ex);
                }
            }
		}
	}
	
	public static String getProperty(String key) {
		if(prop == null) {
			init();
		}
		return prop.getProperty(key);
	}

    public static String getUploadFilePath() {
        return getProperty("uploadpath");
    }

    public static String getCategoryPath() {
        return getProperty("categorypath");
    }

    /**
     * 获取上传文件路径
     *
     * @param importHistory
     * @return
     */
    public static File getFilePath(EnfordProductImportHistory importHistory, String dirPath) {
        //获取文件名
        String fileName = importHistory.getFilePath();
        //获取文件类型
        String fileType = importHistory.getFileType();
        //获取文件存放的路径
        File fileDir = new File(dirPath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        //生成文件对象
        File file = new File(fileDir, fileName + "." + fileType);
        return file;
    }

}
