package app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
	
	public void insertPushedDetails(PubsubMessage userMessage)
	{
		//	String query = "insert into notifier_pushed (message_id, message_data, published_timestamp, global_txn_id, topic_name) VALUES (?, ?, ?, ?, ?)";
			
			String query = "insert into  activity_logging (message_id, message_data, subscription_name, published_timestamp, glo_tran_id, topic_name) VALUES (?, ?, ?, ?, ?, ?)";
			
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

		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, userMessage.getMessageId().trim());
			statement.setString(2, userMessage.getData());

			statement.setTimestamp(4, publishTime);
			statement.setString(5, userMessage.getAttributes().get("globalTransactionId"));
			if (null != userMessage.getAttributes().get("emailId")) {
				statement.setString(3, "EmailSub");
				statement.setString(6, "SendGridEmail");
			} else if (null != userMessage.getAttributes().get("mobileNumber")) {

				statement.setString(3, "SmsSub");
				statement.setString(6, "TwilioSMS");
			}

			statement.execute();
		} catch (SQLException e1) {
				e1.printStackTrace();
			}

	} 
	
		
		
		
		
	}
	
	
	

