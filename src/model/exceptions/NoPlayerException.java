package model.exceptions;

/**
 * Exception thrown when the user attempts to prepare a game to be played without a model.player character placed on the game
 * model.grid.
 *
 * @author Filip Mazurek
 */

public class NoPlayerException extends Exception implements Alert {

    public NoPlayerException() {
        super();

    }

    @Override
    public String getMessage() {
        return String.format(NO_PLAYER);
    }
}
