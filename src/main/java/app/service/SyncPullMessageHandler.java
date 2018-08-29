package app.service;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import com.google.pubsub.v1.ReceivedMessage;

import app.dao.SubscriberDao;
import app.model.SubscriberMessage;
import app.util.MessageUtils;

public class SyncPullMessageHandler {

	public List<SubscriberMessage> pullMessages(String maxMessageStr, String returnImmediatelyStr) throws IOException {
		int maxMessage = Integer.parseInt(maxMessageStr);

		boolean returnImmediately = Boolean.parseBoolean(returnImmediatelyStr);

		SyncPullAction syncPullAction = new SyncPullAction();
		List<ReceivedMessage> receivedMessages = syncPullAction.getReceivedMessages(maxMessage, returnImmediately);
		List<SubscriberMessage> messageList = MessageUtils.getSubscriberMessages(receivedMessages);

		if (messageList != null && !messageList.isEmpty()) {
			persistInDB(messageList);
		}

		return messageList;
	}

	private void persistInDB(List<SubscriberMessage> messageList) {
		SubscriberDao subscriberDao;
		try {
			subscriberDao = new SubscriberDao();
			subscriberDao.insertMessages(messageList);
		} catch (SQLException | ParseException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
