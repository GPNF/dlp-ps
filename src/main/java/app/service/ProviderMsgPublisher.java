package app.service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
//import com.google.api.services.pubsub.model.PubsubMessage;
import com.google.pubsub.v1.PubsubMessage.Builder;

import app.dao.PublisherDao;
import app.dao.UpdateNotifierPubsubDao;
import app.model.PublisherMessage;
import app.model.UserMessageSO;

public class ProviderMsgPublisher {
	
	private static final String YYYY_MM_DD_HH_MM_SS_A = "yyyy-MM-dd hh:mm:ss a";
	
	public boolean publishMessage(UserMessageSO publishMessage)
	{
		boolean published=false;

		List<String> messageIds = new ArrayList<>();

		ByteString data = ByteString.copyFromUtf8(publishMessage.getMessage());
		Builder builder = PubsubMessage.newBuilder().setData(data);
		builder.putAttributes("globalTransactionId", publishMessage.getGlobalTransactionId());
		if (null != publishMessage.getEmailId() && null != publishMessage.getMobileNumber()) {
			builder.putAttributes("emailId", publishMessage.getEmailId());
			builder.putAttributes("mobileNumber", publishMessage.getMobileNumber());
		} else {
			if (null != publishMessage.getEmailId() && "" != publishMessage.getEmailId()) {

				builder.putAttributes("emailId", publishMessage.getEmailId());
				
			} else if (null != publishMessage.getMobileNumber() && "" != publishMessage.getMobileNumber()) {
				builder.putAttributes("mobileNumber", publishMessage.getMobileNumber());
			}
		}

		PubsubMessage pubsubMessage = builder.build();

		String messageId = "";

		try {
			GenericMessagePublisher publisher = new GenericMessagePublisher();
			messageId = publisher.publishMessage(publishMessage.getTopicName(), pubsubMessage);

			System.out.println("pubsubMessage publishing " + pubsubMessage);

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		PublisherMessage publisherMessage = new PublisherMessage(publishMessage.getMessage(),
				publishMessage.getTopicName());
		publisherMessage.setGlobalTransactionId(publishMessage.getGlobalTransactionId());
		SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_A);
		String publishTime = formatter.format(new Date());
		publisherMessage.setPublishTime(publishTime);
		if (messageId != null && !messageId.isEmpty()) {
			publisherMessage.setMessageId(messageId);
			messageIds.add(messageId);
		}

		persistInDB(publisherMessage);
		published=true;
		
		return published;
		// persistInDb(pubsubMessage);
	}
	
	private void persistInDB(PublisherMessage publisherMessage) {
		try {
			PublisherDao publisherDao = new PublisherDao();
			publisherDao.insertPubliser(publisherMessage);
		} catch (SQLException | ParseException e) {
			e.printStackTrace();
		}
	}
	

}
