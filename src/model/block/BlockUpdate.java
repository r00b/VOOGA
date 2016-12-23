package model.block;

/**
 * Container class which lets observer know what to do to a changing model.block. Used by the InteractionHandler class to
 * do the correct action based on the type of update to complete and to which model.block it should be applied.
 *
 * @author Filip Mazurek, Aninda Manocha
 */

public class BlockUpdate {
    private BlockUpdateType myUpdateType;
    private int myRow;
    private int myColumn;
    private String myContent;

    public BlockUpdate(BlockUpdateType updateType, int row, int col, String content) {
        myUpdateType = updateType;
        myRow = row;
        myColumn = col;
        myContent = content;
    }

    public BlockUpdateType getType() {
        return myUpdateType;
    }

    public int getRow() {
        return myRow;
    }

    public int getColumn() {
        return myColumn;
    }

    public String getContent() {
        return myContent;
    }
}
