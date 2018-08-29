package app.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import app.dao.DBConnectionProvider;

/**
 * <span style='color:red'>Use with caution.</span> Executes DDL/DML commands.
 * Following are supported operations:-
 * <ul>
 * <li>executeUpdate(String sql)</li>
 * <li>truncateTables() - Publisher, Subscriber, Logging, Message Status</li>
 * <li>truncateTables(String... tables)</li>
 * </ul <br>
 * 
 * @author adarshsinghal
 *
 */
public class SQLExecutor {

	public static void main(String[] args) throws SQLException {
		truncateTables();
	}

	private static Statement executeUpdate(String sql) throws SQLException {
		DBConnectionProvider connProvider = new DBConnectionProvider();
		Connection conn = connProvider.getConnection();
		Statement sm = conn.createStatement();
		return sm;
	}

	public static void truncateTables(String... tables) throws SQLException {
		for (int i = 0; i < tables.length; i++) {
			executeUpdate("DELETE FROM  " + tables[i]);
		}
	}

	/**
	 * Truncate the tables<br>
	 * <br>
	 * activity_logging <br>
	 * message_status_cache_db <br>
	 * publisher <br>
	 * subscriber
	 */
	public static void truncateTables() throws SQLException {
		String[] tables = { "activity_logging", "message_status_cache_db", "publisher", "subscriber" };
		for (int i = 0; i < tables.length; i++) {
			executeUpdate("DELETE FROM " + tables[i]);
		}
	}

}
