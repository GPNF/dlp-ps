package app.service;

import java.util.List;

import javax.servlet.ServletException;

import app.model.RequestMapper;
import app.model.SubscriberMessage;
import app.servlet.HttpClientRequestHandler;

public class UserService {

	public void sendMessagesToUser(List<SubscriberMessage> messageList) throws ServletException {
		for (SubscriberMessage subMessage : messageList) {
			HttpClientRequestHandler reqHandler = new HttpClientRequestHandler();
			RequestMapper req = new RequestMapper();
			req.setMessageId(subMessage.getGlobalTransactionId());
			req.setMessageData(subMessage.getMessage());
			reqHandler.processRequest(req, "https://possible-haven-212003.appspot.com/userService");
		}
	}

}
