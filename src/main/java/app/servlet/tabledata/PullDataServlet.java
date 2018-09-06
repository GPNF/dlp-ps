package app.servlet.tabledata;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.dao.SubscriberDao;
import app.model.SubscriberMessage;

/**
 * This servlet is used to get data from <b>Subscriber Table</b>. The Subscriber Table
 * entry is made on successful pull from Notification Service who is listening to
 * Subscription2. Notification Service acts as Subscriber for Subscription2.
 * 
 * @author AdarshSinghal
 *
 */
@WebServlet(name = "PullDataServlet", urlPatterns = { "/pulldata", "/pullData", "/pull-data", "/PullData",
		"/getPullData", "/api/pulldata" })
public class PullDataServlet extends TableDataParentServlet<SubscriberMessage> {
	private static final Logger LOGGER = Logger.getLogger(PullDataServlet.class.getName());
	private static final long serialVersionUID = 8626493333510999766L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {

		List<SubscriberMessage> subscriberMessages = getSubscriberMessageList();

		if (subscriberMessages == null || subscriberMessages.isEmpty()) {
			prepareNoContentResponse(response);
			return;
		}

		prepareJsonResponse(response, subscriberMessages);

	}

	/**
	 * @return List
	 */
	private List<SubscriberMessage> getSubscriberMessageList() {
		List<SubscriberMessage> subscriberMessages = null;
		try {
			SubscriberDao dao = new SubscriberDao();
			subscriberMessages = dao.getAllSubscriberMessage();
		} catch (ClassNotFoundException | SQLException e) {
			LOGGER.severe(e.getMessage());
		}
		return subscriberMessages;
	}
}
