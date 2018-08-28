package app.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.service.NotifyService;
import app.service.TopicListProvider;

/**
 * @author AdarshSinghal
 *
 */
@WebServlet(name = "publish", urlPatterns = { "/topic/publish", "/api/topic/list" })

public class PublishServlet extends HttpServlet {

	private static final long serialVersionUID = -9098430818560246450L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String pathInfo = req.getServletPath(); // /{value}/test

		if (pathInfo.equals("/api/topic/list")) {

			TopicListProvider topicService = new TopicListProvider();
			String topicListJson = topicService.getTopicListJson();

			resp.setContentType("application/json");
			PrintWriter pw = resp.getWriter();
			pw.print(topicListJson);
			return;
		}

		req.getRequestDispatcher("/index.jsp").forward(req, resp);
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String topicName = req.getParameter("topic-name");
		String message = req.getParameter("message");

		if (topicName == null || message == null) {
			resp.sendError(400);
			return;
		}

		List<String> topics = getTopicList(topicName);

		String gbTxnId = "g" + new Date().getTime() + "r" + (int) (Math.random() * 100);
		NotifyService notifyService = new NotifyService();
		List<String> messageIds = notifyService.publishMessage(topics, message, gbTxnId);

		req.setAttribute("gbTxnId", gbTxnId);
		req.setAttribute("messageIds", messageIds);

		if (messageIds.size() != 0) {
			req.getRequestDispatcher("/results/success.jsp").forward(req, resp);
		} else {
			req.getRequestDispatcher("/results/failure.jsp").forward(req, resp);
		}

	}

	private List<String> getTopicList(String topicName) {
		List<String> topics;
		if (topicName.contains(",")) {
			topics = Arrays.asList(topicName.split(","));
		} else {
			topics = new ArrayList<>();
			topics.add(topicName);
		}
		return topics;
	}

}
