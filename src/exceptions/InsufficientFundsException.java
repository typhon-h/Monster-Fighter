package exceptions;

/**
 * {@link main.Player Player} tries to withdraw more gold than they have
 *
 * @author Harrison Tyson
 * @version 1.0, Apr 2022.
 */
public class InsufficientFundsException extends Exception {
    /**
     * Serial version of class
     */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiate error with display message
     *
     * @param message message to be displayed
     */
    public InsufficientFundsException(String message) {
        super(message);
    }

}
