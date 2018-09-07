package app.service;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutures;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.pubsub.v1.ProjectTopicName;
import com.google.pubsub.v1.PubsubMessage;

import app.constants.Constants;

/**
 * This class can be used for publishing on single topic. All required message
 * attributes needs to be set before using publish method.
 * 
 * @author AdarshSinghal
 *
 */
public class GenericMessagePublisher {

	/**
	 * Publish message on a topic & return messageId.<br>
	 * <br>
	 * If required, Set PubsubMessage attributes before using publish method.
	 * 
	 * @param topicName
	 * @param pubsubMessage
	 * @return messageId - String
	 * @throws Exception
	 */
	public String publishMessage(String topicName, PubsubMessage pubsubMessage) throws Exception {

		ProjectTopicName projectTopicName = ProjectTopicName.of(Constants.PROJECT_ID, topicName);

		Publisher publisher = null;
		publisher = Publisher.newBuilder(projectTopicName).build();
		ApiFuture<String> future = publisher.publish(pubsubMessage);

		// Add an asynchronous callback to handle success / failure
		DefaultApiFutureCallback callback = new DefaultApiFutureCallback(pubsubMessage.getData().toStringUtf8());
		ApiFutures.addCallback(future, callback);
		StringBuilder messageId = callback.getOuputMessageId();

		if (publisher != null) {
			// When finished with the publisher, shutdown to free up
			// resources.
			publisher.shutdown();
		}
		return messageId.toString();
	}

}
