package app.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.json.JsonParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.pubsub.model.PubsubMessage;

import app.dao.UpdateNotifierPubsubDao;
import app.service.messagestatus.NotifyToMessageStatusService;
import app.util.NotifyUtility;

/**
 * @author Amol
 *
 */
@WebServlet(name = "TwilioEndpointService", urlPatterns = { "/twilioService" })
public class TwilioEndpointService extends HttpServlet {

	private static final long serialVersionUID = 360024119674491022L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");

		response.getWriter().print("Hello Do Get method called of twilioService !\r\n");
		// doGet(request, response);
	}
	
	
	
	@Override
	public final void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {

		ServletInputStream inputStream = req.getInputStream();
		JsonParser parser = JacksonFactory.getDefaultInstance().createJsonParser(inputStream);
		parser.skipToKey("message");
		PubsubMessage message = parser.parseAndClose(PubsubMessage.class);
		

		persistInDb(message);
		
		NotifyUtility uitility=new NotifyUtility();
		uitility.notifyUsersBySMS(message);

	}
	/**
	 * @param message
	 */
	private void persistInDb(PubsubMessage message) {
		try {
			UpdateNotifierPubsubDao dao =new UpdateNotifierPubsubDao();
			dao.insertPushedDetails(message);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
