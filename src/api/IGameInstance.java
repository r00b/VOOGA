package api;

import model.block.BlockUpdate;
import controller.engine.UserInstruction;
import controller.engine.GameStatus;

import java.util.List;

/**
 * The game instance interface
 *
 * @author Aninda Manocha
 */

public interface IGameInstance {

    /**
     * Changes to a different specified model.grid, identified by its index in the list of grids
     *
     * @param index - the index of the model.grid
     */
    void changeGrid(int index);

    /**
     * Processes the user input and moves the model.player based on the input
     *
     * @param input - the user input
     */
    void processInput(UserInstruction input);

    /**
     * Gets the model.interactions for the frontend to display
     *
     * @return the list of model.interactions
     */
    List<BlockUpdate> getInteractions();

    /**
     * Gets the current model.grid that the model.player is located in
     *
     * @return the current model.grid
     */
    Grid getGrid();

    /**
     * Gets the model.player character
     *
     * @return the model.player character
     */
    Player getPlayer();

    /**
     * Gets the score of the game
     *
     * @return the game
     */
    int getScore();

    /**
     * Gets the status of the game
     *
     * @return the status
     */
    GameStatus getGameStatus();
}
