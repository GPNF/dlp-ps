package app.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import app.dao.MessageStatusDAO;
import app.model.MessageStatus;

/**
 * @author Aniruddha Sinha
 * 
 * @Description: The UpdateMessageCacheUponAcknowledgement is intended to update
 *               the delivery report column of the message status cache database
 *               with either of the flags which are mentioned below
 * 
 *               1. Delivered -> If the message has been successfully delivered
 *               2. In-progress -> In-progress was the already assigned value
 *               when the message status cache was initialized by the
 *               NotifyToMessageStatusServlet. Its just like, the delivery
 *               report flag needs to be changed by this service whenever the
 *               message has been sent successfully
 *
 */
@WebServlet(name = "UpdateMessageCacheUponAcknowledgement", urlPatterns = { "/queryMessageStat" })
public class UpdateMessageCacheUponAcknowledgementServlet extends HttpServlet {

	private static final long serialVersionUID = 1390700307039619091L;

	@Override
	public final void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {

		Gson gson = new Gson();
		MessageStatus messageStatus = gson.fromJson(req.getReader(), MessageStatus.class);
		try {
			new MessageStatusDAO().insertIntoTable(messageStatus);
			resp.setStatus(HttpServletResponse.SC_OK);
		} catch (SQLException e) {
			e.printStackTrace();
			resp.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print("Hello Do Get method called  of queryMessageStat!\r\n");
	}
}
