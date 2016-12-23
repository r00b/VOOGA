package model.interactions;

import api.Player;
import model.block.BlockUpdate;
import model.block.BlockUpdateType;

import java.util.ArrayList;
import java.util.List;

/**
 * Interaction class which will give a message to the front end to display.
 *
 * @author Filip Mazurek, Aninda Manocha
 */
public class MessageInteraction implements Interaction {
    private int myRow;
    private int myCol;
    private String myMessage;

    public MessageInteraction(int row, int col, String message) {
        myRow = row;
        myCol = col;
        myMessage = message;
    }

    public List<BlockUpdate> act(Player player) {
        List<BlockUpdate> updateList = new ArrayList<>();
        updateList.add(new BlockUpdate(BlockUpdateType.DISPLAY_MESSAGE, myRow, myCol, myMessage));
        return updateList;
    }
}
