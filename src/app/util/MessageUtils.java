package app.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.google.protobuf.Timestamp;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.ReceivedMessage;

import app.model.SubscriberMessage;

public class MessageUtils {

	private static final String YYYY_MM_DD_HH_MM_SS_A = "yyyy-MM-dd hh:mm:ss a";

	private static SubscriberMessage getSubscriberMessage(ReceivedMessage msg, String subscriptionId, String subscriberName, String pullType) {
		String ackId = msg.getAckId();
		PubsubMessage pubsubMsg = msg.getMessage();
		String msgId = pubsubMsg.getMessageId();
		String data = pubsubMsg.getData().toStringUtf8();
		Timestamp timestamp = pubsubMsg.getPublishTime();
		long time = timestamp.getSeconds();

		SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_A);
		Date publishDate = new Date(time * 1000);
		String publishTime = formatter.format(publishDate);
		Date today = new Date();
		String pullTime = formatter.format(today);
		
		SubscriberMessage subMsg = new SubscriberMessage(msgId, data, publishTime, ackId);
		subMsg.setSubscriptionId(subscriptionId);
		subMsg.setPullTime(pullTime);
		subMsg.setPullType(pullType);
		subMsg.setSubscriberName(subscriberName);
		return subMsg;
	}

	public static List<SubscriberMessage> getSubscriberMessages(List<ReceivedMessage> receivedMessages,
			String subscriptionId, String subscriberName, String pullType) {
		return receivedMessages.stream().map(msg -> getSubscriberMessage(msg, subscriptionId, subscriberName, pullType))
				.collect(Collectors.toList());
	}

}
