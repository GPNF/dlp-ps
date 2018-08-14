package app.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.MessagePublisher;
import app.dao.PublisherDao;
import app.model.PublisherMessage;

@WebServlet(name = "publish", urlPatterns = { "/publish" })

public class PublishServlet extends HttpServlet {

	private static final String YYYY_MM_DD_HH_MM_SS_A = "yyyy-MM-dd hh:mm:ss a";
	private static final long serialVersionUID = -9098430818560246450L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/index.jsp").forward(req, resp);
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String topicName = req.getParameter("topic-name");
		String message = req.getParameter("message");
		StringBuilder messageId = new StringBuilder("");
		Date date = new Date();
		long start = date.getTime();
		
		SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_A);
		PublisherMessage publisher = new PublisherMessage(messageId.toString(), message, topicName);
		
		try {
			new MessagePublisher().publishMessage(topicName, publisher, messageId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		long now = new Date().getTime();
		while (now - start < 20000 && messageId.length() == 0) {
			Date currDate = new Date();
			now = currDate.getTime();
		}
		String publishTime = formatter.format(new Date(now));
		publisher.setPublishTime(publishTime);
		boolean isMessageIdGenerated = messageId.toString() != null && messageId.toString().length() > 0;

		if (isMessageIdGenerated) {
			publisher.setMessageId(messageId.toString());
			try {
				persistInDB(publisher);
			} catch (SQLException | ParseException e) {
				e.printStackTrace();
			}
			req.setAttribute("messageId", messageId.toString());
			req.setAttribute("timeTaken", (now - start) / 1000);
			req.getRequestDispatcher("/results/success.jsp").forward(req, resp);
		} else {
			req.getRequestDispatcher("/results/failure.jsp").forward(req, resp);
		}

	}

	private void persistInDB(PublisherMessage publisher)
			throws SQLException, ParseException {
		
		PublisherDao publisherDao = new PublisherDao();
		publisherDao.insertPubliser(publisher);
	}

}
