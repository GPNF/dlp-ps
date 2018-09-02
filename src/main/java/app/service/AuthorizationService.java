package app.service;

import java.sql.SQLException;

import app.dao.UserGroupDetailsDAO;
import app.exception.ExternalUserNotAllowedException;
import app.exception.InsufficientAuthorizationException;
import app.exception.NoSuchGroupException;
import app.model.SourceMessage;

/**
 * Authorization Service is responsible for handling authorization for both
 * Notiy as well as Notification Service.
 * 
 * @author adarshsinghal
 *
 */
public class AuthorizationService {

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

	private void checkForExternalUser(SourceMessage srcMessage) throws ExternalUserNotAllowedException {
		if (srcMessage.getSourceauthLevel() == 0)
			throw new ExternalUserNotAllowedException();
	}

	private void checkSourceToGroupAuthorization(SourceMessage srcMessage)
			throws SQLException, NoSuchGroupException, InsufficientAuthorizationException {
		UserGroupDetailsDAO userGroupDetailsDAO = new UserGroupDetailsDAO();
		int grpAuthLvl = userGroupDetailsDAO.getAuthLevel(srcMessage.getGroupId());

		if (srcMessage.getSourceauthLevel() < grpAuthLvl) {
			throw new InsufficientAuthorizationException();
		}
	}

}
