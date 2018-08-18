package app.util;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;

import app.dao.UserDetailsDao;
import app.model.UserDetailsSO;
import app.service.SendMailSendgrid;
import app.service.SmsSender;

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
	public void checkAllUserPreference(String message) throws SQLException {

		UserDetailsDao userDetailsDao = new UserDetailsDao();

		List<UserDetailsSO> allUsers = userDetailsDao.getAllUserDetails();

		if (null != allUsers) {
			for (UserDetailsSO userDetails : allUsers) {
				if (userDetails.getEmailFlag().equalsIgnoreCase(YES)) {

					notifyUsersByEmail(userDetails, message);
				}
				if (userDetails.getSmsFlag().equalsIgnoreCase(YES)) {

					notifyUsersBySMS(userDetails, message);
				}
			}
		}

	}

	/**
	 * @param userDetails
	 * @param message
	 */
	public void notifyUsersBySMS(UserDetailsSO userDetails, String message) {

		boolean isSmsEnabled = null != userDetails.getSmsFlag() && userDetails.getSmsFlag().equalsIgnoreCase(YES);
		if (isSmsEnabled) {
			SmsSender sms = new SmsSender();
			sms.sendSms(userDetails, message);

		}
	}

	/**
	 * @param userDetails
	 * @param message
	 */
	public void notifyUsersByEmail(UserDetailsSO userDetails, String message) {

		boolean isEmailEnabled = null != userDetails.getEmailFlag() && userDetails.getEmailFlag().equalsIgnoreCase(YES);
		if (isEmailEnabled) {
			SendMailSendgrid mail = new SendMailSendgrid();
			mail.sendEmail(userDetails, message);
		}
	}

}