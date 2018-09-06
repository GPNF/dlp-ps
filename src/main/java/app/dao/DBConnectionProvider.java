package app.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

import app.util.ExternalProperties;

/**
 * Provides JDBC connection. Use getConnection() method to obtain connection
 * object.
 * 
 * @author AdarshSinghal
 *
 */
public class DBConnectionProvider {

	private static final Logger LOGGER = Logger.getLogger(DBConnectionProvider.class.getName());
	private Connection connection;

	/**
	 * Initialize JDBC connection. Use getConnection() method to obtain connection
	 * object.
	 * 
	 * 
	 * @throws SQLException
	 */
	public DBConnectionProvider() throws SQLException {
		String jdbcUrl = formJdbcConnectionUrl();
		initializeJdbcDriver();
		setConnection(jdbcUrl);
	}

	private void setConnection(String jdbcUrl) throws SQLException {
		String user = ExternalProperties.getDbConfig("app.gc.cloudsql.jdbc.username");
		String passwd = ExternalProperties.getDbConfig("app.gc.cloudsql.jdbc.password");
		connection = DriverManager.getConnection(jdbcUrl, user, passwd);
	}

	/**
	 * Initializes JDBC Driver. Driver name is used from DBConfig property file.
	 */
	private void initializeJdbcDriver() {
		try {
			String jdbcDriver = ExternalProperties.getDbConfig("app.gc.cloudsql.jdbc.driver");
			Class.forName(jdbcDriver).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			LOGGER.severe(e.getMessage());
		}
	}

	private String formJdbcConnectionUrl() {

		String databaseName = ExternalProperties.getDbConfig("app.gc.cloudsql.db");
		String cloudSqlInstance = ExternalProperties.getDbConfig("app.gc.cloudsql.instance");

		String jdbcUrl = String.format(
				"jdbc:mysql://google/%s?cloudSqlInstance=%s"
						+ "&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false",
				databaseName, cloudSqlInstance);
		return jdbcUrl;
	}

	/**
	 * @return Connection
	 */
	public Connection getConnection() {
		return connection;
	}
}
