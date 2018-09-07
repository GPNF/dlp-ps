package app.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.google.protobuf.Timestamp;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.ReceivedMessage;

import app.constants.Constants;
import app.model.SubscriberMessage;

/**
 * This utility is responsible for <b>convert</b>ing
 * <b>List&lt;ReceivedMessage&gt;</b> into <b>List&lt;SubscriberMessage&gt;</b>.
 * To obtain List<ReceivedMessage> getReceivedMessages() is called on
 * SyncPullAction. <br>
 * <br>
 * ReceivedMessage is part of PubSub Java API. SubscriberMessage is POJO.
 * 
 * @author AdarshSinghal
 *
 */
public class MessageUtils {

	/**
	 *  Converter ReceivedMessage into SubscriberMessage
	 * 
	 * @param message
	 * @return SubscriberMessage
	 */
	private static SubscriberMessage getSubscriberMessage(ReceivedMessage message) {
		String ackId = message.getAckId();
		PubsubMessage pubsubMsg = message.getMessage();
		String msgId = pubsubMsg.getMessageId();
		String data = pubsubMsg.getData().toStringUtf8();
		String globaTxnId = pubsubMsg.getAttributesOrThrow("globalTransactionId");
		Timestamp timestamp = pubsubMsg.getPublishTime();
		long time = timestamp.getSeconds();

		SimpleDateFormat formatter = DateUtility.getDefaultFormatter();
		Date publishDate = new Date(time * 1000);

		String publishTime = formatter.format(publishDate);
		Date today = new Date();
		String pullTime = formatter.format(today);

		SubscriberMessage subMsg = new SubscriberMessage(msgId, data, publishTime, ackId, globaTxnId);
		String subscriptionId = Constants.SUBSCRIPTION_ID;
		subMsg.setSubscriptionId(subscriptionId);
		subMsg.setPullTime(pullTime);

		subMsg.setDestGroupId(pubsubMsg.getAttributesOrDefault("destGroupId",null));
		return subMsg;
	}

	/**
	 * Converts List of ReceivedMessage into List of SubscriberMessage
	 * 
	 * @param receivedMessages
	 * @return
	 */
	public static List<SubscriberMessage> getSubscriberMessages(List<ReceivedMessage> receivedMessages) {
		return receivedMessages.stream().map(msg -> getSubscriberMessage(msg)).collect(Collectors.toList());
	}

}
