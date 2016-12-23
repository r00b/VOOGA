package controller.engine;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import api.Block;
import api.Controller;
import api.Player;
import com.sun.xml.internal.ws.api.pipe.Engine;
import model.block.AbstractBlock;
import model.block.BlockUpdate;
import model.grid.GridManager;
import model.player.PlayerDirection;
import model.xml.GridWorldAndPlayer;
import model.xml.GridXMLHandler;

/**
 * This is the controller for the game controller.engine. It allows the backend and frontend to talk to each other while the game
 * is being played.
 *
 * @author Aninda Manocha, Nisakorn Valyasevi
 */

public class EngineController extends Observable implements Observer, Controller {
    private GridXMLHandler xmlHandler;
    private List<GameInstance> gameInstances;
    private GameInstance gameInstance;
    private int gameInstanceIndex;

    public EngineController() {
        xmlHandler = new GridXMLHandler();
        gameInstances = new ArrayList<GameInstance>();
    }

    public EngineController(Player player, GridManager gridManager, String musicFile) {
        xmlHandler = new GridXMLHandler();
        gameInstances = new ArrayList<GameInstance>();
        gameInstance = new GameInstance(player, gridManager);
        gameInstance.addObserver(this);
        gameInstance.setMusic(musicFile);
    }

    /* GRID METHODS */

    /**
     * Changes to a different specified model.grid, identified by its index in the list of grids
     *
     * @param index - the index of the model.grid
     */
    public void changeGrid(int index) {
        gameInstance.changeGrid(index);
    }

    /**
     * Gets the model.block located in a specific row and column in the model.grid. The frontend calls this method in order to
     * render a model.grid model.block by model.block.
     *
     * @param row - the specific row
     * @param col - the specific column
     * @return the model.block
     */
    public String getBlock(int row, int col) {
        return gameInstance.getGrid().getBlock(row, col).getName();
    }

    /**
     * Gets the model.interactions for the frontend to display
     *
     * @return the list of model.interactions
     */
    public List<BlockUpdate> getInteractions() {
        return gameInstance.getInteractions();
    }

    /**
     * Gets the number of rows in the current model.grid in order for the frontend to draw the model.grid
     *
     * @return the number of rows
     */
    public int getNumRows() {
        return gameInstance.getGrid().getNumRows();
    }

    /**
     * Gets the number of columns in the current model.grid in order for the frontend to draw the model.grid
     *
     * @return the number of columns
     */
    public int getNumCols() {
        return gameInstance.getGrid().getNumCols();
    }

    /**
     * Gets the name of the music file for the game
     *
     * @return the file name
     */
    public String getMusic() {
        return gameInstance.getMusic();
    }

    /* PLAYER METHODS */

    /**
     * Takes in a user input and calls the game instance class to process it. The frontend calls this method once a key
     * input is sent so that the backend can affect the model.player.
     *
     * @param input - the user input
     */
    public void keyListener(UserInstruction input) {
        gameInstance.processInput(input);
    }

    public List<String> getPlayerImages() {
        return gameInstance.getPlayerNames();
    }

    public String getPlayerName() {
        return gameInstance.getPlayerName();
    }

    public int getPlayerRow() {
        return gameInstance.getPlayerRow();
    }

    public int getPlayerColumn() {
        return gameInstance.getPlayerColumn();
    }

    public PlayerDirection getPlayerDirection() {
        return gameInstance.getPlayerDirection();
    }

    public double getPlayerHealth() {
        return gameInstance.getPlayerHealth();
    }

    public int getPlayerNumPokemon() {
        return gameInstance.getPlayer().getNumPokemon();
    }

    /**
     * Passes a model.player update type to the frontend to update the display of the model.player when a model.player's information has
     * been changed (the game instance is observed in order to detect a change in the model.player's information)
     *
     * @param observableValue - the observable game instance
     * @param value           - the model.player update type
     */
    public void update(Observable observableValue, Object value) {
        if (observableValue instanceof GameInstance) {
            setChanged();
            notifyObservers(value);
        }
    }

    /* DATA METHODS */

    /**
     * Saves the status of a game by saving the model.grid world and model.player in a file
     *
     * @param file - the myIconPath of the file that will contain the game
     */
    public void saveEngine(String file) {
        gameInstance.saveEngine(file);
    }

    /**
     * Loads a game file containing a model.grid world and model.player
     *
     * @param file - the myIconPath of the file that contains the game
     */
    public void loadEngine(String file) {
        GridWorldAndPlayer gridWorldAndPlayer = xmlHandler.loadContents(file);
        Player player = gridWorldAndPlayer.getPlayer();
        GridManager gridManager = new GridManager(gridWorldAndPlayer.getGridWorld().getGrids());
        gridManager.addMusic(gridWorldAndPlayer.getGridWorld().getMusicFile());
        gameInstance = new GameInstance(player, gridManager);
        gameInstance.addObserver(this);
    }
}
