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
 * @author AmolPol This class is used to fetch user related information through
 *         UserServlet endpoint
 *
 */
public class UserService {

	/**
	 * this method being called after pulling messages from notify subscription
	 * of pubsub layer receives pulled messages from notify pull pubsub layer
	 * calls decoupled UserServlet endpoint to check user and there group
	 * details to whom we have to send message
	 * 
	 * @param messageList
	 * @throws ServletException
	 * @throws IOException
	 */
	public void sendMessagesToUser(List<SubscriberMessage> messageList) throws ServletException, IOException {

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
	 * this method listens request on user servlet endpoint receives actual
	 * messages after pulling from subscriptions prepares the userlist based on
	 * group from dao and calls notify uitility to handle and publish on pubsub
	 * 
	 * @param message
	 * @throws Exception
	 * @throws ServletException
	 * 
	 */
	public void checkAllUserPreference(MessageStatus req) throws Exception {

		List<UserDetailsSO> allUsers = null;
		UserDetailsDao userDetailsDao = new UserDetailsDao();
		if (null != req.getDestGroupId() && req.getDestGroupId() != "")
			allUsers = userDetailsDao.getAllUserDetails(req.getDestGroupId());
		else
			allUsers = userDetailsDao.getAllUserDetails();

		NotifyUtility utility = new NotifyUtility();

		if (null != allUsers && allUsers.size() > 0)
			utility.prepareMessagesWithPreferences(allUsers, req);
		else {
			throw new Exception("Notification Preferences not set by any user");
		}

	}

}
