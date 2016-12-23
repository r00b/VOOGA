package model.grid;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;

import api.Block;
import api.Grid;
import model.block.*;
import model.block.blocktypes.BlockType;
import model.block.blocktypes.CommunicatorBlock;
import model.block.blocktypes.GateBlock;
import model.exceptions.*;
import model.player.PlayerBlockUpdate;
import model.player.PlayerUpdate;

/**
 * This class manages all of the grids in the editor or engine
 *
 * @author Aninda Manocha, Filip Mazurek
 */

public class GridManager extends Observable implements Serializable {

    private static final String SIZE_CHOOSER = "resources/properties/size-chooser";
    private ResourceBundle myResources;
    private List<Grid> myGrids;
    private int currentIndex;
    private Grid currentGrid;
    private BlockFactory blockFactory;
    private String musicFile;

    public GridManager() {
        myResources = ResourceBundle.getBundle(SIZE_CHOOSER);
        myGrids = new ArrayList<>();
        currentIndex = 0;
        blockFactory = new BlockFactory();
    }

    public GridManager(List<Grid> grids) {
        myResources = ResourceBundle.getBundle(SIZE_CHOOSER);
        myGrids = grids;
        currentIndex = 0;
        blockFactory = new BlockFactory();
        currentGrid = myGrids.get(currentIndex);
    }

    public void addGrid(int numRows, int numCols) {
        Grid newGrid = new GridInstance(myGrids.size(), numRows, numCols);
        myGrids.add(newGrid);
        changeGrid(myGrids.size() - 1);
    }

    private void addGrid(Grid grid) {
        myGrids.add(grid);
    }

    public void changeGrid(int index) {
        currentIndex = index;
        currentGrid = myGrids.get(currentIndex);
    }

    public boolean changeGridSize(GridSizeDirection direction, int amount, int playerRow, int playerColumn)
            throws LargeGridException, DeletePlayerWarning {
        if (amount >= 0) {
            return growGrid(direction, amount);
        }
        return checkShrink(direction, amount, playerRow, playerColumn);
    }

    /**
     * Sets the name of the music file
     *
     * @param file - the file name
     */
    public void addMusic(String file) {
        musicFile = file;
    }

    /**
     * Determines if the model.grid can shrink without deleting the model.player. If not, the user receives a warning about deleting
     * the model.player.
     *
     * @param direction - the direction in which to shrink the model.grid
     * @param amount    - the amount by which the model.grid size should shrink
     * @return whether the model.grid can shrink without deleting the model.player
     * @throws DeletePlayerWarning
     */
    private boolean checkShrink(GridSizeDirection direction, int amount, int playerRow, int playerColumn)
            throws DeletePlayerWarning {
        switch (direction) {
            case TOP:
                if (playerRow < amount) {
                    throw new DeletePlayerWarning();
                }
            case BOTTOM:
                if (playerRow >= currentGrid.getNumRows() - amount) {
                    throw new DeletePlayerWarning();
                }
            case RIGHT:
                if (playerColumn >= currentGrid.getNumCols() - amount) {
                    throw new DeletePlayerWarning();
                }
            case LEFT:
                if (playerColumn < amount) {
                    throw new DeletePlayerWarning();
                }
        }
        return shrinkGrid(direction, amount);
    }

    /**
     * Decreases the size of the current model.grid in a specified direction and by a specified amount
     *
     * @param direction - the direction in which the size changes
     * @param amount    - the amount by which the model.grid size in the specified direction should shrink
     * @return whether or not the model.grid shrunk
     */
    public boolean shrinkGrid(GridSizeDirection direction, int amount) {
        int numRows, numCols, rowOffset, colOffset, rowStart, rowEnd, colStart, colEnd;
        numRows = rowEnd = currentGrid.getNumRows();
        numCols = colEnd = currentGrid.getNumCols();
        rowOffset = colOffset = rowStart = colStart = 0;
        PlayerBlockUpdate playerBlockUpdate = null;
        switch (direction) {
            case TOP:
                numRows -= amount;
                rowOffset = amount;
                playerBlockUpdate = new PlayerBlockUpdate(PlayerUpdate.ROW, rowOffset);
                setChanged();
                break;
            case BOTTOM:
                numRows -= amount;
                break;
            case RIGHT:
                numCols -= amount;
                break;
            case LEFT:
                numCols -= amount;
                colOffset = amount;
                playerBlockUpdate = new PlayerBlockUpdate(PlayerUpdate.COLUMN, colOffset);
                setChanged();
                break;
        }
        currentGrid.resize(numRows, numCols, rowStart, rowEnd, rowOffset, colStart, colEnd, colOffset);
        notifyObservers(playerBlockUpdate);
        return true;
    }

