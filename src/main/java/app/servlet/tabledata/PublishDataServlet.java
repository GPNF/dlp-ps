package app.servlet.tabledata;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.dao.PublisherDao;
import app.model.PublisherMessage;

/** This servlet should be used to pull data of PublisherTable
 * @author AdarshSinghal
 *
 */
@WebServlet(name = "publishdata", urlPatterns = { "/publishdata", "/api/publishdata", "/api/PublishData",
		"/api/getPublishData" })
public class PublishDataServlet extends TableDataParentServlet<PublisherMessage> {

	private static final long serialVersionUID = -5242138226681465405L;
	private static final Logger LOGGER = Logger.getLogger(PublishDataServlet.class.getName());

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
	 * @return List
	 */
	private List<PublisherMessage> getPublisherMessageList() {
		List<PublisherMessage> publishers = null;
		try {
			PublisherDao publisherDao = new PublisherDao();
			publishers = publisherDao.getAllPublishers();
		} catch (SQLException e) {
			LOGGER.severe(e.getMessage());
		}
		return publishers;
	}

}
