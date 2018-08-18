package app.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import app.util.ExternalProperties;

/**
 * Provides JDBC connection
 * 
 * @author AdarshSinghal
 *
 */
public class DBConnectionProvider {

	private Connection connection;

	/**
	 * Initialize JDBC connection. Use getConnection() method to obtain connection
	 * object.
	 * 
	 * 
	 * @throws SQLException
	 */
	public DBConnectionProvider() throws SQLException {
		this.connectDb();
	}

	private void connectDb() throws SQLException {
		String jdbcUrl = getJdbcUrl();
		initJdbcDriver();
		setConnection(jdbcUrl);
	}

	private void setConnection(String jdbcUrl) throws SQLException {
		String user = ExternalProperties.getDbConfig("app.gc.cloudsql.jdbc.username");
		String passwd = ExternalProperties.getDbConfig("app.gc.cloudsql.jdbc.password");
		connection = DriverManager.getConnection(jdbcUrl, user, passwd);
	}

	private void initJdbcDriver() {
		try {
			String driver = ExternalProperties.getDbConfig("app.gc.cloudsql.jdbc.driver");
			Class.forName(driver).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private String getJdbcUrl() {
		String dbName = ExternalProperties.getDbConfig("app.gc.cloudsql.db");
		String cloudSqlInstance = ExternalProperties.getDbConfig("app.gc.cloudsql.instance");
		String jdbcUrl = String.format(
				"jdbc:mysql://google/%s?cloudSqlInstance=%s"
						+ "&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false",
				dbName, cloudSqlInstance);
		return jdbcUrl;
	}

	/**
	 * @return Connection
	 */
	public Connection getConnection() {
		return connection;
	}
}
