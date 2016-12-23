package model.block.blocktypes;

import model.block.AbstractBlock;

/**
 * Abstract model.block class which allows for the model.player to win by some sort of interaction.
 *
 * @author Filip Mazurek
 */
public abstract class WinBlock extends AbstractBlock {
    public WinBlock(String name, int row, int col) {
        super(name, row, col);
    }
}
