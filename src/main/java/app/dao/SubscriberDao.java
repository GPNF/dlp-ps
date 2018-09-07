package app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import app.model.SubscriberMessage;
import app.util.DateUtility;

/**
 * CRUD operation on subscriber table
 * 
 * @author AdarshSinghal
 *
 */
public class SubscriberDao {

	private Connection connection;

	public SubscriberDao() throws SQLException, ClassNotFoundException {
		DBConnectionProvider connProvider = new DBConnectionProvider();
		this.connection = connProvider.getConnection();
	}

	@Override
	protected void finalize() throws Throwable {
		if (connection != null)
			connection.close();
	}

	/**
	 * @param
	 * @return number of rows changed
	 * @throws SQLException
	 * @throws ParseException
	 */
	public int insertMessages(List<SubscriberMessage> subscriberMessageList) throws SQLException, ParseException {
		int count = 0;
		for (SubscriberMessage subscriberMessage : subscriberMessageList) {
			int result = insertMessage(subscriberMessage);
			count += result;
		}
		return count;
	}

	/**
	 * @param subscriberMessage
	 * @return number of rows changed
	 * @throws SQLException
	 * @throws ParseException
	 */
	private int insertMessage(SubscriberMessage subscriberMessage) throws SQLException, ParseException {
		String sql = "INSERT INTO subscriber (" + "message_id, message, subscription_name,"
				+ " published_timestamp, pull_timestamp, ack_id, global_txn_id) " + "VALUES (?,?,?,?,?,?,?)";
		PreparedStatement ps = connection.prepareStatement(sql);

		Timestamp publishTimestamp = DateUtility.getTimestamp(subscriberMessage.getPublishTime());
		Timestamp pullTimestamp = DateUtility.getTimestamp(subscriberMessage.getPullTime());

		ps.setString(1, subscriberMessage.getMessageId());
		ps.setString(2, subscriberMessage.getMessage());
		ps.setString(3, subscriberMessage.getSubscriptionId());
		ps.setTimestamp(4, publishTimestamp);
		ps.setTimestamp(5, pullTimestamp);
		ps.setString(6, subscriberMessage.getAckId());
		ps.setString(7, subscriberMessage.getGlobalTransactionId());

		return ps.executeUpdate();
	}

	/**
	 * @return List of SubscriberMessage
	 * @throws SQLException
	 */
	public List<SubscriberMessage> getAllSubscriberMessage() throws SQLException {
		List<SubscriberMessage> subscriberMessages = new ArrayList<>();

		String sql = "SELECT *FROM subscriber";

		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			SubscriberMessage subscriberMessage = getSubscriberMessageFromDb(rs);
			subscriberMessages.add(subscriberMessage);
		}

		return subscriberMessages;

	}

	private SubscriberMessage getSubscriberMessageFromDb(ResultSet rs) throws SQLException {
		String id = rs.getString("id");
		String messageId = rs.getString("message_id");
		String message = rs.getString("message");
		String subscriberId = rs.getString("subscription_name");
		String publishTime = DateUtility.getFormattedTime(rs.getTimestamp("published_timestamp"));
		String pullTime = DateUtility.getFormattedTime(rs.getTimestamp("pull_timestamp"));
		String ackId = rs.getString("ack_id");
		String globalTxnId = rs.getString("global_txn_id");

		SubscriberMessage subscriberMessage = new SubscriberMessage(messageId, message, publishTime, ackId,
				globalTxnId);
		subscriberMessage.setId(id);
		subscriberMessage.setSubscriptionId(subscriberId);
		subscriberMessage.setPullTime(pullTime);
		return subscriberMessage;
	}

}
