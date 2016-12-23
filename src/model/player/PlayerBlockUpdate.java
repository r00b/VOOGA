package model.player;

/**
 * This class wraps an update when the model.grid shrinks and the model.player's location needs to be updated.
 *
 * @author Aninda Manocha
 */

public class PlayerBlockUpdate {

    private PlayerUpdate myUpdate;
    private int myOffset;

    public PlayerBlockUpdate(PlayerUpdate update, int offset) {
        myUpdate = update;
        myOffset = offset;
    }

    /**
     * Gets the property of the model.player that was updated (either the row or column) when the model.grid shrinks
     *
     * @return the model.player property
     */
    public PlayerUpdate getUpdate() {
        return myUpdate;
    }

    /**
     * Gets the amount by which the model.player property changes (the offset)
     *
     * @return the offset
     */
    public int getOffset() {
        return myOffset;
    }
}
