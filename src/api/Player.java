package api;

import java.util.List;

import model.battle.Battle;
import model.battle.Item;
import model.interactions.Interaction;
import model.player.PlayerAttribute;
import model.player.PlayerDirection;

/**
 * The model.player interface
 *
 * @author Aninda Manocha
 */

public interface Player {

    int DEFAULT_HEALTH = 100;

    /* GETTERS */

    /**
     * Gets the list of image path names that correspond to the model.player
     *
     * @return the list of image path names
     */
    List<String> getNames();

    /**
     * Gets the name of the model.player that the user entered
     *
     * @return the name
     */
    String getPlayerName();

    /**
     * Gets the row that the model.player is located in
     *
     * @return the row
     */
    int getRow();

    /**
     * Gets the column that the model.player is located in
     *
     * @return the column
     */
    int getCol();

    /**
     * Gets the index of the model.grid that the model.player is located on
     *
     * @return the model.grid index
     */
    int getGridIndex();

    /**
     * Gets the direction of the model.player
     *
     * @return the direction
     */
    PlayerDirection getDirection();

    /**
     * Gets a list of all of the attributes of the model.player
     *
     * @return the list of attributes
     */
    List<PlayerAttribute> getAttributes();

    /**
     * Gets the list of all items that the model.player possesses
     *
     * @return the list of items
     */
    List<Item> getInventory();

    /**
     * Gets the list of all the battles that the model.player has had
     *
     * @return the list of battles
     */
    List<Battle> getBattleHistory();

    /**
     * Gets the list of all the model.interactions that the model.player has had
     *
     * @return the list of model.interactions
     */
    List<Interaction> getInteractionHistory();

    /**
     * Gets the number of battles the model.player has won
     *
     * @return the number of battles won
     */
    int getBattlesWon();

    /**
     * Gets the number of battles the model.player has lost
     *
     * @return the number of battles lost
     */
    int getBattlesLost();

    /**
     * Gets the health of the model.player
     *
     * @return the health
     */
    double getHealth();

    /**
     * Gets the number of pokemon the model.player has
     *
     * @return the number of pokemon
     */
    int getNumPokemon();

    /* SETTERS */

    /**
     * Sets the row of the model.player
     *
     * @param row - the row of the model.grid that the model.player should be moved to
     */
    void setRow(int row);

    /**
     * Sets the column of the model.player
     *
     * @param col - the column of the model.grid that the model.player should be moved to
     */
    void setCol(int col);

    /**
     * Sets the index of the model.grid that the model.player is located on
     *
     * @param gridIndex - the index of the model.grid
     */
    void setGridIndex(int gridIndex);

    /**
     * Sets the direction (orientation) of the model.player
     *
     * @param direction - the direction of the model.player
     */
    void setDirection(PlayerDirection direction);

    /**
     * Adds an attribute to the list of attributes. This method returns false if the attribute type already exists.
     *
     * @param attribute the attribute to add
     * @return whether an attribute was successfully added
     */
    boolean addAttribute(PlayerAttribute attribute);

    /**
     * Adds an item to the inventory
     *
     * @param item - the item to add
     */
    void addItem(Item item);

    /**
     * Adds a battle to the battle history
     *
     * @param battle - the battle to add
     */
    void addBattle(Battle battle);

    /**
     * Adds an interaction to the interaction history
     *
     * @param interaction - the interaction to add
     */
    void addInteraction(Interaction interaction);

    /**
     * Increments the number of battles the model.player won
     */
    void incrementBattlesWon();

    /**
     * Increments the number of battles the model.player lost
     */
    void incrementBattlesLost();

    /**
     * Sets the health of the model.player
     *
     * @param health - the health of the model.player
     */
    void setHealth(double health);

    /**
     * Increments the number of pokemon that the model.player has
     */
    void incrementNumPokemon();

    /**
     * Decrements the number of pokemon that the model.player has
     */
    void decrementNumPokemon();
}