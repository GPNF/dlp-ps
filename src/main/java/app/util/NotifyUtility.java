package app.util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import com.google.api.services.pubsub.model.PubsubMessage;

import app.dao.UserDetailsDao;
import app.model.RequestMapper;
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
	 * @param message
	 * @throws Exception
	 * @throws ServletException
	 */
	public void checkAllUserPreference(RequestMapper req) throws Exception {

		UserDetailsDao userDetailsDao = new UserDetailsDao();
		List<UserDetailsSO> allUsers = userDetailsDao.getAllUserDetails();
		boolean published = false;
		if (null != allUsers && allUsers.size() > 0)
			published = publishUserMessage(allUsers, req);
		else {
			throw new Exception("No Preferences Set for any user");
		}

		/*
		 * if (published) { for (UserDetailsSO userDetails : allUsers) {
		 * delivered = notifyUser(req, userDetails); } if (delivered)
		 * deliveryConfirmation(req, delivered); }
		 */

	}

	private boolean publishUserMessage(List<UserDetailsSO> allUsers, RequestMapper req) {

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
	 * @param userDetails
	 * @return
	 */
	/*
	 * private boolean notifyUser(RequestMapper req, UserDetailsSO userDetails)
	 * { boolean delivered; String emailAck = null; String smsAck = null; if
	 * (userDetails.getEmailFlag().equalsIgnoreCase(YES)) emailAck =
	 * notifyUsersByEmail(userDetails, req.getMessageData());
	 * 
	 * if (userDetails.getSmsFlag().equalsIgnoreCase(YES)) smsAck =
	 * notifyUsersBySMS(userDetails, req.getMessageData());
	 * 
	 * if ((emailAck != null || smsAck != null) && (emailAck.contains("success")
	 * || smsAck.contains("success"))) { delivered = true; } else { delivered =
	 * false; } return delivered; }
	 */

	/**
	 * @param req
	 * @param delivered
	 */
	private void updateDelConfirmation(RequestMapper req) {

		HttpClientRequestHandler client = new HttpClientRequestHandler();

		client.processRequest(req, ExternalProperties.getAppConfig("updatestatus.service.url"));

	}

	/**
	 * @param userDetails
	 * @param message
	 */
	public void notifyUsersBySMS(PubsubMessage message) {
		String ack = null;
		RequestMapper req = null;
		TwilioSmsClient sms = new TwilioSmsClient();
		ack = sms.sendSms(message.getAttributes().get("mobileNumber"), message.getData());

		if (null != ack && ack != "") {
			req = new RequestMapper();
			req.setDeliveryFlag("true");
			req.setMessageData(message.getData());
			req.setMessageId(message.getAttributes().get("globalTransactionId"));
			updateDelConfirmation(req);
		}

	}

	/**
	 * @param userDetails
	 * @param message
	 */
	public void notifyUsersByEmail(PubsubMessage message) {
		String ack = null;
		RequestMapper req = null;
		SendGridEmailClient mail = new SendGridEmailClient();
		ack = mail.sendEmail(message.getAttributes().get("emailId"), message.getData());
		if (null != ack && ack != "") {
			req = new RequestMapper();
			req.setDeliveryFlag("true");
			req.setMessageData(message.getData());
			req.setMessageId(message.getAttributes().get("globalTransactionId"));
			updateDelConfirmation(req);
		}
	}

}