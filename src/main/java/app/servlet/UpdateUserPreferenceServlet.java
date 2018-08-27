package app.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import app.dao.UserPreferenceDao;
import app.model.UpdateUserPreferenceModel;

/**
 * Servlet implementation class UpdateUserPreferenceServlet
 */
@WebServlet("/api/updateUserPreference")
public class UpdateUserPreferenceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateUserPreferenceServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Gson gson = new GsonBuilder().create();
		UpdateUserPreferenceModel preference = gson.fromJson(request.getReader(), UpdateUserPreferenceModel.class);

		UserPreferenceDao dao;
		int rowsModified = 0;
		try {
			dao = new UserPreferenceDao();
			rowsModified = dao.updateUserPreference(preference);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		response.setContentType("application/json");
		JsonObject root = new JsonObject();
		root.addProperty("rowsModified", rowsModified);
		response.getWriter().print(root);
	}

}
