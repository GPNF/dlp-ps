package app.service.thirdparty;

import com.twilio.Twilio;
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
	 * this method calls the twilio api to send sms to users
	 * 
	 * @param userDetailsSO
	 * @param message
	 * @return String
	 */
	public String sendSms(String receiverMobNumber, String actualMessage) {
		String ack = "failed";

		String sid = ExternalProperties.getAppConfig("sms.auth.sid");
		String decryptedSid = AES.decrypt(sid);

		String token = ExternalProperties.getAppConfig("sms.auth.token");
		String decryptedToken = AES.decrypt(token);

		Twilio.init(decryptedSid, decryptedToken);

		String formattedMessage = "Dear Customer,\n" + actualMessage;

		Message twilioMessage = null;

		String senderNumber = ExternalProperties.getAppConfig("sms.sender.number");
		twilioMessage = Message.creator(new PhoneNumber(receiverMobNumber), // to
				new PhoneNumber(senderNumber), // from
				formattedMessage).create();

		if (twilioMessage != null && null != twilioMessage.getSid())
			ack = "ReceivedByTwilio";

		return ack;

	}
}