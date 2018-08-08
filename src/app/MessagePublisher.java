package app;

import java.util.ArrayList;
import java.util.List;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutures;
import com.google.cloud.ServiceOptions;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.ProjectTopicName;
import com.google.pubsub.v1.PubsubMessage;

public class MessagePublisher {

	private static final String PROJECT_ID = ServiceOptions.getDefaultProjectId();

	/**
	 * Publish message on a topic & return messageId
	 * 
	 * @param message
	 * @param topicName
	 * @return
	 * @throws Exception
	 */
/*	
	public static void main(String[] args) throws Exception {
		MessagePublisher publisher = new MessagePublisher();
		
		List<String> messageIds = new ArrayList<>();
		
		for (int i = 0; i < 10; i++) {
			StringBuilder messageId = new StringBuilder("");
			String message = "This is message "+(i+1);
			publisher.publishMessage(message, "NSE",messageId );
			messageIds.add(messageId.toString());
		}
		
		System.out.println(messageIds.size() + " messages published");

	}*/
	
	
	public void publishMessage(String message, String topicName, StringBuilder messageId) throws Exception {
		ProjectTopicName projectTopicName = ProjectTopicName.of(PROJECT_ID, topicName);
		Publisher publisher = null;

		boolean isEmptyTopic = topicName == null || topicName.isEmpty();
		boolean isEmptyMessage = message == null || message.isEmpty();
		if (isEmptyTopic || isEmptyMessage)
				return;

		try {
			// Create a publisher instance with default settings bound to the
			// topic
			publisher = Publisher.newBuilder(projectTopicName).build();

			ByteString data = ByteString.copyFromUtf8(message);
			PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data).build();

			// Once published, returns a server-assigned message id (unique
			// within the topic)
			ApiFuture<String> future = publisher.publish(pubsubMessage);

			// Add an asynchronous callback to handle success / failure
			DefaultApiFutureCallback callback = new DefaultApiFutureCallback(message, messageId);
			ApiFutures.addCallback(future, callback);
			callback.getOuputMessageId();

		} finally {
			if (publisher != null) {
				// When finished with the publisher, shutdown to free up
				// resources.
				publisher.shutdown();
			}
		}
	}

}
