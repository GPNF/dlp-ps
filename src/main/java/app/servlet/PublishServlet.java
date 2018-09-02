package app.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;

import app.service.NotifyService;
import app.util.ExternalProperties;

/**
 * @author AdarshSinghal
 *
 */
@WebServlet(name = "publish", urlPatterns = { "/topic/publish", "/api/topic/list" })

public class PublishServlet extends HttpServlet {

	private static final long serialVersionUID = -9098430818560246450L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String pathInfo = req.getServletPath(); // /{value}/test

		if (pathInfo.equals("/api/topic/list")) {

			String topics = ExternalProperties.getAppConfig("app.gc.pubsub.topic.layer1");

			Gson gson = new GsonBuilder().create();
			JsonElement topicJsonArr = gson.toJsonTree(topics.split(","));
			JsonObject topicListJson = new JsonObject();
			topicListJson.add("topics", topicJsonArr);
			
			resp.setContentType("application/json");
			PrintWriter pw = resp.getWriter();
			pw.print(topicListJson);
			return;
		}

		req.getRequestDispatcher("/index.jsp").forward(req, resp);
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String topicName = req.getParameter("topic-name");
		String message = req.getParameter("message");
		String srcAuthLevel = req.getParameter("src-auth-level");
		System.out.println(srcAuthLevel); 
		//if(srcAuthLevel)

		if (topicName == null || message == null) {
			resp.sendError(400);
			return;
		}

		List<String> topics = getTopicList(topicName);

		String gbTxnId = "g" + new Date().getTime() + "r" + (int) (Math.random() * 100);
		
		ByteString data = ByteString.copyFromUtf8(message);
		PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data)
				.putAttributes("globalTransactionId", gbTxnId)
				.build();
		System.out.println("From Publish Servlet, The PubSub Message is\n"+ pubsubMessage);
		NotifyService notifyService = new NotifyService();
		List<String> messageIds = notifyService.publishMessage(topics, pubsubMessage);

		req.setAttribute("gbTxnId", gbTxnId);
		req.setAttribute("messageIds", messageIds);

		if (messageIds.size() != 0) {
			req.getRequestDispatcher("/results/success.jsp").forward(req, resp);
		} else {
			req.getRequestDispatcher("/results/failure.jsp").forward(req, resp);
		}

	}

	private List<String> getTopicList(String topicName) {
		List<String> topics;
		if (topicName.contains(",")) {
			topics = Arrays.asList(topicName.split(","));
		} else {
			topics = new ArrayList<>();
			topics.add(topicName);
		}
		return topics;
	}

}
