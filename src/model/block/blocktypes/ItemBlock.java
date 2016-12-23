package model.block.blocktypes;

import model.block.AbstractBlock;

import java.util.HashSet;
import java.util.Set;

/**
 * Class for items found in the level -- can interact with gates
 *
 * @author Ryan Anders
 */
public abstract class ItemBlock extends AbstractBlock {
    private Set<GateBlock> myGates;

    public ItemBlock(String name, int row, int col) {
        super(name, row, col);
        myGates = new HashSet<GateBlock>();
    }

    public void connectToGate(GateBlock gate) {
        myGates.add(gate);
    }

    public void toggleGate(GateBlock gate) {
        gate.toggleOpenStatus();
    }

    public void disconnectFromGate(GateBlock gate) {
        myGates.remove(gate);
    }
}
