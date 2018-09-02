package app.servlet.tabledata;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.dao.LoggingDAO;
import app.model.DataTableWrapper;
import app.model.LoggingModel;

/**
 * Servlet implementation class LoggingDataServlet
 */
@WebServlet("/api/getLoggingData")
public class LoggingDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoggingDataServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<LoggingModel> logDetails = new ArrayList<>();
		LoggingDAO dao;
		try {
			dao = new LoggingDAO();
			logDetails = dao.getAllFieldDetails();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		if (logDetails == null || logDetails.isEmpty()) {
			prepareNoContentResponse(response);
			return;
		}

		prepareJsonResponse(response, logDetails);

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
	
	private void prepareJsonResponse(HttpServletResponse response, List<LoggingModel> logDetails)
			throws JsonProcessingException, IOException {
		DataTableWrapper wrapper = new DataTableWrapper(logDetails);

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(wrapper);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		out.print(json);
		out.flush();
	}

}
