package app.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.google.pubsub.v1.PubsubMessage;

import app.dao.PublisherDao;
import app.logging.CloudLogger;
import app.model.PublisherMessage;
import app.util.DateUtility;
import app.util.ListUtils;

/**
 * Responsible for publishing PubsubMessage to Pubsub Topic
 * 
 * @author adarshs1
 *
 */
public class NotifyServiceMessagePublisher {

	private CloudLogger LOGGER = CloudLogger.getLogger();

	/**
	 * @param topics
	 * @param pubsubMessage
	 * @return messageIdList
	 */
	public List<String> publishMessage(List<String> topics, PubsubMessage pubsubMessage) {

		List<String> messageIds = new ArrayList<>();

		topics.forEach(topic -> {

			String messageId = "";

			try {
				LOGGER.info("Inside NotifyServiceMessagePublisher. Publishing message on topic: " + topic);
				GenericMessagePublisher publisher = new GenericMessagePublisher();
				messageId = publisher.publishMessage(topic, pubsubMessage);

			} catch (Exception e1) {
				e1.printStackTrace();
			}

			String stringUtf8 = pubsubMessage.getData().toStringUtf8();
			String globalTxnId = pubsubMessage.getAttributesOrThrow("globalTransactionId");
			PublisherMessage publishedMessage = new PublisherMessage(stringUtf8, topic);
			publishedMessage.setGlobalTransactionId(globalTxnId);

			publishedMessage.setPublishTime(DateUtility.getCurrentTimestamp());
			if (messageId != null && !messageId.isEmpty()) {
				publishedMessage.setMessageId(messageId);
				LOGGER.info("Inside NotifyServiceMessagePublisher. " + "Successfuly published message on topic: "
						+ topic + ", Message: \n" + publishedMessage);
				messageIds.add(messageId);
			}

			persistInDB(publishedMessage);

		});

		return messageIds;

	}

	/**
	 * @param commaSeparatedTopics
	 * @param pubsubMessage
	 * @return list of messageIds
	 */
	public List<String> publishMessage(String commaSeparatedTopics, PubsubMessage pubsubMessage) {
		List<String> topics = ListUtils.getListFromCSV(commaSeparatedTopics);
		return publishMessage(topics, pubsubMessage);

	}

	/**
	 * Insert into Publisher table
	 * 
	 * @param publisherMessage
	 * @throws ParseException
	 * @throws SQLException
	 */
	private void persistInDB(PublisherMessage publisherMessage) {
		PublisherDao publisherDao;
		try {
			publisherDao = new PublisherDao();
			publisherDao.insertPublishMessage(publisherMessage);
		} catch (SQLException | ParseException e1) {
			e1.printStackTrace();
		}
	}

}
