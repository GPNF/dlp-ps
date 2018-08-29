package app.dao;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import app.model.LoggingModel;

public class LoggingDAO {

	private Connection connection;

	public LoggingDAO() throws SQLException {
		super();
		DBConnectionProvider connProvider = new DBConnectionProvider();
		connection = connProvider.getConnection();
	}

	public List<LoggingModel> getAllFieldDetails() throws SQLException, UnsupportedEncodingException {
		LoggingModel loggingModelObject;
		List<LoggingModel> loggingEntryList = new ArrayList<>();
		final String sql = "SELECT" + " id, message_id, message_data, subscription_name,"
				+ "  published_timestamp, glo_tran_id, topic_name FROM activity_logging";

		PreparedStatement ps = connection.prepareStatement(sql);

		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			loggingModelObject = getTableDetails(rs);
			loggingEntryList.add(loggingModelObject);
		}

		return loggingEntryList;
	}

	public LoggingModel getTableDetails(ResultSet rs) throws SQLException, UnsupportedEncodingException {

		String b64msg = rs.getString("message_data");
		byte[] decoded = Base64.getMimeDecoder().decode(b64msg.getBytes("UTF-8"));

		return new LoggingModel.LoggingBuilder().setAutoIncrId(rs.getString("id"))
				.setMessageId(rs.getString("message_id")).setMessageData(new String(decoded))
				.setSubscriptionName(rs.getString("subscription_name"))
				.setPublishedTimestamp(rs.getString("published_timestamp"))
				.setGlobalTransactionId(rs.getString("glo_tran_id")).setTopicName(rs.getString("topic_name"))
				.createInstance();
	}
}
