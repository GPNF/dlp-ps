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

import app.service.messagestatus.NotifyToMessageStatusService;

/**
 * @author Aniruddha
 *
 */
@WebServlet(name = "NotifyToMessageStatusService", urlPatterns = { "/notifyServicetoStatDb", "/notifyService" })
public class NotifyToMessageStatusServlet extends HttpServlet {

	private static final long serialVersionUID = 360024119674491022L;

	@Override
	public final void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {

		ServletInputStream inputStream = req.getInputStream();
		JsonParser parser = JacksonFactory.getDefaultInstance().createJsonParser(inputStream);
		parser.skipToKey("message");
		PubsubMessage message = parser.parseAndClose(PubsubMessage.class);
		NotifyToMessageStatusService statusService = new NotifyToMessageStatusService();
		try {
			statusService.insertIntoTable(message.getAttributes().get("globalTransactionId"));
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
