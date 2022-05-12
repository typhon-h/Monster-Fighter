package exceptions;

/**
 * Operation tried to mutate a {@link main.Team team} beyond its size boundaries
 *
 * @author Harrison Tyson
 * @version 1.0, Apr 2022.
 */
public class TeamSizeException extends Exception {
    /**
     * Serial version of class
     */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiate error with display message
     *
     * @param message message to be displayed
     */
    public TeamSizeException(String message) {
        super(message);
    }
}
