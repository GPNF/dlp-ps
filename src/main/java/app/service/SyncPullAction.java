package app.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.google.cloud.pubsub.v1.stub.GrpcSubscriberStub;
import com.google.cloud.pubsub.v1.stub.SubscriberStub;
import com.google.cloud.pubsub.v1.stub.SubscriberStubSettings;
import com.google.pubsub.v1.AcknowledgeRequest;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.PullRequest;
import com.google.pubsub.v1.PullResponse;
import com.google.pubsub.v1.ReceivedMessage;

import app.constants.Constants;

/**
 * This class acts as <b>Sync Pull</b> Client. There are two types of Pull in PubSub -
 * Sync &amp; Async
 * 
 * <br><br>
 * Sync Pull supports following customization:-
 * <br>
 * <b>Maximum Messages</b> - The Pub/Sub system may return fewer than the number
 * specified. This represents the Client's capability of handling messages.<br>
 * <b>Return Immediately</b> field when set to true, the system will respond
 * immediately even if it there are no messages available to return in the
 * `Pull` response. Otherwise, the system may wait (for a bounded amount of
 * time) until at least one message is available, rather than returning no
 * messages.
 * 
 * @author AdarshSinghal
 *
 */
public class SyncPullAction {

	private final String projectId = Constants.PROJECT_ID;
	private final String subscriptionId = Constants.SUBSCRIPTION_ID;

	/**
	 * Pull the messages from PubSub. Accepts two params - Max Message, Return
	 * Immediately <br>
	 * <br>
	 * <b>Maximum Messages</b> - The Pub/Sub system may return fewer than the
	 * number specified. This represents the Client's capability of handling
	 * messages.<br>
	 * <br>
	 * If <b>Return Immediately</b> field set to true, the system will respond
	 * immediately even if it there are no messages available to return in the
	 * `Pull` response. Otherwise, the system may wait (for a bounded amount of
	 * time) until at least one message is available, rather than returning no
	 * messages.
	 * 
	 * 
	 * @param numOfMessages
	 * @param returnImmediately
	 * @return List of ReceivedMessage
	 * @throws IOException
	 * 
	 */
	public List<ReceivedMessage> getReceivedMessages(int numOfMessages, boolean returnImmediately) throws IOException {
		SubscriberStubSettings subscriberStubSettings = SubscriberStubSettings.newBuilder().build();
		SubscriberStub subscriber = GrpcSubscriberStub.create(subscriberStubSettings);
		String subscriptionName = ProjectSubscriptionName.format(projectId, subscriptionId);
		PullRequest pullRequest = PullRequest.newBuilder().setMaxMessages(numOfMessages)
				.setReturnImmediately(returnImmediately).setSubscription(subscriptionName).build();

		// Using pullCallable().futureCall to asynchronously perform this
		// operation
		PullResponse pullResponse = subscriber.pullCallable().call(pullRequest);
		List<String> ackIds = pullResponse.getReceivedMessagesList().stream().map(message -> message.getAckId())
				.collect(Collectors.toList());
		if (!ackIds.isEmpty()) { // acknowledge received messages
			AcknowledgeRequest acknowledgeRequest = AcknowledgeRequest.newBuilder().setSubscription(subscriptionName)
					.addAllAckIds(ackIds).build();
			// Using acknowledgeCallable().futureCall to asynchronously perform
			// this
			// operation
			subscriber.acknowledgeCallable().call(acknowledgeRequest);
		}
		subscriber.shutdown();
		return pullResponse.getReceivedMessagesList();
	}
}
