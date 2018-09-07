package app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import app.model.PublisherMessage;
import app.util.DateUtility;

/**
 * CRUD operation on publisher table
 * 
 * @author AdarshSinghal
 *
 */
public class PublisherDao {

	private Connection connection;

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
			Timestamp publishTime = DateUtility.getTimestamp(formattedDate);

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

				PublisherMessage publisher = getPublishMessage(rs);

				publishers.add(publisher);
			}
		}

		return publishers;
	}

	/**
	 * @param resultSet
	 * @return PublisherMessage
	 * @throws SQLException
	 */
	private PublisherMessage getPublishMessage(ResultSet resultSet) throws SQLException {
		String messageId = resultSet.getString("message_id");
		String topicName = resultSet.getString("topic_name");
		String message = resultSet.getString("message");
		String globalTxnId = resultSet.getString("global_txn_id");
		Timestamp time = resultSet.getTimestamp("published_timestamp");

		String formattedTime = DateUtility.getFormattedTime(time);

		PublisherMessage publisher = new PublisherMessage(message, topicName, messageId, formattedTime);
		publisher.setGlobalTransactionId(globalTxnId);
		return publisher;
	}

}
