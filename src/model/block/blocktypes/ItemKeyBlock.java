package model.block.blocktypes;


/**
 * a key that is picked up by the model.player when walked upon
 *
 * @author Ryan Anders
 */
public class ItemKeyBlock extends ItemBlock {

    public ItemKeyBlock(String name, int row, int col) {
        super(name, row, col);
        setWalkableStatus(true);
    }

}