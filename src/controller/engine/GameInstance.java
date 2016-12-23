package controller.engine;

import api.Block;
import api.Grid;
import api.IGameInstance;
import api.Player;
import controller.battle.BattleHandler;
import model.battle.Difficulty;
import model.block.*;
import model.block.blocktypes.EnemyBlock;
import model.block.blocktypes.PokemonGiverBlock;
import model.grid.GridManager;
import model.grid.GridWorld;
import javafx.scene.control.ChoiceDialog;
import model.player.PlayerDirection;
import model.player.PlayerManager;
import model.player.PlayerUpdate;
import model.xml.GridXMLHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Observable;

/**
 * This class holds all of the information pertaining to a game instance
 *
 * @author Aninda Manocha, Filip Mazurek
 */

public class GameInstance extends Observable implements IGameInstance {

    private GridXMLHandler xmlHandler;
    private GridManager myGridManager;
    private PlayerManager myPlayerManager;
    private Grid myGrid;
    private Player myPlayer;

    private int myScore;
    private GameStatus myStatus;
    private List<BlockUpdate> blockUpdates;

    public GameInstance(Player player, GridManager gridManager) {
        xmlHandler = new GridXMLHandler();
        myGridManager = gridManager;
        myGrid = myGridManager.getCurrentGrid();
        myPlayerManager = new PlayerManager(myGrid);
        myPlayerManager.setPlayer(player);
        myPlayer = player;
        myScore = 0;
        myStatus = new GameStatus();
        blockUpdates = new ArrayList<>();
    }

    public void changeGrid(int index) {
        myGridManager.changeGrid(index);
        myGrid = myGridManager.getCurrentGrid();
    }

    public void processInput(UserInstruction input) {
        int row = myPlayer.getRow();
        int col = myPlayer.getCol();
        PlayerUpdate playerUpdate = null;
        PlayerDirection direction = myPlayer.getDirection();

        switch (input) {
            case UP:
                if (direction == PlayerDirection.NORTH) {
                    playerUpdate = handleMovement(row - 1, col, PlayerUpdate.ROW);
                } else {
                    playerUpdate = handleDirection(PlayerDirection.NORTH);
                }
                break;
            case DOWN:
                if (direction == PlayerDirection.SOUTH) {
                    playerUpdate = handleMovement(row + 1, col, PlayerUpdate.ROW);
                } else {
                    playerUpdate = handleDirection(PlayerDirection.SOUTH);
                }
                break;
            case RIGHT:
                if (direction == PlayerDirection.EAST) {
                    playerUpdate = handleMovement(row, col + 1, PlayerUpdate.COLUMN);
                } else {
                    playerUpdate = handleDirection(PlayerDirection.EAST);
                }
                break;
            case LEFT:
                if (direction == PlayerDirection.WEST) {
                    playerUpdate = handleMovement(row, col - 1, PlayerUpdate.COLUMN);
                } else {
                    playerUpdate = handleDirection(PlayerDirection.WEST);
                }

                break;
            case TALK:
                Block block = blockInFacedDirection(row, col, direction);

                if (block instanceof EnemyBlock) {
                    Collection<Difficulty> choices = new ArrayList<Difficulty>();
                    choices.add(Difficulty.EASY);
                    choices.add(Difficulty.MEDIUM);
                    choices.add(Difficulty.HARD);

                    ChoiceDialog box = new ChoiceDialog(Difficulty.EASY, choices);
                    box.setHeaderText("Enter battle");
                    box.setContentText("Choose Difficulty");
                    box.showAndWait();

                    Difficulty diff = (Difficulty) box.getSelectedItem();
                    BattleHandler handler = new BattleHandler(myPlayer, (EnemyBlock) block);
                    handler.enterBattle(diff);
                } else if (block instanceof PokemonGiverBlock) {
                    PokemonGiverBlock pokeBlock = (PokemonGiverBlock) block;

                    if (pokeBlock.getHasPokemon()) {
                        pokeBlock.setHasPokemon(false);
                        blockUpdates = block.talkInteract(myPlayer);
                        playerUpdate = PlayerUpdate.TALK;
                        setChanged();
                    }
                } else {
                    blockUpdates = block.talkInteract(myPlayer);
                    playerUpdate = PlayerUpdate.TALK;
                    setChanged();
                }
            default:
                break;
        }

        notifyObservers(playerUpdate);
    }


