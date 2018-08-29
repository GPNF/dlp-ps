package app.logging;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.json.JsonParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.pubsub.model.PubsubMessage;

import app.util.ConfigParams;
import app.util.PropertyParserAndConfigAdapter;

class LogTableOperations {
	private ConfigParams params;
	private PropertyParserAndConfigAdapter adapter;

	private static final String YYYY_MM_DD_HH_MM_SS_A_Z = "yyyy-MM-dd hh:mm:ss a z";

	Logger logger = LoggerFactory.getLogger(LogTableOperations.class);

	public LogTableOperations(String path) {
		super();

		this.adapter = new PropertyParserAndConfigAdapter(path);
		this.params = this.adapter.readPropertiesAndSetParameters();
	}

	public void makeAnEntryToLog(PubsubMessage message) throws UnsupportedEncodingException {

		try (Connection conn = this.params.getConn()) {
			String query = "insert into " + this.params.getTableName().trim()
					+ "(message_id, message_data, subscription_name, published_timestamp, glo_tran_id, topic_name) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement statement = conn.prepareStatement(query);
			String formattedDate = message.getPublishTime();
			SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_A_Z);
			formatter.setTimeZone(TimeZone.getTimeZone("US/Eastern"));
			Date date = new Date();
			try {
				date = formatter.parse(formattedDate);
			} catch (ParseException e) {
				logger.info(e.getMessage());
			}
			

			Timestamp publishTime = new Timestamp(date.getTime());
			try {
				statement.setString(1, message.getMessageId().trim());
				statement.setString(2, message.getData());
				statement.setString(3, this.params.getSubscriberName());
				statement.setTimestamp(4, publishTime);
				statement.setString(5, message.getAttributes().get("globalTransactionId"));
				statement.setString(6, this.params.getTopicName());
				statement.execute();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

@WebServlet(name = "LoggingService", urlPatterns = { "/logging" })
public class LoggingService extends HttpServlet {

	private static final long serialVersionUID = -2990878220335313510L;

	public void doPost(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
		ServletInputStream inputStream = request.getInputStream();
		JsonParser parser = JacksonFactory.getDefaultInstance().createJsonParser(inputStream);
		parser.skipToKey("message");
		PubsubMessage message = parser.parseAndClose(PubsubMessage.class);
		new LogTableOperations("logging_configuration.properties").makeAnEntryToLog(message);
	}

}
