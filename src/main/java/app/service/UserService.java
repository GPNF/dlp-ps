package app.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import app.dao.UserDetailsDao;
import app.model.MessageStatus;
import app.model.SubscriberMessage;
import app.model.UserDetailsSO;
import app.servlet.HttpClientRequestHandler;
import app.util.ExternalProperties;
import app.util.NotifyUtility;

/**
 * @author AmolPol
 *
 */
public class UserService {

	/**
	 * @param messageList
	 * @throws ServletException
	 * @throws IOException
	 */
	public void sendMessagesToUser(List<SubscriberMessage> messageList) throws ServletException, IOException {
		System.out.println("Sending SMS to user");
		String userSvcURL = ExternalProperties.getAppConfig("user.service.url");
		for (SubscriberMessage subMessage : messageList) {
			HttpClientRequestHandler httpClient = new HttpClientRequestHandler();
			MessageStatus requestObject = new MessageStatus();
			requestObject.setMessageId(subMessage.getGlobalTransactionId());
			requestObject.setMessageData(subMessage.getMessage());
			if (null != subMessage.getDestGroupId() && subMessage.getDestGroupId() != "")
				requestObject.setDestGroupId(subMessage.getDestGroupId());
			httpClient.sendPostReturnStatus(requestObject, userSvcURL);
		}
	}

	/**
	 * @param message
	 * @throws Exception
	 * @throws ServletException
	 */
	public void checkAllUserPreference(MessageStatus req) throws Exception {

		List<UserDetailsSO> allUsers = null;
		UserDetailsDao userDetailsDao = new UserDetailsDao();
		if (null != req.getDestGroupId() && req.getDestGroupId() != "")
			allUsers = userDetailsDao.getAllUserDetails(req.getDestGroupId());
		else
			allUsers = userDetailsDao.getAllUserDetails();

		NotifyUtility utility = new NotifyUtility();
		// List<UserDetailsSO> allUsers = userDetailsDao.getAllUserDetails();

		if (null != allUsers && allUsers.size() > 0)
			utility.publishUserMessage(allUsers, req);
		else {
			throw new Exception("No Preferences Set for any user");
		}

		/*
		 * if (published) { for (UserDetailsSO userDetails : allUsers) {
		 * delivered = notifyUser(req, userDetails); } if (delivered)
		 * deliveryConfirmation(req, delivered); }
		 */

	}

}
