package app.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.dao.PublisherDao;
import app.model.PublisherMessage;
import app.service.MessagePublisher;
import app.service.TopicListProvider;

/**
 * @author AdarshSinghal
 *
 */
@WebServlet(name = "publish", urlPatterns = { "/topic/publish", "/topic/list" })

public class PublishServlet extends HttpServlet {

	private static final String YYYY_MM_DD_HH_MM_SS_A = "yyyy-MM-dd hh:mm:ss a";
	private static final long serialVersionUID = -9098430818560246450L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String pathInfo = req.getServletPath(); // /{value}/test
		String[] pathParts = pathInfo.split("/");
		String part2 = pathParts[2]; // test

		if (part2.equalsIgnoreCase("list")) {

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

		List<String> topics;
		if (topicName.contains(",")) {
			topics = Arrays.asList(topicName.split(","));
		} else {
			topics = new ArrayList<>();
			topics.add(topicName);
		}

		String gbTxnId = "g" + new Date().getTime() + "r" + (int)(Math.random() * 100);

		List<String> messageIds = new ArrayList<>();

		topics.forEach(topic -> {
			StringBuilder messageId = new StringBuilder("");
			boolean isMessageIdGenerated = publishMessage(topic, message, gbTxnId, messageId);
			if (isMessageIdGenerated) {
				messageIds.add(messageId.toString());
			}
		});
		
		req.setAttribute("gbTxnId", gbTxnId);

		if (messageIds.size() != 0) {
			req.getRequestDispatcher("/results/success.jsp").forward(req, resp);
		} else {
			req.getRequestDispatcher("/results/failure.jsp").forward(req, resp);
		}

	}

	private boolean publishMessage(String topicName, String message, String gbTxnId, StringBuilder messageId) {
		SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_A);
		PublisherMessage publisher = new PublisherMessage(messageId.toString(), message, topicName);

		try {
			new MessagePublisher().publishMessage(topicName, publisher, messageId, gbTxnId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String publishTime = formatter.format(new Date());
		publisher.setPublishTime(publishTime);
		boolean isMessageIdGenerated = messageId.toString() != null && messageId.toString().length() > 0;

		if (isMessageIdGenerated) {
			publisher.setMessageId(messageId.toString());
			try {
				persistInDB(publisher);
			} catch (SQLException | ParseException e) {
				e.printStackTrace();
			}
		}
		return isMessageIdGenerated;
	}

	private void persistInDB(PublisherMessage publisher) throws SQLException, ParseException {

		PublisherDao publisherDao = new PublisherDao();
		publisherDao.insertPubliser(publisher);
	}

}