    /**
     * Determines if a model.block is within the bounds of the model.grid
     *
     * @param block - the model.block
     * @return whether the model.block is in bounds
     */
    private boolean inBounds(Block block) {
        return (block != null);
    }

    /**
     * Determines if a model.block is walkable
     *
     * @param block - the model.block
     * @return whether the model.block is walkable
     */
    private boolean isWalkable(Block block) {
        return block.isWalkable();
    }

    /**
     * Handles the case where the model.player moves
     *
     * @param row          - the row of the model.player after it moves
     * @param col          - the column of the model.player after it moves
     * @param playerUpdate - the model.player update type depending on whether the row or column changes (ROW or COLUMN)
     * @return the model.player update type
     */
    private PlayerUpdate handleMovement(int row, int col, PlayerUpdate playerUpdate) {
        Block newBlock = myGrid.getBlock(row, col);
        if (inBounds(newBlock) && isWalkable(newBlock)) {
            myPlayer.setRow(newBlock.getRow());
            myPlayer.setCol(newBlock.getCol());
            blockUpdates = newBlock.stepInteract(myPlayer);
            setChanged();
        }
        return playerUpdate;
    }

    /**
     * Handles the case where the model.player changes direction
     *
     * @param direction - the new direction the model.player will face
     * @return the model.player update type (DIRECTION)
     */
    private PlayerUpdate handleDirection(PlayerDirection direction) {
        myPlayer.setDirection(direction);
        setChanged();
        return PlayerUpdate.DIRECTION;
    }

    /**
     * Gets the model.block that the model.player is facing
     *
     * @param row       - the row of the model.player
     * @param col       - the column of the model.player
     * @param direction - the direction of the model.player
     * @return the model.block the model.player is facing
     */
    private Block blockInFacedDirection(int row, int col, PlayerDirection direction) {
        switch (direction) {
            case NORTH:
                return myGrid.getBlock(row - 1, col);
            case SOUTH:
                return myGrid.getBlock(row + 1, col);
            case EAST:
                return myGrid.getBlock(row, col + 1);
            case WEST:
                return myGrid.getBlock(row, col - 1);
            default:
                return null;
        }
    }

    public List<BlockUpdate> getInteractions() {
        List<BlockUpdate> tempList = new ArrayList<>();
        tempList.addAll(blockUpdates);
        blockUpdates.clear();
        return tempList;
    }

    public void saveEngine(String file) {
        GridWorld gridWorld = new GridWorld(myGridManager, myGridManager.getMusic());
        xmlHandler.saveContents(file, gridWorld, myPlayer);
    }

    /***** GETTERS *****/

    public Grid getGrid() {
        return myGrid;
    }

    public Player getPlayer() {
        return myPlayer;
    }

    public List<String> getPlayerNames() {
        return myPlayerManager.getNames();
    }

    public String getPlayerName() {
        return myPlayerManager.getPlayerName();
    }

    public int getPlayerRow() {
        return myPlayerManager.getRow();
    }

    public int getPlayerColumn() {
        return myPlayerManager.getCol();
    }

    public PlayerDirection getPlayerDirection() {
        return myPlayerManager.getDirection();
    }

    public double getPlayerHealth() {
        return myPlayerManager.getHealth();
    }

    public int getPlayerNumPokemon() {
        return myPlayerManager.getNumPokemon();
    }

    public int getScore() {
        return myScore;
    }

    public GameStatus getGameStatus() {
        return myStatus;
    }

    public String getMusic() {
        return myGridManager.getMusic();
    }

    public void setMusic(String file) {
        myGridManager.addMusic(file);
    }
}
