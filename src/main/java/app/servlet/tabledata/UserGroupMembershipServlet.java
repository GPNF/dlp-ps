package app.servlet.tabledata;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.dao.UserGroupMembershipDAO;
import app.model.UserGroupModel;

/**
 * This servlet is used to get user group membership data
 * 
 * @author AdarshSinghal
 *
 */
@WebServlet({ "/UserGroupMembershipServlet", "/api/get-user-group-membership" })
public class UserGroupMembershipServlet extends TableDataParentServlet<UserGroupModel> {

	private static final long serialVersionUID = -1010176831489295952L;
	private static final Logger LOGGER = Logger.getLogger(UserGroupDetailsServlet.class.getName());

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<UserGroupModel> groupMembershipList = getUserGroupList();

		if (groupMembershipList == null || groupMembershipList.isEmpty()) {
			prepareNoContentResponse(response);
			return;
		}

		prepareJsonResponse(response, groupMembershipList);

	}

	private List<UserGroupModel> getUserGroupList() {
		List<UserGroupModel> groupMembershipList = null;
		try {
			UserGroupMembershipDAO groupMembershipDao = new UserGroupMembershipDAO();
			List<UserGroupModel> UserGroupModel1 = groupMembershipDao.getUserGroupMembershipDetails();
			groupMembershipList = UserGroupModel1;
		} catch (SQLException e) {
			LOGGER.severe(e.getMessage());
		}
		return groupMembershipList;
	}

}