    /**
     * Increases the size of the current model.grid in a specified direction and by a specified amount
     *
     * @param direction - the direction in which the size changes
     * @param amount    - the amount by which the model.grid size in the specified direction should grow
     * @return whether or not the model.grid grew
     * @throws LargeGridException
     */
    private boolean growGrid(GridSizeDirection direction, int amount) throws LargeGridException {
        int numRows, numCols, rowOffset, colOffset, rowStart, rowEnd, colStart, colEnd;
        numRows = rowEnd = currentGrid.getNumRows();
        numCols = colEnd = currentGrid.getNumCols();
        rowOffset = colOffset = rowStart = colStart = 0;
        switch (direction) {
            case TOP:
                rowOffset = -amount;
                numRows += amount;
                break;
            case BOTTOM:
                rowEnd = numRows;
                numRows += amount;
                break;
            case RIGHT:
                colEnd = numCols;
                numCols += amount;
                break;
            case LEFT:
                colOffset = -amount;
                numCols += amount;
                break;
        }
        int maxDim = Integer.parseInt(myResources.getString("maxDim"));
        if (numRows > maxDim || numCols > maxDim) {
            throw new LargeGridException();
        }
        currentGrid.resize(numRows, numCols, rowStart, rowEnd, rowOffset, colStart, colEnd, colOffset);
        return true;
    }

    public void addBlock(String name, BlockType blockType, int row, int col) throws BlockCreationException {
        Block block = blockFactory.createBlock(name, blockType, row, col);
        currentGrid.setBlock(row, col, block);
    }

    public boolean addMessage(String message, int row, int col) {
        Block block = currentGrid.getBlock(row, col);
        if (block instanceof CommunicatorBlock) {
            ((CommunicatorBlock) block).setMessage(message);
            return true;
        }
        return false;
    }

    public boolean setGateStatus(int row, int col, boolean isOpen) {
        Block block = currentGrid.getBlock(row, col);

        if (block instanceof GateBlock) {
            if (isOpen) {
                ((GateBlock) block).openGate();
                return true;
            } else {
                ((GateBlock) block).closeGate();
                return true;
            }
        } else {
            return false;
        }
    }

    public boolean linkBlocks(int row1, int col1, int index1, int row2, int col2, int index2) {
        Grid grid1 = myGrids.get(index1);
        Grid grid2 = myGrids.get(index2);
        Block block1 = grid1.getBlock(row1, col1);
        Block block2 = grid2.getBlock(row2, col2);
        boolean firstLink = block1.link(block2, index2);
        boolean secondLink = block2.link(block1, index1);
        return (firstLink || secondLink);
    }

    public boolean unlinkBlocks(int row1, int col1, int index1, int row2, int col2, int index2) {
        Grid grid1 = myGrids.get(index1);
        Grid grid2 = myGrids.get(index2);
        Block block1 = grid1.getBlock(row1, col1);
        Block block2 = grid2.getBlock(row2, col2);
        return (block1.unlink(block2) || block2.unlink(block2));
    }

    /**
     * Makes a copy of the grid manager so that one can be used to test the game while in the editor, but the original
     * model.grid manager is still preserved
     *
     * @return the copy of the grid manager
     */
    public GridManager deepClone() {
        GridManager newGridManager = new GridManager();
        for (int i = 0; i < myGrids.size(); i++) {
            GridInstance grid = (GridInstance) myGrids.get(i);
            GridInstance tempGrid = new GridInstance(i, grid.getNumRows(), grid.getNumCols());
            for (int row = 0; row < grid.getNumRows(); row++) {
                for (int col = 0; col < grid.getNumCols(); col++) {
                    Block block = grid.getBlock(row, col);
                    Block tempBlock = block.deepClone();
                    tempGrid.setBlock(row, col, tempBlock);
                }
            }
            newGridManager.addGrid(tempGrid);
        }
        newGridManager.changeGrid(0);
        newGridManager.addMusic(musicFile);
        return newGridManager;
    }

    /* GETTERS */

    /**
     * Gets the list of grids
     *
     * @return the list of grids
     */
    public List<Grid> getGridList() {
        return myGrids;
    }

    /**
     * Gets the current model.grid
     *
     * @return the current model.grid
     */
    public Grid getCurrentGrid() {
        return currentGrid;
    }

    /**
     * Gets the index of the current model.grid
     *
     * @return the index
     */
    public int getCurrentIndex() {
        return currentIndex;
    }

    /**
     * Gets the name (image path) of the model.block located at a specified row and column
     *
     * @param row - the row
     * @param col - the column
     * @return the name of the model.block
     */
    public String getBlock(int row, int col) {
        return currentGrid.getBlock(row, col).getName();
    }

    /**
     * Gets the name of the music file used for the game
     *
     * @return the file name
     */
    public String getMusic() {
        return musicFile;
    }
}