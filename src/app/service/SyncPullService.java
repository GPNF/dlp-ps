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

public class SyncPullService {

	private final String projectId = "possible-haven-212003";
	private final String subscriptionId = "sub1";

	public List<ReceivedMessage> getReceivedMessages(int numOfMessages, boolean returnImmediately) throws IOException {
		SubscriberStubSettings subscriberStubSettings = SubscriberStubSettings.newBuilder().build();
		SubscriberStub subscriber = GrpcSubscriberStub.create(subscriberStubSettings);
		String subscriptionName = ProjectSubscriptionName.format(projectId, subscriptionId);
		System.out.println("Provided maxMessage: " + numOfMessages + " & Return Immediately: " + returnImmediately);
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
		System.out.println("No. of received messages: " + pullResponse.getReceivedMessagesList().size());
		subscriber.shutdown();
		return pullResponse.getReceivedMessagesList();
	}
}
