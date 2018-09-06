package app.servlet.tabledata;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.dao.UserGroupDetailsDAO;
import app.model.UserGroupDetails;

/**
 * @author AdarshSinghal
 *
 */
@WebServlet("/api/user-group-details")
public class UserGroupDetailsServlet extends TableDataParentServlet<UserGroupDetails> {

	private static final long serialVersionUID = -2172315964746965220L;
	private static final Logger LOGGER = Logger.getLogger(UserGroupDetailsServlet.class.getName());

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<UserGroupDetails> subscriberMessages = getSubscriberMessageList();

		if (subscriberMessages == null || subscriberMessages.isEmpty()) {
			prepareNoContentResponse(response);
			return;
		}

		prepareJsonResponse(response, subscriberMessages);
	}

	private List<UserGroupDetails> getSubscriberMessageList() {
		List<UserGroupDetails> subscriberMessages = null;
		try {
			UserGroupDetailsDAO dao = new UserGroupDetailsDAO();
			subscriberMessages = dao.getUserGroupDetails();
		} catch (SQLException e) {
			LOGGER.severe(e.getMessage());
		}
		return subscriberMessages;
	}

}
