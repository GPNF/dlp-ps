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
import app.service.UserService;

/**
 * This class is responsible for handling request of sync pull client and
 *  calling user service to check preferences
 * @author amolp
 *
 */
@WebServlet(name = "UserServlet", urlPatterns = { "/userService" })
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");

		
		Reader reader = request.getReader();
		Gson gson = new Gson();
		MessageStatus req = gson.fromJson(reader, MessageStatus.class);
				
		checkUserPrefs(req);
		
	}
/**
 * Calls the user service class to get the user prefernces and route the message
 * @param req
 */
	private void checkUserPrefs(MessageStatus req)  {
		UserService userService = new UserService();
		try {
			userService.checkAllUserPreference(req);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
