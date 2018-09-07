package app.exception;

/**
 * ExternalUserNotAllowedException is subclass of Exception, a form of Throwable
 * that indicates conditions that a reasonable application might want to catch.
 * 
 * @author AdarshSinghal
 *
 */
public class ExternalUserNotAllowedException extends Exception {

	private static final long serialVersionUID = 8817246728631797955L;

	public ExternalUserNotAllowedException() {
		super("External User is not allowed to access application");
	}

}
