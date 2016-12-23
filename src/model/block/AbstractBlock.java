package model.block;

import api.Block;
import api.Player;
import model.interactions.Interaction;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Parent class of all blocks.
 *
 * @author Filip Mazurek, Aninda Manocha
 */
public abstract class AbstractBlock implements Block, Serializable {

    private String myName;
    private int myRow;
    private int myCol;
    private boolean isWalkable;
    private List<Interaction> myStepInteractions;
    private List<Interaction> myTalkInteractions;

    public AbstractBlock(String name, int row, int col) {
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

    public List<BlockUpdate> talkInteract(Player player) {
        List<BlockUpdate> blockUpdates = new ArrayList<>();
        if (myTalkInteractions.size() > 0) {
            for (Interaction interaction : myTalkInteractions) {
                blockUpdates.addAll(interaction.act(player));
            }
        }
        return blockUpdates;

    }

    public boolean link(Block block, int gridIndex) {
        return false;
    }

    public boolean unlink(Block block) {
        return false;
    }

    public String replaceNameStatus(String name, String status) {
        int extensionLoc = name.lastIndexOf('.');
        String extension = name.substring(extensionLoc);
        int statusLoc = name.lastIndexOf('-');
        return name.substring(0, statusLoc + 1) + status + extension;
    }

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

    public boolean removeStepInteraction(Interaction stepInteraction) {
        return myStepInteractions.remove(stepInteraction);
    }

    public List<Interaction> getTalkInteractions() {
        return Collections.unmodifiableList(myTalkInteractions);
    }

    public boolean addTalkInteraction(Interaction talkInteraction) {
        return myTalkInteractions.add(talkInteraction);
    }

    public boolean removeTalkInteraction(Interaction talkInteraction) {
        return myTalkInteractions.remove(talkInteraction);
    }

    public void setWalkableStatus(boolean status) {
        isWalkable = status;
    }

    public Block deepClone() {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(this);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return (Block) objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
