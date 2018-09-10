package app.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.json.JsonParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.pubsub.model.PubsubMessage;

import app.dao.UpdateNotifierPubsubDao;
import app.service.thirdparty.TwilioSmsNotifier;

/**
 * @author Amol this servlet responsible for listening messages pushed on twilio
 *         topic and invoke twilio notifier
 */
@WebServlet(name = "TwilioEndpointService", urlPatterns = { "/twilioService" })
public class TwilioEndpointService extends HttpServlet {

	private static final long serialVersionUID = 360024119674491022L;

	@Override
	public final void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
		resp.setStatus(HttpServletResponse.SC_OK);
		ServletInputStream inputStream = req.getInputStream();
		JsonParser parser = JacksonFactory.getDefaultInstance().createJsonParser(inputStream);
		parser.skipToKey("message");
		PubsubMessage message = parser.parseAndClose(PubsubMessage.class);

		persistInDb(message);

		invokeNotifier(message);

	}

	/**
	 * @param message
	 * @throws IOException
	 */
	private void invokeNotifier(PubsubMessage message) throws IOException {
		TwilioSmsNotifier notifier = new TwilioSmsNotifier();
		int maxRetryCount=3;
		notifier.notifyUserBySMS(message,maxRetryCount);
	}

	/**
	 * @param message
	 */
	private void persistInDb(PubsubMessage message) {
		try {
			UpdateNotifierPubsubDao dao = new UpdateNotifierPubsubDao();
			dao.insertPushedDetails(message);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
