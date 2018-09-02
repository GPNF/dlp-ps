package app.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class AuthorizationServlet
 */
@WebServlet(name = "AuthorizationServlet", urlPatterns = { "/authorize" })
public class AuthorizationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Use POST method.");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// AuthorizationService authSvc = new AuthorizationService();

		response.setContentType("application/json");
		response.getWriter();
		// TODO For now, NotifySvc is calling AuthService directly. The task is to write
		// HTTP Client for calling this servlet by NotifySvc.
	}

}
