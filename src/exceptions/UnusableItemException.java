package exceptions;

/**
 * Applying a {@link monsters.Trigger} to a {@link monsters.Monster} that already
 * has the given {@link monsters.Trigger}.
 *
 * @author Jackie Jone
 * @version 1.0, Apr 2022.
 */
public class UnusableItemException extends Exception {
    /**
     * Serial version of class
     */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiate error with display message
     *
     * @param message The message to be displayed.
     */
    public UnusableItemException(String message) {
        super(message);
    }
}
