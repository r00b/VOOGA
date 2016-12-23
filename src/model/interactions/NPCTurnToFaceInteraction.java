package model.interactions;

import api.Player;
import model.block.BlockUpdate;
import model.block.BlockUpdateType;
import model.block.blocktypes.NPCBlock;
import model.block.NPCDirection;

import java.util.ArrayList;
import java.util.List;

/**
 * Interaction so that NPC's may turn to face the model.player character
 *
 * @author Filip Mazurek
 */
public class NPCTurnToFaceInteraction implements Interaction {
    private NPCBlock myNPCBlock;

    public NPCTurnToFaceInteraction(NPCBlock block) {
        myNPCBlock = block;
    }

    public List<BlockUpdate> act(Player player) {
        List<BlockUpdate> updatesList = new ArrayList<>();

        String status = "";

        switch (player.getDirection()) {
            case NORTH:
                NPCDirection down = NPCDirection.DOWN;
                myNPCBlock.switchDirection(down);
                status = down.toString().toLowerCase();
                break;
            case SOUTH:
                NPCDirection up = NPCDirection.UP;
                myNPCBlock.switchDirection(up);
                status = up.toString().toLowerCase();
                break;
            case EAST:
                NPCDirection left = NPCDirection.LEFT;
                myNPCBlock.switchDirection(left);
                status = left.toString().toLowerCase();
                break;
            case WEST:
                NPCDirection right = NPCDirection.RIGHT;
                myNPCBlock.switchDirection(right);
                status = right.toString().toLowerCase();
                break;
        }

        String newName = myNPCBlock.replaceNameStatus(myNPCBlock.getName(), status);
        updatesList.add(new BlockUpdate(BlockUpdateType.RE_RENDER, myNPCBlock.getRow(), myNPCBlock.getCol(), newName));
        return updatesList;
    }
}
