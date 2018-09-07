package app.servlet.tabledata;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.dao.UserDetailsDao;
import app.model.UserDetailsSO;

/**
 * This servlet is used to get user details.
 * 
 * @author AdarshSinghal
 *
 */
@WebServlet(name = "UserDetailsServlet", urlPatterns = { "/userdetailsdata" })
public class UserDetailsServlet extends TableDataParentServlet<UserDetailsSO> {

	private static final long serialVersionUID = 8626493333510999766L;
	private static final Logger LOGGER = Logger.getLogger(UserDetailsServlet.class.getName());

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {

		List<UserDetailsSO> userDetailsList = getUserDetailsList();

		if (userDetailsList == null || userDetailsList.isEmpty()) {
			prepareNoContentResponse(response);
			return;
		}

		prepareJsonResponse(response, userDetailsList);

	}

	private List<UserDetailsSO> getUserDetailsList() {
		List<UserDetailsSO> userDetailsList = null;
		try {
			UserDetailsDao userDetailsDao = new UserDetailsDao();
			List<UserDetailsSO> allUsers = userDetailsDao.getAllUserDetails();
			userDetailsList = allUsers;
		} catch (SQLException e) {
			LOGGER.severe(e.getMessage());
		}
		return userDetailsList;
	}

}
