package app.servlet;

import java.io.IOException;
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

import app.model.SubscriberMessage;
import app.service.NotificationService;

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

		NotificationService notificationSvc = new NotificationService();
		
		List<SubscriberMessage> messageList = notificationSvc.pullMessages(maxMessageStr, returnImmediatelyStr);
		
		if (!messageList.isEmpty()) {
			notificationSvc.sendMessagesToUser(messageList);
		} else
			request.setAttribute("noMsg", true);
		
		if(request.getServletPath().equals("/api/pullmessage")) {
			prepareJsonResponse(response, messageList);
			return;
		}

		request.setAttribute("messageList", messageList);
		request.getRequestDispatcher(JSP_PAGE).forward(request, response);
	}

	private void prepareJsonResponse(HttpServletResponse response, List<SubscriberMessage> messageList)
			throws IOException {
		Gson gson = new GsonBuilder().create();
		JsonArray jsonArr = (JsonArray) gson.toJsonTree(messageList);
		JsonObject container = new JsonObject();
		container.add("messages", jsonArr);
		response.setContentType("application/json");
		response.getWriter().print(container.toString());
	}



}
