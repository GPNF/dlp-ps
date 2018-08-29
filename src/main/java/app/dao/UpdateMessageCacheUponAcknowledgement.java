package app.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import app.model.JsonDataContainer;
import app.util.ConfigParams;
import app.util.PropertyParserAndConfigAdapter;

/**
 * @author Aniruddha
 *
 */
public class UpdateMessageCacheUponAcknowledgement {

	private ConfigParams params;
	private PropertyParserAndConfigAdapter connAdapter;

	public UpdateMessageCacheUponAcknowledgement(String cfgPath) {
		super();
		this.connAdapter = new PropertyParserAndConfigAdapter(cfgPath);
		this.params = this.connAdapter.readPropertiesAndSetParameters();
	}

	/**
	 * @param message
	 * @throws IOException
	 */
	public void insertIntoTable(JsonDataContainer message) throws IOException {
		try (Connection conn = this.params.getConn()) {
			final String query = "update message_status_cache_db set dlv_rprt = ?  where glo_tran_id=?";
			PreparedStatement statement = conn.prepareStatement(query);

			try {
				if (message.getDeliveryFlag().equalsIgnoreCase("true"))
					statement.setString(1, "Delivered");
				else
					statement.setString(1, "In-progress");
				statement.setString(2, message.getMessageId());
				statement.execute();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}