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

import app.model.PublisherMessage;

public class PublisherDao {

	private static final String YYYY_MM_DD_HH_MM_SS_A = "yyyy-MM-dd hh:mm:ss a";
	Connection connection;

	public PublisherDao() throws SQLException {
		DBConnectionProvider connProvider = new DBConnectionProvider();
		this.connection = connProvider.getConnection();
	}

	public int insertPubliser(PublisherMessage publisher) throws SQLException, ParseException {

		String sql = "INSERT INTO publisher " + "(message_id, topic_name, message, published_timestamp, global_txn_id) "
				+ "VALUES (?,?,?,?,?)";

		PreparedStatement ps = connection.prepareStatement(sql);

		String formattedDate = publisher.getPublishTime();
		SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_A);
		Date date = formatter.parse(formattedDate);

		Timestamp publishTime = new Timestamp(date.getTime());

		ps.setString(1, publisher.getMessageId());
		ps.setString(2, publisher.getTopicName());
		ps.setString(3, publisher.getMessage());
		ps.setTimestamp(4, publishTime);
		ps.setString(5, publisher.getGlobalTransactionId());

		int rowsAffected = ps.executeUpdate();
		return rowsAffected;

	}

	public List<PublisherMessage> getAllPublishers() throws SQLException {
		List<PublisherMessage> publishers = new ArrayList<>();

		String sql = "SELECT * FROM publisher";
		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {

			String messageId = rs.getString("message_id");
			String topicName = rs.getString("topic_name");
			String message = rs.getString("message");
			String globalTxnId = rs.getString("global_txn_id");
			Timestamp time = rs.getTimestamp("published_timestamp");
			Date publishTime = new Date(time.getTime());

			SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_A);
			String formattedDate = formatter.format(publishTime);

			PublisherMessage publisher = new PublisherMessage(messageId, message, topicName, formattedDate);
			publisher.setGlobalTransactionId(globalTxnId);
			
			publishers.add(publisher);
		}

		return publishers;

	}

}
