package model.exceptions;

/**
 * Exception thrown in the controller.editor when the user attempts to place their model.player character in a model.block which the model.player
 * should not be able to walk on.
 *
 * @author Filip Mazurek
 */
public class BadPlayerPlacementException extends Exception implements Alert {
    private int myRow;
    private int myColumn;


    public BadPlayerPlacementException(int row, int col) {
        super();
        myRow = row;
        myColumn = col;
    }

    @Override
    public String getMessage() {
        return String.format(BAD_PLAYER_PLACEMENT, Integer.toString(myRow), Integer.toString(myColumn));
    }
}

