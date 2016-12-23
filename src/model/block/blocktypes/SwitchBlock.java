package model.block.blocktypes;

import api.Block;
import model.block.AbstractBlock;
import model.block.BlockUpdate;
import model.block.BlockUpdateType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A top-level switch class which may interact with board ui.scenes.controller.editor.objects which respond to a switch. Toggles
 * the status of the blocks to which it is connected.
 *
 * @author Filip Mazurek, Aninda Manocha
 */

public abstract class SwitchBlock extends AbstractBlock {
    private Set<GateBlock> myGates;
    private boolean onPosition;
    private static final String onString = "ON";
    private static final String offString = "OFF";

    public SwitchBlock(String name, int row, int col) {
        super(name, row, col);
        myGates = new HashSet<>();
        onPosition = false;
    }

    /**
     * For all gates to which the switch is connected, toggle their open status.
     *
     * @return list of updates which the front end needs to render.
     */
    public List<BlockUpdate> toggleGates() {
        List<BlockUpdate> blockUpdates = new ArrayList<>();
        for (GateBlock oneGate : myGates) {
            blockUpdates.add(oneGate.toggleOpenStatus());
        }

        int extensionLoc = getName().lastIndexOf('.');
        String extension = getName().substring(extensionLoc);
        int statusLoc = getName().lastIndexOf('-');
        String status;

        if (onPosition) {
            status = offString;
            onPosition = false;
        } else {
            status = onString;
            onPosition = true;
        }

        String newName = replaceNameStatus(getName(), status);
        blockUpdates.add(new BlockUpdate(BlockUpdateType.RE_RENDER, getRow(), getCol(), newName));

        return blockUpdates;
    }

    /**
     * Accept linkage with GateBlocks.
     *
     * @param block:     model.block to be linked to from this model.block
     * @param gridIndex: the model.grid on which the model.block resides (for multiple model.grid levels)
     * @return whether the connection was successful
     */
    @Override
    public boolean link(Block block, int gridIndex) {
        if (block instanceof GateBlock) {
            myGates.add((GateBlock) block);
            return true;
        }
        return false;
    }

    /**
     * Remove the linkage with the selected GateBlock
     *
     * @param block: model.block with which the link must be broken
     * @return whether the unlinkage was successful.
     */
    @Override
    public boolean unlink(Block block) {
        if (block instanceof GateBlock) {
            myGates.remove(block);
            return true;
        }
        return false;
    }
}
