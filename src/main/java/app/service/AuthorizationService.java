package app.service;

import java.sql.SQLException;

import app.exception.ExternalUserNotAllowedException;
import app.exception.InsufficientAuthorizationException;
import app.exception.NoSuchGroupException;
import app.logging.CloudLogger;
import app.model.SourceMessage;

/**
 * Authorization Service is responsible for handling authorization for both
 * Notiy as well as Notification Service.
 * 
 * <br>
 * <br>
 * Service class should be 'As Simple As Possible'. If you're modifying this
 * class, add operation &amp; delegate logics to other class
 * 
 * @author AdarshSinghal
 *
 */
public class AuthorizationService {

	private CloudLogger LOGGER = CloudLogger.getLogger();

	/**
	 * @param srcMessage
	 * @return boolean
	 * @throws SQLException
	 * @throws ExternalUserNotAllowedException
	 * @throws NoSuchGroupException
	 * @throws InsufficientAuthorizationException
	 */
	public void checkSourceAuthorization(SourceMessage srcMessage) throws SQLException, ExternalUserNotAllowedException,
			NoSuchGroupException, InsufficientAuthorizationException {
		LOGGER.info("Inside Authorization Service. Checking for External user.");
		checkForExternalUser(srcMessage);
		LOGGER.info("Inside Authorization Service. Internal User verified. \n"
				+ "Checking if source has sufficient authorization to post on group. Source Message \n" + srcMessage);
		checkSourceToGroupAuthorization(srcMessage);
		LOGGER.info("Inside Authorization Service. Source has sufficient authorization.");
	}

	/**
	 * The application should be accessible by Internal Users only. Terminate if
	 * External User.
	 * 
	 * @param srcMessage
	 * @throws ExternalUserNotAllowedException
	 */
	private void checkForExternalUser(SourceMessage srcMessage) throws ExternalUserNotAllowedException {
		if (srcMessage.getSourceauthLevel() == 0) {
			LOGGER.warning("Inside Authorization Service. Throwing ExternalUserNotAllowedException. "
					+ "\nTransaction terminated. " + "Reason:- External User tried to send message.");
			throw new ExternalUserNotAllowedException();
		}
	}

	/**
	 * Source Authorization Level must be equal or greater than required
	 * authorization level for targeting a group for messaging.
	 * 
	 * @param srcMessage
	 * @throws SQLException
	 * @throws NoSuchGroupException
	 * @throws InsufficientAuthorizationException
	 */
	private void checkSourceToGroupAuthorization(SourceMessage srcMessage)
			throws SQLException, NoSuchGroupException, InsufficientAuthorizationException {

		LOGGER.info("Inside Authorization Service. Using User Service to retrieve Group Auth Level of Group id: "
				+ srcMessage.getGroupId());

		UserService userService = new UserService();
		int grpAuthLvl = userService.getGroupAuthLevel(srcMessage.getGroupId());

		if (srcMessage.getSourceauthLevel() < grpAuthLvl) {
			LOGGER.warning("Inside Authorization Service. Throwing InsufficientAuthorizationException. "
					+ "Transaction terminated. Reason:- Insufficient authorization.");
			throw new InsufficientAuthorizationException();
		}
	}

}
