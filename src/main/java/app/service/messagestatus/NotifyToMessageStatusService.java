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
 * @Description: This is a 
 ** @params
 * @param ConfigParams object - The aim of this object is to contain the data
 *                     provided by the propertyParserAndConfigAdapter object and
 *                     create a database connection out of it, which will later
 *                     be used for feeding data into message status cache db
 * @PropertyParserAndConfigAdapter object - The aim of this object is to pick
 *                                 data from the property file and create a
 *                                 configuration parameters object out of it.
 *
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
	 * this function feeds-in the data to message status cache db
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