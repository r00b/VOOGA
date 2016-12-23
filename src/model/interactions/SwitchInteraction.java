package model.interactions;

import api.Player;
import model.block.BlockUpdate;
import model.block.blocktypes.SwitchBlock;

import java.util.List;

/**
 * Add ability for a switch to interact with a gate to open it. This will for the gate to become walkable in the back
 * end, as well as force the front end to redraw the gate so that the user may see the change.
 *
 * @author Filip Mazurek, Aninda Manocha
 */

public class SwitchInteraction implements Interaction {
    private SwitchBlock mySwitchBlock;

    public SwitchInteraction(SwitchBlock switchBlock) {
        mySwitchBlock = switchBlock;
    }

    public List<BlockUpdate> act(Player player) {
        return mySwitchBlock.toggleGates();
    }
}
