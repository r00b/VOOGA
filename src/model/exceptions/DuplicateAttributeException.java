package model.exceptions;

/**
 * An exception that occurs when the user tries to create an attribute that already exists
 *
 * @author Aninda Manocha
 */

public class DuplicateAttributeException extends Exception implements Alert {
    public DuplicateAttributeException() {
        super();

    }

    @Override
    public String getMessage() {
        return String.format(DUPLICATE_PLAYER_ATTRIBUTE);
    }
}
