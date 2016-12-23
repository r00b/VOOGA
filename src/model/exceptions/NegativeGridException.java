package model.exceptions;

/**
 * Exception thrown when the user attempts to modify the model.grid to be impossibly small.
 *
 * @author Filip Mazurek
 */
public class NegativeGridException extends Exception implements Alert {
    public NegativeGridException() {
        super();
    }

    @Override
    public String getMessage() {
        return String.format(NEGATIVE_GRID);
    }
}

