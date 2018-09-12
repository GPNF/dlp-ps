package app.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import app.dao.EmailNotifeirStatusLogDAO;
import app.model.DeliveryStatus;

/**
 * @author Amol this servlet responsible for listening messages pushed on
 *         Sendgrid topic and invoke sendgrid notifier
 */
@WebServlet(name = "SendGridWebhook", urlPatterns = { "/sendgridwebhook" })
public class SendGridWebhook extends HttpServlet {

	private static final long serialVersionUID = 360024119674491022L;

	@Override
	public final void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
		resp.setStatus(HttpServletResponse.SC_OK);
		String inputJson = getInputData(req);
		Gson g = new Gson();
		System.out.println("inputJson " + inputJson);
		// DeliveryStatus DeliveryStatus = g.fromJson(inputJson,
		// DeliveryStatus.class);
		Type collectionType = new TypeToken<Collection<DeliveryStatus>>() {
		}.getType();
		Collection<DeliveryStatus> enums = g.fromJson(inputJson, collectionType);

		System.out.println(enums);

		enums.forEach((statusData) -> persistInDb(statusData));

	}

	public void persistInDb(DeliveryStatus message) {
		try {
			EmailNotifeirStatusLogDAO dao;
			try {
				dao = new EmailNotifeirStatusLogDAO();
				dao.insertWebhookDetails(message);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * public static void main(String[] args) { Gson g = new Gson();
	 * 
	 * StatusList listStatus = g.fromJson("[{\r\n" +
	 * "    \"email\": \"example@test.com\",\r\n" +
	 * "    \"timestamp\": 1536648769,\r\n" +
	 * "    \"smtp-id\": \"<14c5d75ce93.dfd.64b469@ismtpd-555>\",\r\n" +
	 * "    \"event\": \"delivered\",\r\n" +
	 * "    \"category\": \"cat facts\",\r\n" +
	 * "    \"sg_event_id\": \"nZYZNTeicmbLmpCR2sM4jQ==\",\r\n" +
	 * "    \"sg_message_id\": \"14c5d75ce93.dfd.64b469.filter0001.16648.5515E0B88.0\",\r\n"
	 * + "    \"response\": \"250 OK\"\r\n" + "  }]", StatusList.class);
	 * 
	 * Type collectionType = new
	 * TypeToken<Collection<DeliveryStatus>>(){}.getType();
	 * Collection<DeliveryStatus> enums = g.fromJson("[{\r\n" +
	 * "    \"email\": \"example@test.com\",\r\n" +
	 * "    \"timestamp\": 1536648769,\r\n" +
	 * "    \"smtp-id\": \"<14c5d75ce93.dfd.64b469@ismtpd-555>\",\r\n" +
	 * "    \"event\": \"delivered\",\r\n" +
	 * "    \"category\": \"cat facts\",\r\n" +
	 * "    \"sg_event_id\": \"nZYZNTeicmbLmpCR2sM4jQ==\",\r\n" +
	 * "    \"sg_message_id\": \"14c5d75ce93.dfd.64b469.filter0001.16648.5515E0B88.0\",\r\n"
	 * + "    \"response\": \"250 OK\"\r\n" + "  }]", collectionType);
	 * 
	 * System.out.println(g.toJson(enums)); }
	 */
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
