package model.block.blocktypes;

import api.Block;
import model.block.AbstractBlock;
import model.interactions.Interaction;
import model.interactions.TeleportInteraction;

/**
 * This send model.block sends the model.player to its corresponding receive model.block by changing the model.player position.
 *
 * @author Aninda Manocha, Filip Mazurek
 */

public class TeleportBlock extends AbstractBlock {
    private TeleportBlock myReceiveBlock;

    public TeleportBlock(String name, int row, int col) {
        super(name, row, col);
        setWalkableStatus(true);
    }

    /**
     * Link the model.block to another TeleportBlock.
     *
     * @param receiver:  TeleportBlcok to which stepping on this model.block will send the model.player character
     * @param gridIndex: the model.grid on which the model.block resides (for multiple model.grid levels)
     * @return whether the linkage was successful
     */
    @Override
    public boolean link(Block receiver, int gridIndex) {
        if (receiver instanceof TeleportBlock) {
            unlink(receiver);
            myReceiveBlock = (TeleportBlock) receiver;
            addStepInteraction(new TeleportInteraction(receiver.getRow(), receiver.getCol(), gridIndex));
            return true;
        }
        return false;
    }

    /**
     * Unlink this model.block from the selected TeleportBlock.
     *
     * @param receiver: TeleportBlock to which this model.block is connected.
     * @return whether the unlink was successful.
     */
    @Override
    public boolean unlink(Block receiver) {
        if (receiver.equals(myReceiveBlock)) {
            for (Interaction interaction : getStepInteractions()) { // if a new destination is set, old one is erased
                if (interaction instanceof TeleportInteraction) {
                    removeStepInteraction(interaction);
                }
            }
            myReceiveBlock = null;
            return true;
        }
        return false;
    }
}
