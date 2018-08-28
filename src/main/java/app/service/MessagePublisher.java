package app.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutures;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.ProjectTopicName;
import com.google.pubsub.v1.PubsubMessage;

import app.DefaultApiFutureCallback;
import app.constants.Constants;
import app.dao.PublisherDao;
import app.model.PublisherMessage;

/**
 * Responsible for publishing message to topic
 * 
 * @author adarshs1
 *
 */
public class MessagePublisher {

	private static final String YYYY_MM_DD_HH_MM_SS_A = "yyyy-MM-dd hh:mm:ss a";

	public List<String> publishMessages(List<String> topics, String message, String gbTxnId) {

		List<String> messageIds = new ArrayList<>();

		topics.forEach(topic -> {
			
			//This messageId will be set on successful publish inside DefaultApiFutureCallback
			StringBuilder messageId = new StringBuilder("");
			PublisherMessage publisherMessage = new PublisherMessage(messageId.toString(), message, topic);
			publisherMessage.setGlobalTransactionId(gbTxnId);
			SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_A);
			String publishTime = formatter.format(new Date());
			publisherMessage.setPublishTime(publishTime);

			try {
				publishMessageOnTopic(topic, message, messageId, gbTxnId);
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			boolean isMessageIdGenerated = messageId.toString() != null && messageId.toString().length() > 0;

			if (isMessageIdGenerated) {
				publisherMessage.setMessageId(messageId.toString());
				messageIds.add(messageId.toString());
				try {
					persistInDB(publisherMessage);
				} catch (SQLException | ParseException e) {
					e.printStackTrace();
				}
			}
		});

		return messageIds;

	}

	/**
	 * Publish message on a topic & return messageId
	 * 
	 * @param message
	 * @param topicName
	 * @return
	 * @throws Exception
	 */
	public String publishMessageOnTopic(String topicName, String message, StringBuilder messageId, String gbTxnId)
			throws Exception {
		ProjectTopicName projectTopicName = ProjectTopicName.of(Constants.PROJECT_ID, topicName);

		Publisher publisher = null;

		try {
			// Create a publisher instance with default settings bound to the
			// topic
			publisher = Publisher.newBuilder(projectTopicName).build();
			ByteString data = ByteString.copyFromUtf8(message);
			PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data)
					.putAttributes("globalTransactionId", gbTxnId).build();
			// pubsubMessage.getAttributesMap().put("globalTransactionId",
			// gbTxnId);

			// Once published, returns a server-assigned message id (unique
			// within the topic)
			ApiFuture<String> future = publisher.publish(pubsubMessage);
			// Add an asynchronous callback to handle success / failure
			DefaultApiFutureCallback callback = new DefaultApiFutureCallback(message, messageId);
			ApiFutures.addCallback(future, callback);
			messageId = callback.getOuputMessageId();

		} finally {
			if (publisher != null) {
				// When finished with the publisher, shutdown to free up
				// resources.
				publisher.shutdown();
			}
		}
		return messageId.toString();
	}

	private void persistInDB(PublisherMessage publisher) throws SQLException, ParseException {

		PublisherDao publisherDao = new PublisherDao();
		publisherDao.insertPubliser(publisher);
	}

}
