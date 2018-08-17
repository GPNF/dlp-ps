package app.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import app.model.PubsubSO;
import app.model.UserDetailsSO;
import app.util.ExternalProperties;

public class SmsSender {

	public String sendSms(UserDetailsSO userSO, String msg) {

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
		System.out.println("MobNo. " + userSO.getMobileNumber());
		Message message = Message.creator(new PhoneNumber(userSO.getMobileNumber()), // to
				new PhoneNumber(ExternalProperties.getAppConfig("sms.sender.number")), // from
				msg).create();

		System.out.println("SMS sent successfully... " + message.getSid());

		return message.getSid();

	}
}