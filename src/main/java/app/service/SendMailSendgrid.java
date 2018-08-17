package app.service;

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

public class SendMailSendgrid {

	public String sendEmail(UserDetailsSO userSO, String message) {

		// ExternalProperties.getDbConfig("email.sendgrid.apikey");

		Email from = new Email(ExternalProperties.getAppConfig("email.sendgrid.user"));
		String subject = "Sendgrid test mail";

		Email to = new Email(userSO.getEmailId());
		Content content = new Content("text/plain", message);
		Mail mail = new Mail(from, subject, to, content);

		SendGrid sg = new SendGrid(ExternalProperties.getAppConfig("email.sendgrid.apikey"));
		Request request = new Request();
		try {

			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());

			Response response = sg.api(request);
			System.out.println(response.getStatusCode());
			System.out.println(response.getBody());
			System.out.println(response.getHeaders());

			System.out.println("Mail sent successfully...");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return "success";
	}
}