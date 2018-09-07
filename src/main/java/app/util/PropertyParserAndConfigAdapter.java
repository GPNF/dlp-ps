package app.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;
/**
 * 
 * @author Aniruddha Sinha
 * @Description This class acts as a de-facto adapter class where properties get
 *              loaded and the values against different property keys are being
 *              fed to their respective model classes such as <<ConfigParams>>
 *              class, which is used to create Database Connection Object
 * 
 * @params
 * @param propertyObject
 * @param classLoaderObject
 * @param inputStreamObject
 * @param ConfigParamsObject
 *
 */
public class PropertyParserAndConfigAdapter {


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
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return this.params; // temporary
	}

}
