package app.msgstatuscache.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

public class PropertyParserAndConfigAdapter {
//	private Properties propertiesObject;
//	private String propertyFilePath;
//	private ConfigParams params;

	private Properties propertiesObject;
	private ClassLoader classLoaderObject;
	private InputStream inStreamObject;
	private ConfigParams params;

	public PropertyParserAndConfigAdapter(String path) {
		this.propertiesObject = new Properties();
		this.classLoaderObject = Thread.currentThread().getContextClassLoader();
		this.inStreamObject = this.classLoaderObject.getResourceAsStream(path);
	}

	/**
	 * the primary intention of this function is to act as an Adapter which inits a
	 * ConfigParams object and get the parameters from config.properties file
	 * 
	 * @return ConfigParams object
	 */
	public ConfigParams readPropertiesAndSetParameters() {
		// Properties propertiesObject = new Properties();
		// ClassLoader loader = Thread.currentThread().getContextClassLoader();
		// InputStream stream = loader.getResourceAsStream("myProp.properties");
		// prop.load(stream);

		try {
			this.propertiesObject.load(this.inStreamObject);
			this.params = new ConfigParams.ParamsBuilder()
					.setDatabaseName(this.propertiesObject.getProperty("database"))
					.setUserName(this.propertiesObject.getProperty("username"))
					.setPassword(this.propertiesObject.getProperty("password"))
					.setInstanceConnectionName(this.propertiesObject.getProperty("instanceConnectionName"))
					.setJDBCConnectionString(this.propertiesObject.getProperty("JDBCConnectionString"))
					.setTableName(this.propertiesObject.getProperty("tablename"))
					.setTopicName(this.propertiesObject.getProperty("logging.topic.name"))
					.setSubscriptionName(this.propertiesObject.getProperty("logging.subscription.name")).build();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return this.params; // temporary
	}

}
