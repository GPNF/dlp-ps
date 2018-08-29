package app.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import app.dao.UpdateMessageCacheUponAcknowledgement;
import app.model.JsonDataContainer;

/**
 * @author Aniruddha
 *
 */
@WebServlet(name = "UpdateMessageCacheUponAcknowledgement", urlPatterns = { "/queryMessageStat" })
public class UpdateMessageCacheUponAcknowledgementServlet extends HttpServlet {

	private static final long serialVersionUID = 1390700307039619091L;

	@Override
	public final void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {

		Gson gson = new Gson();
		JsonDataContainer container = gson.fromJson(req.getReader(), JsonDataContainer.class);
		new UpdateMessageCacheUponAcknowledgement("config_table.properties").insertIntoTable(container);

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print("Hello Do Get method called  of queryMessageStat!\r\n");
	}
}
