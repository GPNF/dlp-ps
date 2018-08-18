package app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import app.model.SubscriberMessage;

/**
 * CRUD operation on subscriber table
 * 
 * @author AdarshSinghal
 *
 */
public class SubscriberDao {
	private static final String YYYY_MM_DD_HH_MM_SS_A_Z = "yyyy-MM-dd hh:mm:ss a z";
	Logger logger = LoggerFactory.getLogger(SubscriberDao.class);

	private Connection connection;

	public SubscriberDao() throws SQLException, ClassNotFoundException {
		DBConnectionProvider connProvider = new DBConnectionProvider();
		this.connection = connProvider.getConnection();
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

		SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_A_Z);

		Date publishDate = new Date();
		Date pullDate = new Date();

		try {
			publishDate = formatter.parse(subscriberMessage.getPublishTime());
			pullDate = formatter.parse(subscriberMessage.getPullTime());
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}

		Timestamp publishTimestamp = new Timestamp(publishDate.getTime());
		Timestamp pullTimestamp = new Timestamp(pullDate.getTime());

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
		String publishTime = getFormattedTimestamp(rs, "published_timestamp");
		String pullTime = getFormattedTimestamp(rs, "pull_timestamp");
		String ackId = rs.getString("ack_id");
		String globalTxnId = rs.getString("global_txn_id");

		SubscriberMessage subscriberMessage = new SubscriberMessage(messageId, message, publishTime, ackId,
				globalTxnId);
		subscriberMessage.setId(id);
		subscriberMessage.setSubscriptionId(subscriberId);
		subscriberMessage.setPullTime(pullTime);
		return subscriberMessage;
	}

	private String getFormattedTimestamp(ResultSet rs, String columnName) throws SQLException {
		SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_A_Z);
		Timestamp timestamp = rs.getTimestamp(columnName);
		Date date = new Date(timestamp.getTime());
		formatter.setTimeZone(TimeZone.getTimeZone("EST"));
		String formattedTime = formatter.format(date);
		return formattedTime;
	}

}
