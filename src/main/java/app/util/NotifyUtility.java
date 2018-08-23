package app.util;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;

import app.dao.UserDetailsDao;
import app.model.RequestMapper;
import app.model.UserDetailsSO;
import app.service.EmailSender;
import app.service.SmsSender;
import app.servlet.HttpClientRequestHandler;

/**
 * @author AmolPol
 *
 */
public class NotifyUtility {
	private static final String YES = "Yes";

	/**
	 * @param message
	 * @throws ServletException
	 * @throws SQLException
	 */
	public boolean checkAllUserPreference(RequestMapper req) throws SQLException {

		UserDetailsDao userDetailsDao = new UserDetailsDao();
		List<UserDetailsSO> allUsers = userDetailsDao.getAllUserDetails();

		boolean delivered = false;

		if (null != allUsers) {
			for (UserDetailsSO userDetails : allUsers) {
				delivered = notifyUser(req, userDetails);
			}
			if (delivered)
				deliveryConfirmation(req, delivered);
		}
		return delivered;
	}

	/**
	 * @param req
	 * @param userDetails
	 * @return
	 */
	private boolean notifyUser(RequestMapper req, UserDetailsSO userDetails) {
		boolean delivered;
		String emailAck = null;
		String smsAck = null;
		if (userDetails.getEmailFlag().equalsIgnoreCase(YES))
			emailAck = notifyUsersByEmail(userDetails, req.getMessageData());

		if (userDetails.getSmsFlag().equalsIgnoreCase(YES))
			smsAck = notifyUsersBySMS(userDetails, req.getMessageData());

		if ((emailAck != null || smsAck != null) && (emailAck.contains("success") || smsAck.contains("success"))) {
			delivered = true;
		} else {
			delivered = false;
		}
		return delivered;
	}

	/**
	 * @param req
	 * @param delivered
	 */
	private void deliveryConfirmation(RequestMapper req, boolean delivered) {
		String statusDbUrl = "https://possible-haven-212003.appspot.com/queryMessageStat";
		//String statusDbUrl = "http://localhost:8080/queryMessageStat";
		HttpClientRequestHandler client = new HttpClientRequestHandler();

		req.setDeliveryFlag("true");

		client.processRequest(req, statusDbUrl);

	}

	/**
	 * @param userDetails
	 * @param message
	 */
	public String notifyUsersBySMS(UserDetailsSO userDetails, String message) {
		String ack = null;
		boolean isSmsEnabled = null != userDetails.getSmsFlag() && userDetails.getSmsFlag().equalsIgnoreCase(YES);
		if (isSmsEnabled) {
			SmsSender sms = new SmsSender();
			ack = sms.sendSms(userDetails, message);

		}
		return ack;
	}

	/**
	 * @param userDetails
	 * @param message
	 */
	public String notifyUsersByEmail(UserDetailsSO userDetails, String message) {
		String ack = null;
		boolean isEmailEnabled = null != userDetails.getEmailFlag() && userDetails.getEmailFlag().equalsIgnoreCase(YES);
		if (isEmailEnabled) {
			EmailSender mail = new EmailSender();
			ack = mail.sendEmail(userDetails, message);
		}
		return ack;
	}

}