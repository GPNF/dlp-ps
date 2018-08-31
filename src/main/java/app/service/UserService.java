package app.service;

import java.util.List;

import javax.servlet.ServletException;

import app.model.RequestMapper;
import app.model.SubscriberMessage;
import app.model.UserDetailsSO;
import app.servlet.HttpClientRequestHandler;
import app.util.ExternalProperties;

public class UserService {

	public void sendMessagesToUser(List<SubscriberMessage> messageList) throws ServletException {
		for (SubscriberMessage subMessage : messageList) {
			HttpClientRequestHandler reqHandler = new HttpClientRequestHandler();
			RequestMapper req = new RequestMapper();
			req.setMessageId(subMessage.getGlobalTransactionId());
			req.setMessageData(subMessage.getMessage());
			reqHandler.processRequest(req, ExternalProperties.getAppConfig("user.service.url"));
		}
	}

	
}
