package app.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import app.model.UserDetailsSO;
import app.util.ExternalProperties;

/**
 * @author AmolPol
 *
 */
public class SmsSenderService {

	/**
	 * @param userDetailsSO
	 * @param message
	 * @return String
	 */
	public String sendSms(UserDetailsSO userDetailsSO, String message) {

		String sid = ExternalProperties.getAppConfig("sms.auth.sid");
		String token = ExternalProperties.getAppConfig("sms.auth.token");
		Twilio.init(sid, token);

		/*
		 * ProxiedTwilioClientCreator clientCreator = new
		 * ProxiedTwilioClientCreator( prop.getProperty("sms.auth.sid"),
		 * prop.getProperty("sms.auth.token"), "127.0.0.1", "8888");
		 * TwilioRestClient twilioRestClient = clientCreator.getClient();
		 * Twilio.setRestClient(twilioRestClient);
		 */
		System.out.println("MobNo. " + userDetailsSO.getMobileNumber());
		Message twilioMessage = Message.creator(new PhoneNumber(userDetailsSO.getMobileNumber()), // to
				new PhoneNumber(ExternalProperties.getAppConfig("sms.sender.number")), // from
				message).create();

		System.out.println("SMS sent successfully... " + twilioMessage.getSid());

		return twilioMessage.getSid();

	}
}