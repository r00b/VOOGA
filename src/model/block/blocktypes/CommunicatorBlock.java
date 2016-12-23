package model.block.blocktypes;

import model.block.AbstractBlock;
import model.interactions.MessageInteraction;

/**
 * A board object with which the model.player character will talk in order to display a message to the user.
 *
 * @author Filip Mazurek, Aninda Manocha
 */
public class CommunicatorBlock extends AbstractBlock {

    private String myMessage;

    public CommunicatorBlock(String name, int row, int col) {
        super(name, row, col);
    }

    /**
     * Sets the message which should be displayed when the model.block is talked to in the game.
     *
     * @param message: message to be displayed
     */
    public void setMessage(String message) {
        addTalkInteraction(new MessageInteraction(this.getRow(), this.getCol(), message));
        myMessage = message;
    }

    public String getMessage() {
        return myMessage;
    }

}
