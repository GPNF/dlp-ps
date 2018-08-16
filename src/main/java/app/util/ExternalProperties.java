package app.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ExternalProperties {
	
	private static Properties properties =null;

	private static void init(String fileName) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream(fileName);
		properties = new Properties();
		try {
			properties.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getAppConfig(String key) {
		init("appconfig.properties");
		return (String) properties.get(key);
	}
	
	public static String getDbConfig(String key) {
		init("dbconfig.properties");
		return (String) properties.get(key);
	}
	
}
