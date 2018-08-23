package app.msgstatuscache.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConfigParams {
	private String instanceConnectionName;
	private String connectionString;
	private String jdbcConnectionUrl;
	private String dbName;
	private Connection conn;
	private String userName;
	private String passwd;
	private String tableName;
	private String topicName;
	private String subscriberName;

	private ConfigParams(ParamsBuilder builder) {
		this.connectionString = builder.connectionString;
		this.instanceConnectionName = builder.instanceConnectionName;
		this.jdbcConnectionUrl = builder.jdbcConnectionUrl;
		this.conn = builder.conn;
		this.userName = builder.userName;
		this.passwd = builder.passwd;
		this.dbName = builder.dbName;
		this.tableName = builder.tableName;
		this.topicName = builder.topicName;
		this.subscriberName = builder.subscriberName;
	}

	public String getTopicName() {
		return topicName;
	}

	public String getSubscriberName() {
		return subscriberName;
	}

	public String getTableName() {
		return tableName;
	}

	public String getInstanceConnectionName() {
		return instanceConnectionName;
	}

	public String getConnectionString() {
		return connectionString;
	}

	public String getJdbcConnectionUrl() {
		return jdbcConnectionUrl;
	}

	public String getDbName() {
		return dbName;
	}

	public Connection getConn() {
		return conn;
	}

	public String getUserName() {
		return userName;
	}

	public String getPasswd() {
		return passwd;
	}

	public static class ParamsBuilder {
		private String instanceConnectionName;
		private String connectionString;
		private String jdbcConnectionUrl;
		private String dbName;
		private Connection conn;
		private String userName;
		private String passwd;
		private String tableName;
		private String topicName;
		private String subscriberName;

		public ParamsBuilder() {
		}
		/*
		 * public ParamsBuilder(String connName, String connString, Connection co,
		 * String uname, String passwd) { this.instanceConnectionName = connName;
		 * this.connectionString = connString; this.conn = co; this.userName = uname;
		 * this.passwd = passwd; }
		 */

		public ParamsBuilder setTopicName(String topicName) {
			this.topicName = topicName;
			return this;
		}

		public ParamsBuilder setSubscriptionName(String subscriptionName) {
			this.subscriberName = subscriptionName;
			return this;
		}

		public ParamsBuilder setInstanceConnectionName(String connName) {
			this.instanceConnectionName = connName;
			return this;
		}

		public ParamsBuilder setJDBCConnectionString(String connectionString) {
			this.connectionString = connectionString;
			return this;
		}

		/*
		 * public ParamsBuilder buildConnectionObject(Connection cObj) { this.conn =
		 * cObj; return this; }
		 */

		public ParamsBuilder setUserName(String username) {
			this.userName = username;
			return this;
		}

		public ParamsBuilder setPassword(String password) {
			this.passwd = password;
			return this;
		}

		public ParamsBuilder setTableName(String tblNm) {
			this.tableName = tblNm;
			return this;
		}

		public ParamsBuilder setDatabaseName(String dbName) {
			this.dbName = dbName;
			return this;
		}

		public ConfigParams build() throws SQLException {
			this.jdbcConnectionUrl = String.format(this.connectionString, this.dbName, this.instanceConnectionName);
			this.conn = DriverManager.getConnection(this.jdbcConnectionUrl, this.userName, this.passwd);
			return new ConfigParams(this);
		}
	}
}
