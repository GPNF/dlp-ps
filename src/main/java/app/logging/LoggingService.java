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

/**
 * 
 * @author Aniruddha Sinha
 * @Description This is a utility class which is intended to feed the data to
 *              the logging database whenever packets come from NotifyService in
 *              the first Pub/Sub layer.
 * @param ConfigParams object - The aim of this object is to contain the data
 *                     provided by the propertyParserAndConfigAdapter object and
 *                     create a database connection out of it, which will later
 *                     be used for feeding data into message status cache db
 * @PropertyParserAndConfigAdapter object - The aim of this object is to pick
 *                                 data from the property file and create a
 *                                 configuration parameters object out of it.
 * 
 * 
 *
 */
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

/**
 * 
 * @author Aniruddha Sinha
 * @Description: This service is intended to be a push endpoint service for
 *               Google Cloud Pub/Sub. This service will act as an interface
 *               between Logging Database and the subscriber that will push to
 *               the url assigned to this service. The logging database will
 *               contain all the relevant information such as : -messageId
 *               -messageData -globalTransactionID -publishTime
 * 
 * @Note: Any sensitive data in message_data will be kept in a redacted state as
 *        the same flows through the Google Data Loss Prevention service
 *
 */

@WebServlet(name = "LoggingService", urlPatterns = { "/logging" })
public class LoggingService extends HttpServlet {

	private static final long serialVersionUID = -2990878220335313510L;

	public void doPost(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
		ServletInputStream inputStream = request.getInputStream();
		JsonParser parser = JacksonFactory.getDefaultInstance().createJsonParser(inputStream);
		parser.skipToKey("message");
		PubsubMessage message = parser.parseAndClose(PubsubMessage.class);
		new LogTableOperations("logging_configuration.properties").makeAnEntryToLog(message);
		response.setStatus(HttpServletResponse.SC_OK);
	}

}
