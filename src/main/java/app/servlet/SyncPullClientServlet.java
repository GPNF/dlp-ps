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

import com.google.pubsub.v1.ReceivedMessage;

import app.dao.SubscriberDao;
import app.model.SubscriberMessage;
import app.service.SyncPullService;
import app.util.MessageUtils;
import app.util.NotifyUtility;

/**
 * @author AdarshSinghal
 *
 */
@WebServlet({ "/pullmessage" })
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

		SyncPullService syncPullSvc = new SyncPullService();
		List<ReceivedMessage> receivedMessages = syncPullSvc.getReceivedMessages(maxMessage, returnImmediately);
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
			NotifyUtility utility = new NotifyUtility();
			try {
				utility.checkAllUserPreference(subMessage.getMessage());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
