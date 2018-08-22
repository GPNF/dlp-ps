package app.servlet.tabledata;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.dao.UserDetailsDao;
import app.model.DataTableWrapper;
import app.model.UserDetailsSO;

/**
 * @author AdarshSinghal
 *
 */
@WebServlet(name = "UserDetailsServlet", urlPatterns = { "/userdetailsdata" })
public class UserDetailsServlet extends HttpServlet {

	private static final long serialVersionUID = 8626493333510999766L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {

		List<UserDetailsSO> userDetailsList = null;
		try {
			userDetailsList = getUserDetailsList();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (userDetailsList == null || userDetailsList.isEmpty()) {
			prepareNoContentResponse(response);
			return;
		}

		prepareJsonResponse(response, userDetailsList);

	}

	/**
	 * @param response
	 * @param userDetailsList
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	private void prepareJsonResponse(HttpServletResponse response, List<UserDetailsSO> userDetailsList)
			throws JsonProcessingException, IOException {
		DataTableWrapper wrapper = new DataTableWrapper(userDetailsList);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(wrapper);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		out.print(json);
		out.flush();
	}

	/**
	 * @return List
	 * @throws SQLException
	 */
	private List<UserDetailsSO> getUserDetailsList() throws SQLException {
		UserDetailsDao userDetailsDao = new UserDetailsDao();
		List<UserDetailsSO> allUsers = userDetailsDao.getAllUserDetails();
		return allUsers;
	}

	/**
	 * @param response
	 * @throws IOException
	 */
	private void prepareNoContentResponse(HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.setStatus(204); // No content found
		out.flush();
	}
}
