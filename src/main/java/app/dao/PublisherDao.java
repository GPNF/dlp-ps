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
import java.util.logging.Logger;

import app.model.PublisherMessage;

/**
 * CRUD operation on publisher table
 * 
 * @author AdarshSinghal
 *
 */
public class PublisherDao {

	private static final String YYYY_MM_DD_HH_MM_SS_A_Z = "yyyy-MM-dd hh:mm:ss a z";
	private Connection connection;
	private static final Logger LOGGER = Logger.getLogger(PublisherDao.class.getName());

	public PublisherDao() throws SQLException {
		DBConnectionProvider connProvider = new DBConnectionProvider();
		this.connection = connProvider.getConnection();
	}

	@Override
	protected void finalize() throws Throwable {
		if (connection != null)
			connection.close();
	}

	/**
	 * @param publisher
	 * @return no. of rows modified
	 * @throws SQLException
	 * @throws ParseException
	 */
	public int insertPublishMessage(PublisherMessage publisher) throws SQLException, ParseException {

		String sql = "INSERT INTO publisher " + "(message_id, topic_name, message, published_timestamp, global_txn_id) "
				+ "VALUES (?,?,?,?,?)";

		int rowsAffected = -1;

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			String formattedDate = publisher.getPublishTime();
			SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_A_Z);
			formatter.setTimeZone(TimeZone.getTimeZone("US/Eastern"));
			Date date = new Date();
			try {
				date = formatter.parse(formattedDate);
			} catch (ParseException e) {
				LOGGER.severe(e.getMessage());
			}

			Timestamp publishTime = new Timestamp(date.getTime());

			ps.setString(1, publisher.getMessageId());
			ps.setString(2, publisher.getTopicName());
			ps.setString(3, publisher.getMessage());
			ps.setTimestamp(4, publishTime);
			ps.setString(5, publisher.getGlobalTransactionId());
			rowsAffected = ps.executeUpdate();
		}

		return rowsAffected;

	}

	/**
	 * @return List of Publish Messages
	 * @throws SQLException
	 */
	public List<PublisherMessage> getAllPublishers() throws SQLException {
		List<PublisherMessage> publishers = new ArrayList<>();

		String sql = "SELECT * FROM publisher";
		PreparedStatement ps = connection.prepareStatement(sql);

		try (ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {

				String messageId = rs.getString("message_id");
				String topicName = rs.getString("topic_name");
				String message = rs.getString("message");
				String globalTxnId = rs.getString("global_txn_id");
				Timestamp time = rs.getTimestamp("published_timestamp");
				Date publishTime = new Date(time.getTime());

				SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_A_Z);
				formatter.setTimeZone(TimeZone.getTimeZone("US/Eastern"));
				String formattedDate = formatter.format(publishTime);

				PublisherMessage publisher = new PublisherMessage(message, topicName, messageId, formattedDate);
				publisher.setGlobalTransactionId(globalTxnId);

				publishers.add(publisher);
			}
		}

		return publishers;
	}

}
