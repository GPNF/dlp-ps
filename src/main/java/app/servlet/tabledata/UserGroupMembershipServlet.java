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

import app.dao.UserGroupMembershipDAO;
import app.model.DataTableWrapper;
import app.model.UserGroupMembership;

/**
 * Servlet implementation class UserGroupMembershipServlet
 */
@WebServlet({ "/UserGroupMembershipServlet", "/api/get-user-group-membership" })
public class UserGroupMembershipServlet extends HttpServlet {
	
	private static final long serialVersionUID = -1010176831489295952L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<UserGroupMembership> groupMembershipList = null;
		try {
			groupMembershipList = getgroupMembershipList();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (groupMembershipList == null || groupMembershipList.isEmpty()) {
			prepareNoContentResponse(response);
			return;
		}

		prepareJsonResponse(response, groupMembershipList);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * @param response
	 * @param groupMembershipList
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	private void prepareJsonResponse(HttpServletResponse response, List<UserGroupMembership> groupMembershipList)
			throws JsonProcessingException, IOException {
		DataTableWrapper wrapper = new DataTableWrapper(groupMembershipList);
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
	private List<UserGroupMembership> getgroupMembershipList() throws SQLException {
		UserGroupMembershipDAO groupMembershipDao = new UserGroupMembershipDAO();
		List<UserGroupMembership> UserGroupMembership = groupMembershipDao.getMembershipTableData();
		return UserGroupMembership;
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
