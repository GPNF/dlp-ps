package app.servlet;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import app.model.RequestMapper;
import app.util.NotifyUtility;

/**
 * Servlet implementation class Test
 */
@WebServlet(name = "UserServlet", urlPatterns = { "/userService" })
public class UserService extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserService() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");

		response.getWriter().print("Hello Do Get method called !\r\n");
		// doGet(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");

		// doGet(request, response);
		String globalMsgId = request.getParameter("messageId");
		String messageData = request.getParameter("messageData");

		System.out.println("messageData " + messageData + " id " + globalMsgId);

		Reader reader = request.getReader();
		Gson gson = new Gson();
		RequestMapper req = gson.fromJson(reader, RequestMapper.class);
		System.out.println(
				"messageData " + req.getMessageData() + " id " + req.getMessageId() + "flag" + req.getDeliveryFlag());

		boolean status = NotifyUsers(req);
		response.getWriter().print("Hello Do Post method called Result !\r\n" + status);
	}

	private boolean NotifyUsers(RequestMapper req) {
		NotifyUtility utility = new NotifyUtility();
		boolean status = false;
		try {
			status = utility.checkAllUserPreference(req);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}

}
