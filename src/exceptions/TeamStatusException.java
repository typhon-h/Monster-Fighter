package exceptions;

/**
 * No team members are available to battle
 *
 * @author Harrison Tyson
 * @version 1.0, Apr 2022.
 */
public class TeamStatusException extends Exception {
    /**
     * Constructor for TeamStatusException
     */
    public TeamStatusException() {
        super("No available monsters in team");
    }

}
