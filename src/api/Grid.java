package api;

import java.io.Serializable;

/**
 * The model.grid interface, The rectangular model.grid in which all the model.block ui.scenes.controller.editor.objects may be placed. This back end
 * class serves to be the main hub of action by holding all the blocks in the model.grid.
 *
 * @author Aninda Manocha, Filip Mazurek
 */

public interface Grid extends Serializable {

    /**
     * Change the size of the model.grid based on what the user chooses in the controller.editor.
     *
     * @param numRows   the number of rows in the new model.grid
     * @param numCols   the number of columns in the new model.grid
     * @param rowStart  the row on which to start in the original model.grid
     * @param rowEnd    the row on which to end in the original model.grid
     * @param rowOffset the offset where to place the original model.grid on the new model.grid
     * @param colStart  the column on which to start in the original model.grid
     * @param colEnd    the column on which to end in the original model.grid
     * @param colOffset the offset where to place the original model.grid on the new model.grid
     */
    void resize(int numRows, int numCols, int rowStart, int rowEnd,
                int rowOffset, int colStart, int colEnd, int colOffset);

    /**
     * Gets the number of rows in the model.grid
     *
     * @return the number of rows
     */
    int getNumRows();

    /**
     * Gets the number of columns in the model.grid
     *
     * @return the number of columns
     */
    int getNumCols();

    /**
     * Gets the model.block located at a specific cell
     *
     * @param row - the row of the cell
     * @param col - the column of the cell
     * @return the model.block
     */
    Block getBlock(int row, int col);

    /**
     * Sets a model.block in the model.grid in a specific location
     *
     * @param row   - the row of the model.block
     * @param col   - the column of the model.block
     * @param block - the new model.block
     */
    void setBlock(int row, int col, Block block);

    /**
     * Get the index of the model.grid, used for multiple grids
     *
     * @return index
     */
    int getIndex();
}
