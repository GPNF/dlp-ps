package app.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.model.LoggingModel;

import app.msgstatuscache.utils.ConfigParams;
import app.msgstatuscache.utils.PropertyParserAndConfigAdapter;

public class LoggingDAO {
	private ConfigParams params;
	private PropertyParserAndConfigAdapter adapter;

	public LoggingDAO() {
		super();
		this.adapter = new PropertyParserAndConfigAdapter("logging_configuration.properties");
		this.params = this.adapter.readPropertiesAndSetParameters();
	}

	public List<LoggingModel> getAllFieldDetails() {
		LoggingModel loggingModelObject;
		List<LoggingModel> loggingEntryList = new ArrayList();
		final String sql = "SELECT" + " id, message_id, message_data, subscription_name,"
				+ "  published_timestamp, glo_tran_id, topic_name FROM activity_logging";
		try (ResultSet rs = this.params.getConn().prepareStatement(sql).executeQuery()) {
			while (rs.next()) {
				loggingModelObject = getTableDetails(rs);
				loggingEntryList.add(loggingModelObject);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return loggingEntryList;
	}

	public LoggingModel getTableDetails(ResultSet rs) throws SQLException {
		return new LoggingModel.LoggingBuilder().setAutoIncrId(rs.getString("id"))
				.setMessageId(rs.getString("message_id")).setMessageData(rs.getString("message_data"))
				.setSubscriptionName(rs.getString("subscription_name"))
				.setPublishedTimestamp(rs.getString("published_timestamp"))
				.setGlobalTransactionId(rs.getString("glo_tran_id")).setTopicName(rs.getString("topic_name"))
				.createInstance();
	}
}
