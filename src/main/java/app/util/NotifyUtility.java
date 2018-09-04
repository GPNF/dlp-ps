package app.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.google.api.services.pubsub.model.PubsubMessage;

import app.model.MessageStatus;
import app.model.UserDetailsSO;
import app.model.UserMessageSO;
import app.service.ProviderMsgPublisher;
import app.service.thirdparty.SendGridEmailClient;
import app.service.thirdparty.TwilioSmsClient;
import app.servlet.HttpClientRequestHandler;

/**
 * @author AmolPol
 *
 */
public class NotifyUtility {
	private static final String YES = "Yes";

	/**
	 * @param allUsers
	 * @param req
	 * @return boolean
	 */
	public boolean publishUserMessage(List<UserDetailsSO> allUsers, MessageStatus req) {

		List<UserMessageSO> emailPrefered = new ArrayList<>();
		List<UserMessageSO> smsPrefered = new ArrayList<>();
		UserMessageSO emailPrefUser = null;
		UserMessageSO smsPrefUser = null;
		String topics = ExternalProperties.getAppConfig("app.gc.pubsub.topic.layer2");

		String[] topicList = topics.split(",");

		for (UserDetailsSO userDet : allUsers) {

			if (userDet.getEmailFlag().equalsIgnoreCase(YES) && userDet.getSmsFlag().equalsIgnoreCase(YES)) {
				emailPrefUser = new UserMessageSO();
				emailPrefUser.setMessage(req.getMessageData());
				emailPrefUser.setUserId(userDet.getUserId());
				emailPrefUser.setGlobalTransactionId(req.getMessageId());
				emailPrefUser.setTopicName(topicList[1]);
				emailPrefUser.setEmailId(userDet.getEmailId());
				emailPrefered.add(emailPrefUser);

				smsPrefUser = new UserMessageSO();
				smsPrefUser.setMessage(req.getMessageData());
				smsPrefUser.setUserId(userDet.getUserId());
				smsPrefUser.setGlobalTransactionId(req.getMessageId());
				smsPrefUser.setTopicName(topicList[0]);
				smsPrefUser.setMobileNumber(userDet.getMobileNumber());
				smsPrefered.add(smsPrefUser);

			} else {
				if (userDet.getEmailFlag().equalsIgnoreCase(YES)) {
					emailPrefUser = new UserMessageSO();
					emailPrefUser.setMessage(req.getMessageData());
					emailPrefUser.setUserId(userDet.getUserId());
					emailPrefUser.setGlobalTransactionId(req.getMessageId());
					emailPrefUser.setTopicName(topicList[1]);
					emailPrefUser.setEmailId(userDet.getEmailId());
					emailPrefered.add(emailPrefUser);
				} else if (userDet.getSmsFlag().equalsIgnoreCase(YES)) {
					smsPrefUser = new UserMessageSO();
					smsPrefUser.setMessage(req.getMessageData());
					smsPrefUser.setUserId(userDet.getUserId());
					smsPrefUser.setGlobalTransactionId(req.getMessageId());
					smsPrefUser.setTopicName(topicList[0]);
					smsPrefUser.setMobileNumber(userDet.getMobileNumber());
					smsPrefered.add(smsPrefUser);
				}

			}
		}

		boolean status = publishMessgeOnTopics(emailPrefered, smsPrefered);
		return status;
	}

	private boolean publishMessgeOnTopics(List<UserMessageSO> emailPrefered, List<UserMessageSO> smsPrefered) {

		boolean publishedOnTopics = false;
		ProviderMsgPublisher emailPublisher = new ProviderMsgPublisher();
		ProviderMsgPublisher smsPublisher = new ProviderMsgPublisher();

		if (null != emailPrefered && emailPrefered.size() > 0)

			emailPrefered.forEach(publishMessage -> {

				try {

					emailPublisher.publishMessage(publishMessage);

				} catch (Exception e) {
					e.printStackTrace();
				}

			});
		if (null != smsPrefered && smsPrefered.size() > 0)
			smsPrefered.forEach(publishMessage -> {

				try {

					smsPublisher.publishMessage(publishMessage);
				} catch (Exception e) {
					e.printStackTrace();
				}

			});
		publishedOnTopics = true;
		return publishedOnTopics;
	}

	/**
	 * @param req
	 * @param delivered
	 * @throws IOException
	 */
	private void updateDelConfirmation(MessageStatus req) throws IOException {

		HttpClientRequestHandler client = new HttpClientRequestHandler();

		String updateStatusSvcURL = ExternalProperties.getAppConfig("updatestatus.service.url");
		client.sendPostReturnStatus(req, updateStatusSvcURL);

	}

	/**
	 * @param userDetails
	 * @param message
	 * @throws IOException
	 */
	public void notifyUsersBySMS(PubsubMessage message) throws IOException {
		String ack = null;
		MessageStatus req = null;
		TwilioSmsClient sms = new TwilioSmsClient();
		
		byte[] decodedMessageData = Base64.getMimeDecoder().decode(message.getData().getBytes());
		String decodedMessage = new String(decodedMessageData);
		
		ack = sms.sendSms(message.getAttributes().get("mobileNumber"),decodedMessage);

		if (null != ack && ack != "") {
			req = new MessageStatus();
			req.setDeliveryFlag("true");
			req.setMessageData(message.getData());
			req.setMessageId(message.getAttributes().get("globalTransactionId"));
			updateDelConfirmation(req);
		}

	}

	/**
	 * @param userDetails
	 * @param message
	 * @throws IOException
	 */
	public void notifyUsersByEmail(PubsubMessage message) throws IOException {
		String ack = null;
		MessageStatus req = null;
		SendGridEmailClient mail = new SendGridEmailClient();
		byte[] decodedMessageData = Base64.getMimeDecoder().decode(message.getData().getBytes());
		String decodedMessage = new String(decodedMessageData);
		
		ack = mail.sendEmail(message.getAttributes().get("emailId"), decodedMessage);
		if (null != ack && ack != "") {
			req = new MessageStatus();
			req.setDeliveryFlag("true");
			req.setMessageData(message.getData());
			req.setMessageId(message.getAttributes().get("globalTransactionId"));
			updateDelConfirmation(req);
		}
	}

}