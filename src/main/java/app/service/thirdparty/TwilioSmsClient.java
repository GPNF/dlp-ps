package app.service.thirdparty;

import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import app.util.AES;
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
		String decryptedSid = AES.decrypt(sid);

		String token = ExternalProperties.getAppConfig("sms.auth.token");
		String decryptedToken = AES.decrypt(token);

		Twilio.init(decryptedSid, decryptedToken);

		/*
		 * ProxiedTwilioClientCreator clientCreator = new
		 * ProxiedTwilioClientCreator( prop.getProperty("sms.auth.sid"),
		 * prop.getProperty("sms.auth.token"), "127.0.0.1", "8888");
		 * TwilioRestClient twilioRestClient = clientCreator.getClient();
		 * Twilio.setRestClient(twilioRestClient);
		 */
		String formattedMessage="Dear Customer,\n"+actualMessage;
		System.out.println("MobNo. " + receiverMobNumber);
		Message twilioMessage=null;
		try {
			String senderNumber = ExternalProperties.getAppConfig("sms.sender.number");
			twilioMessage = Message.creator(new PhoneNumber(receiverMobNumber), // to
					new PhoneNumber(senderNumber), // from
					formattedMessage).create();
		} catch(ApiException e) {
			e.printStackTrace();
		}
		

		String ack = null;
		if (twilioMessage!=null && null != twilioMessage.getSid()) {
			ack = "success";
			System.out.println("SMS sent successfully... " + twilioMessage.getSid());
		} else {
			ack = "failed";
		}
		return ack;

	}
}