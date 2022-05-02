package exceptions;

/**
 * Duplicate {@link monsters.Monster monster} tried to be added to
 * {@link main.Team team}
 *
 * @author Harrison Tyson
 * @version 1.0, Apr 2022.
 */
public class DuplicateMonsterException extends Exception {

    /**
     * Instantiate error with display message
     *
     * @param message message to be displayed
     */
    public DuplicateMonsterException(String message) {
        super(message);
    }

}
