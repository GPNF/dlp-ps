package app.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import app.model.InspectResult;
import app.service.NotifyService;

@WebServlet(name = "Scan Data", urlPatterns = { "/inspect" })
public class InspectServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse httpResponse)
			throws IOException, ServletException {
		httpResponse.setContentType("application/json");
		String inputJson = getInputData(request);
		String inputMessage = getInputMessage(inputJson);

		NotifyService notifyService = new NotifyService();
		List<InspectResult> inspectResList = notifyService.inspect(inputMessage);
		String deidentifiedRes = notifyService.deIdentify(inputMessage);

		if (inspectResList.size() > 0) {
			returnResponseJson(httpResponse, inspectResList, deidentifiedRes);
		}

		else {
			PrintWriter out = httpResponse.getWriter();
			out.println(inputMessage);
		}
	}

	private void returnResponseJson(HttpServletResponse response, List<InspectResult> inspectResList,
			String deidentifiedRes) throws IOException {
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().create();
		JsonArray inspectResults = gson.toJsonTree(inspectResList).getAsJsonArray();
		JsonObject responseJsonObj = new JsonObject();
		responseJsonObj.add("inspectResult", inspectResults);
		responseJsonObj.addProperty("message", deidentifiedRes);
		out.println(responseJsonObj);
	}

	/**
	 * The Json should be in format {"message":"{{message}}"}
	 * 
	 * @param inputJson
	 * @return
	 */
	private String getInputMessage(String inputJson) {
		Gson gson = new GsonBuilder().create();
		JsonPrimitive jsonPrimitive = (JsonPrimitive) gson.fromJson(inputJson, JsonObject.class).get("message");

		String inputMessage = jsonPrimitive.getAsString();
		return inputMessage;
	}

	private String getInputData(HttpServletRequest req) throws IOException {
		BufferedReader d = req.getReader();
		StringBuilder inputData = new StringBuilder();

		String data = "";
		while ((data = d.readLine()) != null) {
			inputData.append(data);
		}
		return inputData.toString();
	}
}