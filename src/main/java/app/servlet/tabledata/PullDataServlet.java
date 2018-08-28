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

import app.dao.SubscriberDao;
import app.model.DataTableWrapper;
import app.model.SubscriberMessage;

/**
 * @author AdarshSinghal
 *
 */
@WebServlet(name = "PullDataServlet", urlPatterns = { "/pulldata", "/pullData", "/pull-data", "/PullData",
		"/getPullData" ,"/api/pulldata"})
public class PullDataServlet extends HttpServlet {

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
	 * @param response
	 * @param subscriberMessages
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	private void prepareJsonResponse(HttpServletResponse response, List<SubscriberMessage> subscriberMessages)
			throws JsonProcessingException, IOException {
		DataTableWrapper wrapper = new DataTableWrapper(subscriberMessages);
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
	private List<SubscriberMessage> getSubscriberMessageList() {
		List<SubscriberMessage> subscriberMessages = null;
		try {
			SubscriberDao dao = new SubscriberDao();
			subscriberMessages = dao.getAllSubscriberMessage();
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
		return subscriberMessages;
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
}
