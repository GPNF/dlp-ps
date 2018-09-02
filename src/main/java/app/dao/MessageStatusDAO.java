package app.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.constants.Constants;
import app.model.MessageStatus;
import app.model.MessageStatusCacheField;

/**
 * @author Aniruddha
 *
 */
public class MessageStatusDAO {

	private Connection connection;

	public MessageStatusDAO() throws SQLException {
		DBConnectionProvider connProvider = new DBConnectionProvider();
		connection = connProvider.getConnection();
	}

	/**
	 * @param message
	 * @throws IOException
	 * @throws SQLException
	 */
	public void insertIntoTable(MessageStatus message) throws IOException, SQLException {

		final String query = "UPDATE message_status_cache_db SET dlv_rprt = ?  WHERE glo_tran_id=?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, getDeliveryStatus(message));
		statement.setString(2, message.getMessageId());
		statement.execute();

	}

	/**
	 * @param message
	 * @return deliveryStatus:String
	 */
	private String getDeliveryStatus(MessageStatus message) {
		String deliveryFlag = message.getDeliveryFlag();
		boolean isDelivered = deliveryFlag != null && deliveryFlag.equalsIgnoreCase(Constants.TRUE);
		String deliveryStatus = Constants.IN_PROGRESS;
		if (isDelivered)
			deliveryStatus = Constants.DELIVERED;
		return deliveryStatus;
	}
	
	public List<MessageStatusCacheField> getAllFieldDetails() throws SQLException {
		MessageStatusCacheField mscfObject;
		List<MessageStatusCacheField> messageEntryList = new ArrayList<>();
		final String sql = "SELECT glo_tran_id, dlv_rprt FROM message_status_cache_db";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			mscfObject = getTableDetails(rs);
			messageEntryList.add(mscfObject);
		}

		return messageEntryList;
	}

	public MessageStatusCacheField getTableDetails(ResultSet rs) throws SQLException {
		return new MessageStatusCacheField.MessageStatusCacheFieldBuilder()
				.setDeliveryReport(rs.getString("dlv_rprt"))
				.setGlobalTransactionId(rs.getString("glo_tran_id")).build();

	}

}