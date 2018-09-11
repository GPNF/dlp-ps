package app.servlet.tabledata;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.dao.LoggingDAO;
import app.logging.CloudLogger;
import app.model.LoggingModel;

/**
 * This servlet is used to get logging data from ActivityLogging Table
 * 
 * @author adarshsinghal
 *
 */
@WebServlet("/api/getLoggingData")
public class LoggingDataServlet extends TableDataParentServlet<LoggingModel> {

	private static final long serialVersionUID = 8696484787182103032L;
	private CloudLogger LOGGER = CloudLogger.getLogger();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<LoggingModel> logDetails = getLoggingDetailsList();

		if (logDetails == null || logDetails.isEmpty()) {
			prepareNoContentResponse(response);
			return;
		}

		prepareJsonResponse(response, logDetails);

	}

	private List<LoggingModel> getLoggingDetailsList() {
		List<LoggingModel> logDetails = new ArrayList<>();
		LoggingDAO dao;
		try {
			dao = new LoggingDAO();
			logDetails = dao.getAllFieldDetails();
		} catch (SQLException | UnsupportedEncodingException e) {
			LOGGER.error(e.getMessage());
		}
		return logDetails;
	}

}
