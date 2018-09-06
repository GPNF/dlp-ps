package app.exception;

/**
 * @author AdarshSinghal
 *
 */
public class NoSuchGroupException extends Exception {

	private static final long serialVersionUID = 5753728217139265565L;

	public NoSuchGroupException() {
		super("The group on which source tried to post does not exist.");
	}

}
