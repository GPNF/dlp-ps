package app.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.pubsub.v1.PubsubMessage;

import app.dao.PublisherDao;
import app.model.PublisherMessage;
import app.util.ListUtils;

/**
 * Responsible for publishing PubsubMessage to Pubsub Topic
 * 
 * @author adarshs1
 *
 */
public class NotifyServiceMessagePublisher {

	private static final String YYYY_MM_DD_HH_MM_SS_A = "yyyy-MM-dd hh:mm:ss a";

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
				GenericMessagePublisher publisher = new GenericMessagePublisher();
				messageId = publisher.publishMessage(topic, pubsubMessage);

			} catch (Exception e1) {
				e1.printStackTrace();
			}

			String stringUtf8 = pubsubMessage.getData().toStringUtf8();
			String globalTxnId = pubsubMessage.getAttributesOrThrow("globalTransactionId");
			PublisherMessage publishedMessage = new PublisherMessage(stringUtf8, topic);
			publishedMessage.setGlobalTransactionId(globalTxnId);
			
			SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_A);
			String publishTime = formatter.format(new Date());
			publishedMessage.setPublishTime(publishTime);
			if (messageId != null && !messageId.isEmpty()) {
				publishedMessage.setMessageId(messageId);
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

	/** Insert into Publisher table
	 * @param publisherMessage
	 */
	private void persistInDB(PublisherMessage publisherMessage) {
		try {
			PublisherDao publisherDao = new PublisherDao();
			publisherDao.insertPublishMessage(publisherMessage);
		} catch (SQLException | ParseException e) {
			e.printStackTrace();
		}
	}

}
