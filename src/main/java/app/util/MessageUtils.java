package app.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

import com.google.protobuf.Timestamp;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.ReceivedMessage;

import app.constants.Constants;
import app.model.SubscriberMessage;

public class MessageUtils {

	private static final String YYYY_MM_DD_HH_MM_SS_A_Z = "yyyy-MM-dd hh:mm:ss a z";

	private static SubscriberMessage getSubscriberMessage(ReceivedMessage msg) {
		String ackId = msg.getAckId();
		PubsubMessage pubsubMsg = msg.getMessage();
		String msgId = pubsubMsg.getMessageId();
		String data = pubsubMsg.getData().toStringUtf8();
		String globaTxnId = pubsubMsg.getAttributesOrThrow("globalTransactionId");
		Timestamp timestamp = pubsubMsg.getPublishTime();
		long time = timestamp.getSeconds();

		SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_A_Z);
		Date publishDate = new Date(time * 1000);
		formatter.setTimeZone(TimeZone.getTimeZone("EST"));
		String publishTime = formatter.format(publishDate);
		Date today = new Date();
		String pullTime = formatter.format(today);

		SubscriberMessage subMsg = new SubscriberMessage(msgId, data, publishTime, ackId, globaTxnId);
		String subscriptionId = Constants.SUBSCRIPTION_ID;
		subMsg.setSubscriptionId(subscriptionId);
		subMsg.setPullTime(pullTime);
		return subMsg;
	}

	public static List<SubscriberMessage> getSubscriberMessages(List<ReceivedMessage> receivedMessages) {
		return receivedMessages.stream().map(msg -> getSubscriberMessage(msg)).collect(Collectors.toList());
	}

}
