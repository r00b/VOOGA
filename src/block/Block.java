package block;

import api.IBlock;
import interactions.Interaction;
import player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

/**
 * The general type of object which may be placed on the board.
 *
 * @author Filip Mazurek, Daniel Chai, Aninda Manocha
 */

public abstract class Block extends Observable implements IBlock {

    private String myName;
    private int myRow;
    private int myCol;
    private boolean isWalkable;
    private List<Interaction> myStepInteractions;
    private List<Interaction> myTalkInteractions;
    private String myMessage;

    public Block(String name,  int row, int col) {
        myName = name;
        myRow = row;
        myCol = col;
        isWalkable = false;
        myStepInteractions = new ArrayList<>();
        myTalkInteractions = new ArrayList<>();
    }

    public List<BlockUpdate> stepInteract(Player player) {
        List<BlockUpdate> blockUpdates = new ArrayList<>();
        for (Interaction interaction : myStepInteractions) {
            blockUpdates.addAll(interaction.act(player));
        }
        return blockUpdates;
    }
    
    public List<BlockUpdate> talkInteract(Player player){
        List<BlockUpdate> blockUpdates = new ArrayList<>();
        if (myTalkInteractions.size() > 0) {
            for(Interaction interaction : myTalkInteractions) {
                blockUpdates.addAll(interaction.act(player));
            }
            setChanged();
            notifyObservers(myMessage);
        }
        return blockUpdates;

    }

    public boolean link(Block block, int gridIndex) {
        return false;
    }

    public boolean unlink(Block block) {
        return false;
    }

    /*****GETTERS*****/

    public String getName() {
        return myName;
    }

    public int getRow() {
        return myRow;
    }

    public int getCol() {
        return myCol;
    }

    public boolean isWalkable() {
        return isWalkable;
    }

    //Interactions methods
    public List<Interaction> getStepInteractions() {
        return Collections.unmodifiableList(myStepInteractions);
    }

    public boolean addStepInteraction(Interaction stepInteraction) {
        return myStepInteractions.add(stepInteraction);
    }

    protected boolean removeStepInteraction(Interaction stepInteraction) {
        return myStepInteractions.remove(stepInteraction);
    }

    public List<Interaction> getTalkInteractions() {
        return Collections.unmodifiableList(myTalkInteractions);
    }

    public boolean addTalkInteraction(Interaction talkInteraction) {
        return myTalkInteractions.add(talkInteraction);
    }

    protected boolean removeTalkInteraction(Interaction talkInteraction) {
        return myTalkInteractions.remove(talkInteraction);
    }

    public String getMessage() {
        return myMessage;
    }

    /*****SETTERS******/
    public void setMessage(String message){
        this.myMessage = message;
    }
    protected void setWalkableStatus(boolean status) {
        isWalkable = status;
    }
}
