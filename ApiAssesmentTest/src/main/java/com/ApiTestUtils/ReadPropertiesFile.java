package com.ApiTestUtils;

import java.io.FileInputStream;
import java.util.Properties;

import com.ApiTestUtils.*;
public class ReadPropertiesFile {
	public static String getConfigProperties(String str)
	{
		
		
		String path = SourcePath.CONFIG_FILE_PATH;
		String configInfo = "";
		try
		{
			FileInputStream fis = new FileInputStream(path);
			Properties prop = new Properties();
			prop.load(fis);
			configInfo = prop.getProperty(str);
		}catch(Exception e)
		{
			System.out.println("Property File not found.");
		}
		return configInfo;
	}
}
