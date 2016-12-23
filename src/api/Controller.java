package api;

/**
 * The controller interface
 *
 * @author Aninda Manocha
 */

public interface Controller {

    /**
     * Changes to a different specified model.grid, identified by its index in the list of grids
     *
     * @param index - the index of the model.grid
     */
    void changeGrid(int index);

    /**
     * Gets the model.block located in a specific row and column in the model.grid. The frontend calls this method in order to
     * render a model.grid model.block by model.block.
     *
     * @param row - the specific row
     * @param col - the specific column
     * @return the model.block
     */
    String getBlock(int row, int col);
}
