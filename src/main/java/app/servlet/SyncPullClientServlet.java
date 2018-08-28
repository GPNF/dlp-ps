package app.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.pubsub.v1.ReceivedMessage;

import app.dao.SubscriberDao;
import app.model.RequestMapper;
import app.model.SubscriberMessage;
import app.service.SyncPullAction;
import app.util.MessageUtils;

/**
 * @author AdarshSinghal
 *
 */
@WebServlet({ "/pullmessage", "/api/pullmessage" })
public class SyncPullClientServlet extends HttpServlet {
	private static final String JSP_PAGE = "/pages/syncpullclient.jsp";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SyncPullClientServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher(JSP_PAGE).forward(req, resp);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String maxMessageStr = request.getParameter("max-message");
		String returnImmediatelyStr = request.getParameter("return-immediately");
		if (maxMessageStr == null || returnImmediatelyStr == null)
			request.getRequestDispatcher(JSP_PAGE).forward(request, response);

		List<SubscriberMessage> messageList = pullMessages(maxMessageStr, returnImmediatelyStr);
		
		if(request.getServletPath().equals("/api/pullmessage")) {
			Gson gson = new GsonBuilder().create();
			JsonArray jsonArr = (JsonArray) gson.toJsonTree(messageList);
			JsonObject container = new JsonObject();
			container.add("messages", jsonArr);
			response.setContentType("application/json");
			response.getWriter().print(container.toString());
			return;
		}

		request.setAttribute("messageList", messageList);
		if (!messageList.isEmpty()) {
			sendMessagesToUser(messageList);
			persistInDB(messageList);
		} else
			request.setAttribute("noMsg", true);

		request.getRequestDispatcher(JSP_PAGE).forward(request, response);
	}

	private List<SubscriberMessage> pullMessages(String maxMessageStr, String returnImmediatelyStr) throws IOException {
		int maxMessage = Integer.parseInt(maxMessageStr);

		boolean returnImmediately = Boolean.parseBoolean(returnImmediatelyStr);

		SyncPullAction syncPullAction = new SyncPullAction();
		List<ReceivedMessage> receivedMessages = syncPullAction.getReceivedMessages(maxMessage, returnImmediately);
		List<SubscriberMessage> messageList = MessageUtils.getSubscriberMessages(receivedMessages);
		return messageList;
	}

	private void persistInDB(List<SubscriberMessage> messageList) {
		SubscriberDao subscriberDao;
		try {
			subscriberDao = new SubscriberDao();
			subscriberDao.insertMessages(messageList);
		} catch (SQLException | ParseException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void sendMessagesToUser(List<SubscriberMessage> messageList) throws ServletException {
		for (SubscriberMessage subMessage : messageList) {
			HttpClientRequestHandler reqHandler=new HttpClientRequestHandler();
			RequestMapper req=new RequestMapper();
			req.setMessageId(subMessage.getGlobalTransactionId());
			req.setMessageData(subMessage.getMessage());
			reqHandler.processRequest(req, "https://possible-haven-212003.appspot.com/userService");
			//reqHandler.processRequest(req, "http://localhost:8080/userService");
			//NotifyUtility utility = new NotifyUtility();
			//utility.checkAllUserPreference(subMessage.getMessage());
		}
	}

}
