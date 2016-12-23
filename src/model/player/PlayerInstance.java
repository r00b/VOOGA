package model.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import api.Player;
import model.battle.Battle;
import model.battle.Item;
import model.interactions.Interaction;

/**
 * This is the class that holds all of the information corresponding to the model.player on the backend.
 *
 * @author Aninda Manocha
 */

@XStreamAlias("player")
public class PlayerInstance implements Player {

    @XStreamOmitField
    public static final int DEFAULT_NUM_POKEMON = 3;

    private List<String> myNames;
    private String myPlayerName;
    private int myRow;
    private int myCol;
    private int myGridIndex;
    private PlayerDirection myDirection;
    private List<PlayerAttribute> myAttributes;
    private List<Item> myInventory;
    private List<Battle> myBattleHistory;
    private List<Interaction> myInteractionHistory;
    private int battlesWon;
    private int battlesLost;
    private double health;
    private int numPokemon;

    public PlayerInstance(List<String> names, String playerName, int row, int col, int gridIndex) {
        myNames = names;
        myPlayerName = playerName;
        myRow = row;
        myCol = col;
        myGridIndex = gridIndex;
        myDirection = PlayerDirection.SOUTH;
        myAttributes = new ArrayList<>();
        myInventory = new ArrayList<>();
        myBattleHistory = new ArrayList<>();
        myInteractionHistory = new ArrayList<>();
        battlesWon = battlesLost = 0;
        health = DEFAULT_HEALTH;
        numPokemon = DEFAULT_NUM_POKEMON;
    }

    public PlayerInstance(Player player) {
        myNames = player.getNames();
        myPlayerName = player.getPlayerName();
        myRow = player.getRow();
        myCol = player.getCol();
        myGridIndex = player.getGridIndex();
        myDirection = PlayerDirection.SOUTH;
        myAttributes = new ArrayList<>();
        myInventory = new ArrayList<>();
        myBattleHistory = new ArrayList<>();
        myInteractionHistory = new ArrayList<>();
        battlesWon = battlesLost = 0;
        health = DEFAULT_HEALTH;
        numPokemon = DEFAULT_NUM_POKEMON;
    }

	/* GETTERS */

    public List<String> getNames() {
        return myNames;
    }

    public String getPlayerName() {
        return myPlayerName;
    }

    public int getRow() {
        return myRow;
    }

    public int getCol() {
        return myCol;
    }

    public int getGridIndex() {
        return myGridIndex;
    }

    public PlayerDirection getDirection() {
        return myDirection;
    }

    public List<PlayerAttribute> getAttributes() {
        return Collections.unmodifiableList(myAttributes);
    }

    public List<Item> getInventory() {
        return myInventory;
    }

    public List<Battle> getBattleHistory() {
        return myBattleHistory;
    }

    public List<Interaction> getInteractionHistory() {
        return myInteractionHistory;
    }

    public int getBattlesWon() {
        return battlesWon;
    }

    public int getBattlesLost() {
        return battlesLost;
    }

    public double getHealth() {
        return health;
    }

    public int getNumPokemon() {
        return numPokemon;
    }

	/* SETTERS */

    public void setRow(int row) {
        myRow = row;
    }

    public void setCol(int col) {
        myCol = col;
    }

    public void setGridIndex(int gridIndex) {
        myGridIndex = gridIndex;
    }

    public void setDirection(PlayerDirection direction) {
        myDirection = direction;
    }

    public boolean addAttribute(PlayerAttribute attribute) {
        for (PlayerAttribute playerAttribute : myAttributes) {
            if (attribute.getName().equals(playerAttribute.getName())) {
                return false;
            }
        }
        myAttributes.add(attribute);
        return true;
    }

    public void addItem(Item item) {
        myInventory.add(item);
    }

    public void addBattle(Battle battle) {
        myBattleHistory.add(battle);
    }

    public void addInteraction(Interaction interaction) {
        myInteractionHistory.add(interaction);
    }

    public void incrementBattlesWon() {
        battlesWon++;
    }

    public void incrementBattlesLost() {
        battlesLost++;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public void incrementNumPokemon() {
        numPokemon++;
    }

    public void decrementNumPokemon() {
        numPokemon--;
    }
}