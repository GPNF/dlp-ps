package app.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import app.dao.DBConnectionProvider;

/**
 * <span style='color:red'>Use with caution.</span> Executes DDL/DML commands.
 * Following are supported operations:-
 * <ul>
 * <li>executeUpdate(String sql)</li>
 * <li>truncateTables() - Publisher, Subscriber, Logging, Message Status</li>
 * <li>truncateTables(String... tables)</li> </ul <br>
 * 
 * @author adarshsinghal
 *
 */
public class SQLExecutor {
	static Connection connection;

	public SQLExecutor() throws SQLException {
		DBConnectionProvider connProvider = new DBConnectionProvider();
		connection = connProvider.getConnection();
	}

	public static void main(String[] args) throws SQLException {
		SQLExecutor sqlExecutor = new SQLExecutor();
		// sqlExecutor.truncateTables();
		// sqlExecutor.showCreateTable("msgdb.activity_logging");

		sqlExecutor.showCreateTableForAll();

	}

	public void showCreateTableForAll() throws SQLException {
		List<String> tables = showTables();
		tables.forEach(table -> {
			try {
				showCreateTable(table);
			} catch (SQLException e) {
			}
		});
	}

	public List<String> showTables() throws SQLException {
		List<String> tables = new ArrayList<>();
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery("show TABLES");
		while (rs.next()) {
			tables.add(rs.getString(1));
		}

		rs.close();
		stmt.close();
		return tables;
	}

	public void showCreateTable(String table) throws SQLException {

		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery("show create table " + table);
		rs.next();
		System.out.println(rs.getString(2));
		rs.close();
		stmt.close();
		System.out.println();
	}

	private void executeUpdate(String sql) throws SQLException {
		Statement sm = connection.createStatement();
		sm.executeUpdate(sql);
		sm.close();

	}

	public void truncateTables(String... tables) throws SQLException {
		for (int i = 0; i < tables.length; i++) {
			executeUpdate("TRUNCATE  " + tables[i]);
		}
	}

	public void truncateTables(String table) throws SQLException {
		executeUpdate("TRUNCATE  " + table);
	}

	/**
	 * Truncate the tables<br>
	 * <br>
	 * activity_logging <br>
	 * message_status_cache_db <br>
	 * publisher <br>
	 * subscriber
	 */
	public void truncateTables() throws SQLException {
		String[] tables = { "activity_logging", "message_status_cache_db", "publisher", "subscriber" };
		for (int i = 0; i < tables.length; i++) {
			executeUpdate("TRUNCATE " + tables[i]);
		}
	}

}
