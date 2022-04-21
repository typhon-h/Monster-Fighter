package exceptions;

/**
 * Duplicate monster tried to be added to team
 *
 * @author Harrison Tyson
 * @version 1.0, Apr 2022.
 */
public class DuplicateMonsterException extends Exception {

    /**
     * Constructor for DuplicateMonsterException
     *
     * @param message message to be displayed
     */
    public DuplicateMonsterException(String message) {
        super(message);
    }

}
