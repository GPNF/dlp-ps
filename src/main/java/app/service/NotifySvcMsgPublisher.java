package app.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;

import app.dao.PublisherDao;
import app.model.PublisherMessage;

/**
 * Responsible for publishing message to topic
 * 
 * @author adarshs1
 *
 */
public class NotifySvcMsgPublisher {

	private static final String YYYY_MM_DD_HH_MM_SS_A = "yyyy-MM-dd hh:mm:ss a";

	public List<String> publishMessage(List<String> topics, String message, String gbTxnId) {

		List<String> messageIds = new ArrayList<>();

		ByteString data = ByteString.copyFromUtf8(message);
		PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data)
				.putAttributes("globalTransactionId", gbTxnId).build();

		topics.forEach(topic -> {

			String messageId = "";

			try {
				GenericMessagePublisher publisher = new GenericMessagePublisher();
				messageId = publisher.publishMessage(topic, pubsubMessage);

			} catch (Exception e1) {
				e1.printStackTrace();
			}

			PublisherMessage publisherMessage = new PublisherMessage(message, topic);
			publisherMessage.setGlobalTransactionId(gbTxnId);
			SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_A);
			String publishTime = formatter.format(new Date());
			publisherMessage.setPublishTime(publishTime);
			if (messageId != null && !messageId.isEmpty()) {
				publisherMessage.setMessageId(messageId);
				messageIds.add(messageId);
			}

			persistInDB(publisherMessage);

		});

		return messageIds;

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
