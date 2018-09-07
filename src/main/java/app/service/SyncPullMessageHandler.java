package app.service;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import com.google.pubsub.v1.ReceivedMessage;

import app.dao.SubscriberDao;
import app.model.SubscriberMessage;
import app.util.MessageUtils;

/**
 * Uses Sync Pull Action, the class responsible for pulling message from PubSub.
 * Also, add an entry to DB through a call to SubscriberDao.
 * 
 * @author adarshsinghal
 *
 */
public class SyncPullMessageHandler {

	
	/**
	 * Uses Sync Pull Action to pull the messages from PubSub
	 * 
	 * @param maxMessageStr
	 * @param returnImmediatelyStr
	 * @return List of SubscriberMessage
	 * @throws IOException
	 * @throws ParseException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
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

	/**
	 * Uses SubscriberDao to perform CRUD operation on subscriber table
	 * 
	 * @param messageList
	 * @throws ParseException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
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
