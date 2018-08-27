package app.service;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutures;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.ProjectTopicName;
import com.google.pubsub.v1.PubsubMessage;

import app.DefaultApiFutureCallback;
import app.constants.Constants;
import app.model.PublisherMessage;

/**
 * Responsible for publishing message to topic
 * 
 * @author adarshs1
 *
 */
public class MessagePublisher {

	private static final String PROJECT_ID = Constants.PROJECT_ID;

	/**
	 * Publish message on a topic & return messageId
	 * 
	 * @param message
	 * @param topicName
	 * @return
	 * @throws Exception
	 */
	public void publishMessage(String topicName, PublisherMessage publisherMessage, StringBuilder messageId,
			String gbTxnId) throws Exception {
		ProjectTopicName projectTopicName = ProjectTopicName.of(PROJECT_ID, topicName);

		Publisher publisher = null;
		String message = publisherMessage.getMessage();

		boolean isEmptyTopic = topicName == null || topicName.isEmpty();
		boolean isEmptyMessage = message == null || message.isEmpty();
		if (isEmptyTopic || isEmptyMessage)
			return;

		try {
			// Create a publisher instance with default settings bound to the
			// topic
			publisher = Publisher.newBuilder(projectTopicName).build();
			ByteString data = ByteString.copyFromUtf8(message);
			PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data)
					.putAttributes("globalTransactionId", gbTxnId).build();
			publisherMessage.setGlobalTransactionId(gbTxnId);
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
	}

}
