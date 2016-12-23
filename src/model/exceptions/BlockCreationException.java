package model.exceptions;

/**
 * Exception which is thrown if a model.block cannot be created.
 *
 * @author Filip Mazurek
 */

public class BlockCreationException extends Exception implements Alert {

    public BlockCreationException() {
        super();

    }

    @Override
    public String getMessage() {
        return String.format(BLOCK_CREATION);
    }
}
