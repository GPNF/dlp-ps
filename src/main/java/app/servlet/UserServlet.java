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

import app.model.MessageStatus;
import app.util.NotifyUtility;

/**
 * Servlet implementation class Test
 */
@WebServlet(name = "UserServlet", urlPatterns = { "/userService" })
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserServlet() {
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

		Reader reader = request.getReader();
		Gson gson = new Gson();
		MessageStatus req = gson.fromJson(reader, MessageStatus.class);
		System.out.println(
				"messageData " + req.getMessageData() + " id " + req.getMessageId() + " flag " + req.getDeliveryFlag());

		boolean status;
		try {
			 checkUserPrefs(req);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//response.getWriter().print("Status !\r\n " + status);
	}

	private void checkUserPrefs(MessageStatus req) throws Exception {
		NotifyUtility utility = new NotifyUtility();
		
		try {
			utility.checkAllUserPreference(req);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
