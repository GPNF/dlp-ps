package app.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import app.model.MessageStatus;
import app.model.SubscriberMessage;
import app.servlet.HttpClientRequestHandler;
import app.util.ExternalProperties;

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
			if(null!=subMessage.getDestGroupId() && subMessage.getDestGroupId()!="")
			requestObject.setDestGroupId(subMessage.getDestGroupId());
			httpClient.sendPostReturnStatus(requestObject, userSvcURL);
		}
	}

}
