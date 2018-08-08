package app.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionProvider {

	private Connection connection;
	
	public DBConnectionProvider() throws SQLException {
		this.connectDb();
	}

	private void connectDb() throws SQLException {
		String jdbcUrl = String.format(
				"jdbc:mysql://google/%s?cloudSqlInstance=%s"
						+ "&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false",
				"msgdb", "possible-haven-212003:us-central1:possiblehavendb");

		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connection = DriverManager.getConnection(jdbcUrl, "root", "root");
	}

	public Connection getConnection() {
		return connection;
	}
}
