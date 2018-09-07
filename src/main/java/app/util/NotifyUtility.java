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
	 * this method prepares seperate queues for providers
	 * 
	 * @param allUsers
	 * @param req
	 * @return boolean
	 */
	public void prepareMessagesWithPreferences(List<UserDetailsSO> receiverUserList, MessageStatus req) {

		List<UserMessageSO> emailPrefered = new ArrayList<>();
		List<UserMessageSO> smsPrefered = new ArrayList<>();
		UserMessageSO emailPrefUser = null;
		UserMessageSO smsPrefUser = null;
		String topics = ExternalProperties.getAppConfig("app.gc.pubsub.topic.layer2");

		String[] topicList = topics.split(",");

		for (UserDetailsSO userDet : receiverUserList) {

			if (userDet.getEmailFlag().equalsIgnoreCase(YES) && userDet.getSmsFlag().equalsIgnoreCase(YES)) {
				emailPrefUser = new UserMessageSO();
				emailPrefUser.setMessage(req.getMessageData());
				emailPrefUser.setUserId(userDet.getUserId());
				emailPrefUser.setGlobalTransactionId(req.getMessageId());
				emailPrefUser.setTopicName(topicList[0]);
				emailPrefUser.setEmailId(userDet.getEmailId());
				emailPrefered.add(emailPrefUser);

				smsPrefUser = new UserMessageSO();
				smsPrefUser.setMessage(req.getMessageData());
				smsPrefUser.setUserId(userDet.getUserId());
				smsPrefUser.setGlobalTransactionId(req.getMessageId());
				smsPrefUser.setTopicName(topicList[1]);
				smsPrefUser.setMobileNumber(userDet.getMobileNumber());
				smsPrefered.add(smsPrefUser);

			} else {
				if (userDet.getEmailFlag().equalsIgnoreCase(YES)) {
					emailPrefUser = new UserMessageSO();
					emailPrefUser.setMessage(req.getMessageData());
					emailPrefUser.setUserId(userDet.getUserId());
					emailPrefUser.setGlobalTransactionId(req.getMessageId());
					emailPrefUser.setTopicName(topicList[0]);
					emailPrefUser.setEmailId(userDet.getEmailId());
					emailPrefered.add(emailPrefUser);
				} else if (userDet.getSmsFlag().equalsIgnoreCase(YES)) {
					smsPrefUser = new UserMessageSO();
					smsPrefUser.setMessage(req.getMessageData());
					smsPrefUser.setUserId(userDet.getUserId());
					smsPrefUser.setGlobalTransactionId(req.getMessageId());
					smsPrefUser.setTopicName(topicList[1]);
					smsPrefUser.setMobileNumber(userDet.getMobileNumber());
					smsPrefered.add(smsPrefUser);
				}

			}
		}

		publishOnSpecifcTopic(emailPrefered);

		publishOnSpecifcTopic(smsPrefered);
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
	}

}