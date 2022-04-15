package exceptions;

/**
 * Operation tried to mutate a team beyond its size boundaries
 *
 * @author Harrison Tyson
 * @version 1.0, Apr 2022.
 */
public class TeamSizeException extends Exception {
    /**
     * Constructor for TeamSizeException
     *
     * @param message message to be displayed
     */
    public TeamSizeException(String message) {
        super(message);
    }
}
