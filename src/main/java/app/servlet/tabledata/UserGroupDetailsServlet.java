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
import com.google.gson.JsonObject;

import app.dao.UserGroupDetailsDAO;
import app.model.DataTableWrapper;
import app.model.UserGroupDetails;

/**
 * Servlet implementation class UserGroupDetailsServlet
 */
@WebServlet("/api/user-group-details")
public class UserGroupDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserGroupDetailsServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<UserGroupDetails> subscriberMessages = getSubscriberMessageList();

		if (subscriberMessages == null || subscriberMessages.isEmpty()) {
			prepareNoContentResponse(response);
			return;
		}

		prepareJsonResponse(response, subscriberMessages);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void prepareJsonResponse(HttpServletResponse response, List<UserGroupDetails> userGrpDetails)
			throws JsonProcessingException, IOException {
		DataTableWrapper wrapper = new DataTableWrapper(userGrpDetails);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(wrapper);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		out.print(json);
		out.flush();
	}

	/**
	 * @param response
	 * @throws IOException
	 */
	private void prepareNoContentResponse(HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.setStatus(204); // No content found
		PrintWriter out = response.getWriter();
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("data", "[]");
		out.print(jsonObject);
		out.flush();
	}

	private List<UserGroupDetails> getSubscriberMessageList() {
		List<UserGroupDetails> subscriberMessages = null;
		try {
			UserGroupDetailsDAO dao = new UserGroupDetailsDAO();
			subscriberMessages = dao.getUserGroupDetails();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return subscriberMessages;
	}

}
