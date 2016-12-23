package model.exceptions;

/**
 * An exception that occurs when the user tries to shrink the model.grid in a way that will result in deleting the model.player
 *
 * @author Aninda Manocha
 */

public class DeletePlayerWarning extends Exception implements Alert {

    public DeletePlayerWarning() {
        super();
    }

    @Override
    public String getMessage() {
        return String.format(DELETE_PLAYER);
    }
}
