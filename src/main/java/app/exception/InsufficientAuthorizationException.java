package app.exception;

/**
 * InsufficientAuthorizationException is subclass of Exception, a form of
 * Throwable that indicates conditions that a reasonable application might want
 * to catch.
 * 
 * @author AdarshSinghal
 *
 */
public class InsufficientAuthorizationException extends Exception {

	private static final long serialVersionUID = -4596417175637568289L;

	public InsufficientAuthorizationException() {
		super("Source is not authorized to send message to the group");
	}

}
