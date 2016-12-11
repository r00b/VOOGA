package block;

/**
 * Container class which lets observer know what to do to a changing block
 *
 * @author Filip Mazurek
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
