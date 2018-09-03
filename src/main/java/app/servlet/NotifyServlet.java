package app.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.exception.ExternalUserNotAllowedException;
import app.exception.InsufficientAuthorizationException;
import app.exception.NoSuchGroupException;
import app.exception.PANDataFoundSecurityViolationException;
import app.model.SourceMessage;
import app.service.NotifyService;

/**
 * Servlet implementation class NotifyServlet
 */
@WebServlet(name = "NotifyServlet", urlPatterns = { "/notify" })
public class NotifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("This operation can only be performed using POST");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		

		SourceMessage srcMessage = getSourceMessage(request);
		List<String> messageIds = null;
		NotifyService notifyService = new NotifyService();
		String gbTxnId = "g" + new Date().getTime() + "r" + (int) (Math.random() * 100);
		srcMessage.setGlobalTxnId(gbTxnId);
		boolean isExceptionOccurred = false;
		try {
			messageIds = notifyService.notify(srcMessage);
		} catch (SQLException | ExternalUserNotAllowedException | NoSuchGroupException
				| InsufficientAuthorizationException | PANDataFoundSecurityViolationException e) {
			request.setAttribute("exceptionMsg", e.getMessage());
			isExceptionOccurred=true;
			request.setAttribute("isExceptionOccured", isExceptionOccurred);
			request.getRequestDispatcher("/pages/MessageSource.jsp").forward(request, response);
		}
		
		
		request.setAttribute("gbTxnId", gbTxnId);
		request.setAttribute("messageIds", messageIds);

		if (messageIds !=null && messageIds.size() != 0) {
			request.getRequestDispatcher("/results/success.jsp").forward(request, response);
		} else {
			if(!isExceptionOccurred)
				request.getRequestDispatcher("/results/failure.jsp").forward(request, response);
		}
		
	}

	private SourceMessage getSourceMessage(HttpServletRequest request) {
		String srcAuthLevelStr = request.getParameter("src-auth-level");
		String groupIdStr = request.getParameter("group-id");
		String message = request.getParameter("message");

		int srcAuthLevel = Integer.parseInt(srcAuthLevelStr);
		int groupId = Integer.parseInt(groupIdStr);
		SourceMessage srcMessage = new SourceMessage(srcAuthLevel, groupId, message);
		return srcMessage;
	}

}
