package app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.services.pubsub.model.PubsubMessage;

public class UpdateNotifierPubsubDao {

	private static final String YYYY_MM_DD_HH_MM_SS_A_Z = "yyyy-MM-dd hh:mm:ss a z";
	private Connection connection;
	Logger logger = LoggerFactory.getLogger(UpdateNotifierPubsubDao.class);

	public UpdateNotifierPubsubDao() throws SQLException {
		DBConnectionProvider connProvider = new DBConnectionProvider();
		this.connection = connProvider.getConnection();
	}

	public void insertPushedDetails(PubsubMessage userMessage) throws SQLException {
		// String query = "insert into notifier_pushed (message_id, message_data,
		// published_timestamp, global_txn_id, topic_name) VALUES (?, ?, ?, ?, ?)";

		String query = "INSERT INTO  activity_logging (message_id, message_data, subscription_name, published_timestamp, glo_tran_id, topic_name) VALUES (?, ?, ?, ?, ?, ?)";

		String formattedDate = userMessage.getPublishTime();
		SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_A_Z);
		formatter.setTimeZone(TimeZone.getTimeZone("US/Eastern"));
		Date date = new Date();
		try {
			date = formatter.parse(formattedDate);
		} catch (ParseException e) {
			logger.info(e.getMessage());
		}
		Timestamp publishTime = new Timestamp(date.getTime());

		Map<String, String> attributes = userMessage.getAttributes();
		String subscription = "";
		String provider = "";

		if (null != attributes.get("emailId")) {
			subscription = "EmailSub";
			provider = "SendGridEmail";
		} else if (null != attributes.get("mobileNumber")) {
			subscription = "SmsSub";
			provider = "TwilioSMS";
		}

		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, userMessage.getMessageId().trim());
		statement.setString(2, userMessage.getData());
		statement.setString(3, subscription);
		statement.setTimestamp(4, publishTime);
		statement.setString(5, attributes.get("globalTransactionId"));
		statement.setString(6, provider);

		statement.execute();

	}

}
