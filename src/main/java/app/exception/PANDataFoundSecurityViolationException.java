package app.exception;

/**
 * @author AdarshSinghal
 *
 */
public class PANDataFoundSecurityViolationException extends Exception {

	private static final long serialVersionUID = 6920961081080292426L;

	public PANDataFoundSecurityViolationException() {
		super("Security Violation. PAN data exist.");
	}

}
