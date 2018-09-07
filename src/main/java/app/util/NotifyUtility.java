package app.util;

import java.util.ArrayList;
import java.util.List;

import app.model.MessageStatus;
import app.model.UserDetailsSO;
import app.model.UserMessageSO;
import app.service.ProviderMsgPublisher;

/**
 * @author AmolPol
 *
 *         this class is responsible for activities related to notifications and
 *         notifiers
 */
public class NotifyUtility {
	private static final String YES = "Yes";

	/**
	 * this method prepares seperate queues for providers TODO 1.need to modify
	 * this method completely with Strategy approach for each notifier as number
	 * of notifier API's will be much more such as sendgrid and twilio.
	 * 
	 * 
	 * @param allUsers
	 * @param req
	 * @return boolean
	 */
	public void prepareMessagesWithPreferences(List<UserDetailsSO> receiverUserList, MessageStatus req) {

		List<UserMessageSO> emailPrefered = new ArrayList<>();
		List<UserMessageSO> smsPrefered = new ArrayList<>();
		// TODO Calling pubsub API to get list of topics from 2nd layer of pubsub
		
		String topics = ExternalProperties.getAppConfig("app.gc.pubsub.topic.layer2");

		String[] topicList = topics.split(",");

		for (UserDetailsSO userDet : receiverUserList) {

			if (userDet.getEmailFlag().equalsIgnoreCase(YES) && userDet.getSmsFlag().equalsIgnoreCase(YES)) {
				setEmailMsgDetails(req, emailPrefered, topicList, userDet);
				setSmsMessageDetails(req, smsPrefered, topicList, userDet);

			} else {
				UserMessageSO messagePref = new UserMessageSO();
				messagePref.setMessage(req.getMessageData());
				messagePref.setUserId(userDet.getUserId());
				messagePref.setGlobalTransactionId(req.getMessageId());
				if (userDet.getEmailFlag().equalsIgnoreCase(YES)) {
					messagePref.setTopicName(topicList[0]);
					messagePref.setEmailId(userDet.getEmailId());
					emailPrefered.add(messagePref);
				} else if (userDet.getSmsFlag().equalsIgnoreCase(YES)) {
					messagePref.setTopicName(topicList[1]);
					messagePref.setMobileNumber(userDet.getMobileNumber());
					smsPrefered.add(messagePref);
				}

			}
		}
		// message with user preferences will be published on respective topic
		publishOnSpecifcTopic(emailPrefered);
		publishOnSpecifcTopic(smsPrefered);
	}

	/**
	 * @param req
	 * @param smsPrefered
	 * @param topicList
	 * @param userDet
	 */
	private void setSmsMessageDetails(MessageStatus req, List<UserMessageSO> smsPrefered, String[] topicList,
			UserDetailsSO userDet) {
		UserMessageSO smsPrefUser;
		smsPrefUser = new UserMessageSO();
		smsPrefUser.setMessage(req.getMessageData());
		smsPrefUser.setUserId(userDet.getUserId());
		smsPrefUser.setGlobalTransactionId(req.getMessageId());
		smsPrefUser.setTopicName(topicList[1]);
		smsPrefUser.setMobileNumber(userDet.getMobileNumber());
		smsPrefered.add(smsPrefUser);
	}

	/**
	 * @param req
	 * @param emailPrefered
	 * @param topicList
	 * @param userDet
	 */
	private void setEmailMsgDetails(MessageStatus req, List<UserMessageSO> emailPrefered, String[] topicList,
			UserDetailsSO userDet) {
		UserMessageSO emailPrefUser;
		emailPrefUser = new UserMessageSO();
		emailPrefUser.setMessage(req.getMessageData());
		emailPrefUser.setUserId(userDet.getUserId());
		emailPrefUser.setGlobalTransactionId(req.getMessageId());
		emailPrefUser.setTopicName(topicList[0]);
		emailPrefUser.setEmailId(userDet.getEmailId());
		emailPrefered.add(emailPrefUser);
	}
	
	/**
	 * @param Prefered
	 */
	private void publishOnSpecifcTopic(List<UserMessageSO> preferedNotification) {
		ProviderMsgPublisher publisher = new ProviderMsgPublisher();
		if (null != preferedNotification && preferedNotification.size() > 0)
			preferedNotification.forEach(publishMessage -> {
				try {
					publisher.publishMessage(publishMessage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
	}

}