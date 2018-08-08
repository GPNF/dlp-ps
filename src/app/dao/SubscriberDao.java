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

import app.model.SubscriberMessage;

public class SubscriberDao {
	private static final String YYYY_MM_DD_HH_MM_SS_A = "yyyy-MM-dd hh:mm:ss a";

	private Connection connection;

	public SubscriberDao() throws SQLException, ClassNotFoundException {
		DBConnectionProvider connProvider = new DBConnectionProvider();
		this.connection = connProvider.getConnection();
	}

	public int insertMessages(List<SubscriberMessage> msgs) throws SQLException, ParseException {
		int count = 0;
		for (SubscriberMessage subscriberMessage : msgs) {
			int result = insertMessage(subscriberMessage);
			count += result;
		}
		return count;
	}

	private int insertMessage(SubscriberMessage message) throws SQLException, ParseException {
		String sql = "INSERT INTO subscriber (" + "message_id, message, subscriber_name, subscription_name,"
				+ "pull_type, published_timestamp, pull_timestamp, ack_id) " + "VALUES (?,?,?,?,?,?,?,?)";
		PreparedStatement ps = connection.prepareStatement(sql);

		SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_A);
		Date publishDate = formatter.parse(message.getPublishTime());
		Timestamp publishTimestamp = new Timestamp(publishDate.getTime());
		Date pullDate = formatter.parse(message.getPullTime());
		Timestamp pullTimestamp = new Timestamp(pullDate.getTime());

		ps.setString(1, message.getMessageId());
		ps.setString(2, message.getMessage());
		ps.setString(3, message.getSubscriberName());
		ps.setString(4, message.getSubscriptionId());
		ps.setString(5, message.getPullType());
		ps.setTimestamp(6, publishTimestamp);
		ps.setTimestamp(7, pullTimestamp);
		ps.setString(8, message.getAckId());

		return ps.executeUpdate();
	}

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
		String subscriberName = rs.getString("subscriber_name");
		String subscriberId = rs.getString("subscription_name");
		String pullType = rs.getString("pull_type");
		String publishTime = getFormattedTimestamp(rs, "published_timestamp");
		String pullTime = getFormattedTimestamp(rs, "pull_timestamp");
		String ackId = rs.getString("ack_id");

		SubscriberMessage subscriberMessage = new SubscriberMessage(messageId, message, publishTime, ackId);
		subscriberMessage.setId(id);
		subscriberMessage.setSubscriberName(subscriberName);
		subscriberMessage.setSubscriptionId(subscriberId);
		subscriberMessage.setPullType(pullType);
		subscriberMessage.setPullTime(pullTime);
		return subscriberMessage;
	}
	
	public String getFormattedTimestamp(ResultSet rs, String columnName) throws SQLException {
		SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_A);
		Timestamp timestamp = rs.getTimestamp(columnName);
		Date date = new Date(timestamp.getTime());
		String formattedTime = formatter.format(date);
		return formattedTime;
	}

}
