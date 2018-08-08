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

/**
 * Servlet implementation class SyncPullClientServlet
 */
@WebServlet({ "/pullmessage"})
public class SyncPullClientServlet extends HttpServlet {
	private static final String JSP_PAGE = "/pages/syncpullclient.jsp";
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SyncPullClientServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	req.getRequestDispatcher(JSP_PAGE).forward(req, resp);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String maxMessageStr = request.getParameter("max-message");
		String returnImmediatelyStr = request.getParameter("return-immediately");
		if (maxMessageStr == null || returnImmediatelyStr == null)
			request.getRequestDispatcher(JSP_PAGE).forward(request, response);
		
		int maxMessage = Integer.parseInt(maxMessageStr);

		boolean returnImmediately = Boolean.parseBoolean(returnImmediatelyStr);

		SyncPullService syncPullSvc = new SyncPullService();
		List<ReceivedMessage> receivedMessages = syncPullSvc.getReceivedMessages(maxMessage, returnImmediately);
		List<SubscriberMessage> messageList = MessageUtils.getSubscriberMessages(receivedMessages, "sub1",
				"SyncPullClient", "sync");
		request.setAttribute("messageList", messageList);
		System.out.println("Into Servlet, got messageList of size "+ messageList.size());
		if (!messageList.isEmpty()) {
			SubscriberDao subscriberDao;
			try {
				subscriberDao = new SubscriberDao();
				subscriberDao.insertMessages(messageList);
			} catch (SQLException | ParseException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		} else
			request.setAttribute("noMsg", true);

		request.getRequestDispatcher(JSP_PAGE).forward(request, response);
	}

}
