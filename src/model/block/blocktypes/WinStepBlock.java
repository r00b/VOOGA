package model.block.blocktypes;

import model.interactions.WinInteraction;

/**
 * Block which allows the model.player to win by stepping on it.
 *
 * @author Filip Mazurek
 */
public class WinStepBlock extends WinBlock {

    public WinStepBlock(String name, int row, int col) {
        super(name, row, col);
        setWalkableStatus(true);
        addStepInteraction(new WinInteraction());
    }
}
