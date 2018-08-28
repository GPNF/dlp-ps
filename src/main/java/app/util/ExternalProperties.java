package app.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This class is designed to <b>handle all properties files</b>. The get()
 * method is available for each property file. <br>
 * <br>
 * When you add new property file, you should write your get() method where you
 * need to specify properties file name.
 * 
 * @author AdarshSinghal
 *
 */
public class ExternalProperties {

	private static Properties properties = null;

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

	/**
	 * Use this method to retrieve property from appconfig.properties file
	 * 
	 * @param key
	 * @return
	 */
	public static String getAppConfig(String key) {
		init("appconfig.properties");
		return (String) properties.get(key);
	}

	/**
	 * Use this method to retrieve property from dbconfig.properties file
	 * 
	 * @param key
	 * @return
	 */
	public static String getDbConfig(String key) {
		init("dbconfig.properties");
		return (String) properties.get(key);
	}

}
