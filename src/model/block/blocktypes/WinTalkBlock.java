package model.block.blocktypes;

import model.interactions.WinInteraction;

/**
 * Block which allows the model.player to win by talking to it.
 *
 * @author Filip Mazurek
 */
public class WinTalkBlock extends WinBlock {

    public WinTalkBlock(String name, int row, int col) {
        super(name, row, col);
        setWalkableStatus(false);
        addTalkInteraction(new WinInteraction());
    }
}
