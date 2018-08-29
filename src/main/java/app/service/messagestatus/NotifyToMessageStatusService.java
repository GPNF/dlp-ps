package app.service.messagestatus;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import app.util.ConfigParams;
import app.util.PropertyParserAndConfigAdapter;

/**
 * @author Aniruddha
 *
 */
public class NotifyToMessageStatusService {

	private ConfigParams params;
	private PropertyParserAndConfigAdapter connAdapter;

	public NotifyToMessageStatusService() {
		super();
		this.connAdapter = new PropertyParserAndConfigAdapter("config_table.properties");
		this.params = this.connAdapter.readPropertiesAndSetParameters();
	}

	/**
	 * @param globalTxnId
	 * @throws IOException
	 * @throws SQLException
	 */
	public void insertIntoTable(String globalTxnId) throws IOException, SQLException {
		Connection conn = this.params.getConn();
		String query = "insert into " + this.params.getTableName().trim() + "(glo_tran_id, dlv_rprt) VALUES (?, ?)";
		PreparedStatement statement = conn.prepareStatement(query);
		statement.setString(1, globalTxnId);
		statement.setString(2, "In-progress");
		statement.execute();
	}

}