package app.service.thirdparty;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import app.util.ExternalProperties;

/**
 * @author AmolPol
 *
 */
public class TwilioSmsClient {

	/**
	 * @param userDetailsSO
	 * @param message
	 * @return String
	 */
	public String sendSms(String receiverMobNumber, String actualMessage) {

		String sid = ExternalProperties.getAppConfig("sms.auth.sid");
		String token = ExternalProperties.getAppConfig("sms.auth.token");
		Twilio.init(sid, token);
		String ack=null;

		/*
		 * ProxiedTwilioClientCreator clientCreator = new
		 * ProxiedTwilioClientCreator( prop.getProperty("sms.auth.sid"),
		 * prop.getProperty("sms.auth.token"), "127.0.0.1", "8888");
		 * TwilioRestClient twilioRestClient = clientCreator.getClient();
		 * Twilio.setRestClient(twilioRestClient);
		 */
		System.out.println("MobNo. " + receiverMobNumber);
		Message twilioMessage = Message.creator(new PhoneNumber(receiverMobNumber), // to
				new PhoneNumber(ExternalProperties.getAppConfig("sms.sender.number")), // from
				actualMessage).create();

		

		if(null!=twilioMessage.getSid())
		{
				ack= "success";
				System.out.println("SMS sent successfully... " + twilioMessage.getSid());
		}
		else
		{
			ack= "failed";
		}
		return ack;

	}
}