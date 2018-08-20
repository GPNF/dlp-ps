package app.msgstatuscache.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

import com.google.gson.Gson;

public class PropertyParserAndConfigAdapter {
	private Properties propertiesObject;
	private String propertyFilePath;
	private ConfigParams params;

	public PropertyParserAndConfigAdapter(String path) {
		this.propertiesObject = new Properties();
		this.propertyFilePath = path;
	}

	/**
	 * the primary intention of this function is to act as an Adapter which inits a
	 * ConfigParams object and get the parameters from config.properties file
	 * 
	 * @return ConfigParams object
	 */
	public ConfigParams readPropertiesAndSetParameters() {
		// try with resources
		try (InputStream input = new FileInputStream(this.propertyFilePath)) {
			this.propertiesObject.load(input);
			// actual exchange of data occurs here
			this.params = new ConfigParams.ParamsBuilder()
					.setDatabaseName(this.propertiesObject.getProperty("database"))
					.setUserName(this.propertiesObject.getProperty("username"))
					.setPassword(this.propertiesObject.getProperty("password"))
					.setInstanceConnectionName(this.propertiesObject.getProperty("instanceConnectionName"))
					.setJDBCConnectionString(this.propertiesObject.getProperty("JDBCConnectionString"))
					.setTableName(this.propertiesObject.getProperty("tablename")).build();
		} catch (IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return this.params; // temporary
	}

}
