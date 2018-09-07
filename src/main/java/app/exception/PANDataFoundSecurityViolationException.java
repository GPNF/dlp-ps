package app.exception;

/**
 * 
 * PANDataFoundSecurityViolationException is subclass of Exception, a form of
 * Throwable that indicates conditions that a reasonable application might want
 * to catch.
 * 
 * @author AdarshSinghal
 *
 */
public class PANDataFoundSecurityViolationException extends Exception {

	private static final long serialVersionUID = 6920961081080292426L;

	public PANDataFoundSecurityViolationException() {
		super("Security Violation. PAN data exist.");
	}

}
