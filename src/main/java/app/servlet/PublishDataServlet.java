package app.servlet;

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

import app.dao.PublisherDao;
import app.model.DataTableWrapper;
import app.model.PublisherMessage;

@WebServlet(name = "publishdata", urlPatterns = { "/publishdata", "/publishData", "/PublishData", "/getPublishData" })
public class PublishDataServlet extends HttpServlet {

	private static final long serialVersionUID = -5242138226681465405L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<PublisherMessage> publishers = getPublisherMessageList();

		if (publishers == null || publishers.isEmpty()) {
			prepareNoContentResponse(response);
			return;
		}

		prepareJsonResponse(response, publishers);
	}

	/**
	 * @param response
	 * @param publishers
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	private void prepareJsonResponse(HttpServletResponse response, List<PublisherMessage> publishers)
			throws JsonProcessingException, IOException {
		DataTableWrapper wrapper = new DataTableWrapper(publishers);

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
	 */
	private List<PublisherMessage> getPublisherMessageList() {
		List<PublisherMessage> publishers = null;
		try {
			PublisherDao publisherDao = new PublisherDao();
			publishers = publisherDao.getAllPublishers();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return publishers;
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
