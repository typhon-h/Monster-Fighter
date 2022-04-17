package exceptions;

/**
 * Applying a {@link main.Trigger} to a {@link monsters.Monster} that already
 * has the given {@link main.Trigger}.
 *
 * @author Jackie Jone
 * @version 1.0, Apr 2022.
 */
public class UnusableItemException extends Exception {
    /**
     * Constructor for DuplicateTriggerException.
     *
     * @param message The message to be displayed.
     */
    public UnusableItemException(String message) {
        super(message);
    }
}
