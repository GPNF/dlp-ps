package app.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
//import com.google.api.services.pubsub.model.PubsubMessage;
import com.google.pubsub.v1.PubsubMessage.Builder;

import app.dao.PublisherDao;
import app.model.PublisherMessage;
import app.model.UserMessageSO;

/**
 * 
 * Responsible for publishing message on Sendgrid and twilio topics based on
 * preferences
 * 
 * @author amolp
 *
 */
public class ProviderMsgPublisher {

	private static final String YYYY_MM_DD_HH_MM_SS_A = "yyyy-MM-dd hh:mm:ss a";

	/**
	 * this method receives messages and publish them on topics and logs details
	 * in publisher table
	 * 
	 * @param publishMessage
	 * @throws Exception
	 */
	public void publishMessage(UserMessageSO publishMessage) throws Exception  {

		ByteString data = ByteString.copyFromUtf8(publishMessage.getMessage());
		Builder builder = PubsubMessage.newBuilder().setData(data);
		builder.putAttributes("globalTransactionId", publishMessage.getGlobalTransactionId());

		if (null != publishMessage.getEmailId() && "" != publishMessage.getEmailId()) {

			builder.putAttributes("emailId", publishMessage.getEmailId());

		} else if (null != publishMessage.getMobileNumber() && "" != publishMessage.getMobileNumber()) {
			
			builder.putAttributes("mobileNumber", publishMessage.getMobileNumber());
		}

		PubsubMessage pubsubMessage = builder.build();

		String messageId = "";

		GenericMessagePublisher publisher = new GenericMessagePublisher();
		messageId = publisher.publishMessage(publishMessage.getTopicName(), pubsubMessage);

		PublisherMessage publisherMessage = new PublisherMessage(publishMessage.getMessage(),
				publishMessage.getTopicName());
		publisherMessage.setGlobalTransactionId(publishMessage.getGlobalTransactionId());
		SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_A);
		String publishTime = formatter.format(new Date());
		publisherMessage.setPublishTime(publishTime);
		if (messageId != null && !messageId.isEmpty()) {
			publisherMessage.setMessageId(messageId);
		}
		// persist published details in publisher table
		persistInDB(publisherMessage);

	}

	private void persistInDB(PublisherMessage publisherMessage) throws SQLException, ParseException {

		PublisherDao publisherDao = new PublisherDao();
		publisherDao.insertPublishMessage(publisherMessage);

	}

}
