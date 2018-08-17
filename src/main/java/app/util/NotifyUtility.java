package app.util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;

import app.DBHelper;
import app.model.UserDetailsSO;
import app.service.SendMailSendgrid;
import app.service.SmsSender;

public class NotifyUtility {
	Properties prop;

	public void checkAllUserPreference(String message) throws ServletException, SQLException {
		
		DBHelper dbHelper = new DBHelper();

	
		List<UserDetailsSO> allUsers = dbHelper.getAllUserDetails();
	
		if (null != allUsers) {
			for (UserDetailsSO userDet : allUsers) {
				if (userDet.getEmailFlag().equalsIgnoreCase("Yes")) {
					
					notifyUsersByEmail(userDet, message);
				}
				if (userDet.getSmsFlag().equalsIgnoreCase("Yes")) {
					
					notifyUsersBySMS(userDet, message);
				}
			}
		}

	}

	

	public void notifyUsersBySMS(UserDetailsSO userDet, String message) {

		if (null != userDet.getSmsFlag() && userDet.getSmsFlag().equalsIgnoreCase("Yes")) {
			SmsSender sms = new SmsSender();
			sms.sendSms(userDet, message);

		}
		// return "Message sent successfully to user " + userDet.getUserId();
	}

	public void notifyUsersByEmail(UserDetailsSO userDet, String message) {

		if (null != userDet.getEmailFlag() && userDet.getEmailFlag().equalsIgnoreCase("Yes")) {
			SendMailSendgrid mail = new SendMailSendgrid();
			mail.sendEmail(userDet, message);

		}
		// return "Message sent successfully to user " + userDet.getUserId();
	}

}