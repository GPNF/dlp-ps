package app.servlet.tabledata;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;

import app.model.DataTableWrapper;

public class TableDataParentServlet<T> extends HttpServlet {

	private static final long serialVersionUID = -2099704120391075845L;

	/**
	 * @param response
	 * @param publishers
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	protected void prepareJsonResponse(HttpServletResponse response, List<T> publishers)
			throws JsonProcessingException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		DataTableWrapper wrapper = new DataTableWrapper(publishers);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(wrapper);
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
	}

	/**
	 * @param response
	 * @throws IOException
	 */
	protected void prepareNoContentResponse(HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.setStatus(204); // No content found
		PrintWriter out = response.getWriter();
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("data", "[]");
		out.print(jsonObject);
		out.flush();
	}

}
