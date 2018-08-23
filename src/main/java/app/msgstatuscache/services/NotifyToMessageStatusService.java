package app.msgstatuscache.services;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.json.JsonParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.pubsub.model.PubsubMessage;
import app.msgstatuscache.utils.ConfigParams;
import app.msgstatuscache.utils.PropertyParserAndConfigAdapter;

class DbOps {

	private ConfigParams params;
	private PropertyParserAndConfigAdapter connAdapter;

	public DbOps(String cfgPath) {
		super();
		this.connAdapter = new PropertyParserAndConfigAdapter(cfgPath);
		this.params = this.connAdapter.readPropertiesAndSetParameters();
	}

	public void insertIntoTable(String message) throws IOException {
		try (Connection conn = this.params.getConn()) {
			String query = "insert into " + this.params.getTableName().trim() + "(glo_tran_id, dlv_rprt) VALUES (?, ?)";
			PreparedStatement statement = conn.prepareStatement(query);

			try {
				statement.setString(1, message);
				statement.setString(2, "In-progress");
				statement.execute();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

@WebServlet(name = "NotifyToMessageStatusService", urlPatterns = { "/notifyServicetoStatDb", "/notifyService" })
public class NotifyToMessageStatusService extends HttpServlet {

	@Override
	@SuppressWarnings("unchecked")
	public final void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
		// ServletInputStream inputStream = req.getInputStream();

		ServletInputStream inputStream = req.getInputStream();
		JsonParser parser = JacksonFactory.getDefaultInstance().createJsonParser(inputStream);
		parser.skipToKey("message");
		PubsubMessage message = parser.parseAndClose(PubsubMessage.class);
		new DbOps("WEB-INF/config_table.properties")
				.insertIntoTable(message.getAttributes().get("globalTransactionId"));

	}
}
