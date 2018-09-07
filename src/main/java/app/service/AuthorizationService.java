package app.service;

import java.sql.SQLException;
import java.util.logging.Logger;

import app.dao.UserGroupDetailsDAO;
import app.exception.ExternalUserNotAllowedException;
import app.exception.InsufficientAuthorizationException;
import app.exception.NoSuchGroupException;
import app.model.SourceMessage;

/**
 * Authorization Service is responsible for handling authorization for both
 * Notiy as well as Notification Service.
 * 
 * <br><br>
 * Service class should be 'As Simple As Possible'. 
 * If you're modifying this class, add operation &amp; delegate logics to other class
 * 
 * @author AdarshSinghal
 *
 */
public class AuthorizationService {

	private static Logger LOGGER = Logger.getLogger(AuthorizationService.class.getName());

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
		checkForExternalUser(srcMessage);
		checkSourceToGroupAuthorization(srcMessage);
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
			LOGGER.warning("External User tried to send message.");
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
		UserGroupDetailsDAO userGroupDetailsDAO = new UserGroupDetailsDAO();
		int grpAuthLvl = userGroupDetailsDAO.getAuthLevel(srcMessage.getGroupId());

		if (srcMessage.getSourceauthLevel() < grpAuthLvl) {
			LOGGER.warning("Insufficient Authorization");
			throw new InsufficientAuthorizationException();
		}
	}

}
