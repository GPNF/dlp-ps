package app.service.thirdparty;

import java.io.IOException;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

import app.util.AES;
import app.util.ExternalProperties;

/**
 * @author AmolPol
 *
 */
public class SendGridEmailClient {

	/**
	 * @param userSO
	 * @param message
	 * @return String
	 */
	public String sendEmail(String receiverId, String actualMessage) {

		// ExternalProperties.getDbConfig("email.sendgrid.apikey");
		Response response = null;
		String ack = null;
		String fromEmail = ExternalProperties.getAppConfig("email.sendgrid.user");
		String decrytedFromEmail = AES.decrypt(fromEmail);
		Email from = new Email(decrytedFromEmail);
		String subject = "Global Notification test mail";

		String formattedMessage = "Dear Customer," + "\n\nGreetings from Global Payments." + actualMessage
				+ "\n\nLooking forward to more opportunities to be of service to you." + "\n\nSincerely,"
				+ "\nCustomer Service Team" + "\nGlobal Payments"
				+ "\n\nThis is a system-generated e-mail.Please do not reply to this e-mail.";

		/*
		 * String formattedMessage =
		 * "<p>Dear Customer,<br/>Greetings from Global Payments.</p>" +
		 * actualMessage +
		 * "<p>Looking forward to more opportunities to be of service to you <br/></p>"
		 * + "<hr/><p>Sincerely,<br/>Customer Service Team\nGlobal Payments</p>"
		 * +
		 * "<p><b><i>This is a system-generated e-mail.Please do not reply to this e-mail.</i></b></p>"
		 * ;
		 */

		Email to = new Email(receiverId);
		Content content = new Content("text/plain", formattedMessage);
		Mail mail = new Mail(from, subject, to, content);

		String sendGridApiKey = ExternalProperties.getAppConfig("email.sendgrid.apikey");
		String decryptedKey = AES.decrypt(sendGridApiKey);
		SendGrid sg = new SendGrid(decryptedKey);

		Request request = new Request();
		try {

			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());

			response = sg.api(request);
			System.out.println(response.getStatusCode());
			System.out.println(response.getBody());
			System.out.println(response.getHeaders());

			System.out.println("Mail sent successfully...");
			if (response.getStatusCode() == 202) {
				ack = "success";
			} else {
				ack = "failed";
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return ack;
	}
}