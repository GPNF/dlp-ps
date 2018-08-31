package app.service.thirdparty;

import java.io.IOException;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

import app.model.UserDetailsSO;
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
		Response response=null;
		String ack=null;
		Email from = new Email(ExternalProperties.getAppConfig("email.sendgrid.user"));
		String subject = "Sendgrid test mail";

		Email to = new Email(receiverId);
		Content content = new Content("text/plain", actualMessage);
		Mail mail = new Mail(from, subject, to, content);

		SendGrid sg = new SendGrid(ExternalProperties.getAppConfig("email.sendgrid.apikey"));
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
			if(response.getStatusCode()>0 )
			{
				ack= "success";
			}
			else
			{
				ack="failed";
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return  ack;
	}
}