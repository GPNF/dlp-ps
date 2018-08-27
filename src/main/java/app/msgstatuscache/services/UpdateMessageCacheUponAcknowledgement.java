package app.msgstatuscache.services;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import app.msgstatuscache.datacontainers.JsonDataContainer;
import app.msgstatuscache.utils.ConfigParams;
import app.msgstatuscache.utils.PropertyParserAndConfigAdapter;

class DbQueryAndUpdateOps {

	private ConfigParams params;
	private PropertyParserAndConfigAdapter connAdapter;

	public DbQueryAndUpdateOps(String cfgPath) {
		super();
		this.connAdapter = new PropertyParserAndConfigAdapter(cfgPath);
		this.params = this.connAdapter.readPropertiesAndSetParameters();
	}

	public void insertIntoTable(JsonDataContainer message) throws IOException {
		try (Connection conn = this.params.getConn()) {
			final String query = "update message_status_cache_db set dlv_rprt = ?  where glo_tran_id=?";
			PreparedStatement statement = conn.prepareStatement(query);

			try {
				if (message.getDeliveryFlag().equalsIgnoreCase("true"))
					statement.setString(1, "Delivered");
				else
					statement.setString(1, "In-progress");
				statement.setString(2, message.getMessageId());
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

@SuppressWarnings("serial")
@WebServlet(name = "UpdateMessageCacheUponAcknowledgement", urlPatterns = { "/queryMessageStat" })
public class UpdateMessageCacheUponAcknowledgement extends HttpServlet {

	@Override
	public final void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
		// ServletInputStream inputStream = req.getInputStream();

		Reader reader = req.getReader();
		Gson gson = new Gson();
		JsonDataContainer container = gson.fromJson(reader, JsonDataContainer.class);
		new DbQueryAndUpdateOps("WEB-INF/config_table.properties").insertIntoTable(container);

	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("text/plain");
	    response.setCharacterEncoding("UTF-8");

	    response.getWriter().print("Hello Do Get method called  of queryMessageStat!\r\n");
	//doGet(request, response);
	}
}
