package editor;

import block.Block;
import block.BlockFactory;
import block.BlockType;
import block.CommunicatorBlock;
import engine.EngineController;
import exceptions.*;
import grid.Grid;
import grid.GridGrowthDirection;
import grid.GridWorld;
import player.Player;
import player.PlayerAttribute;
import xml.GridWorldAndPlayer;
import xml.GridXMLHandler;

/**
 * @author Aninda Manocha, Filip Mazurek
 */

public class EditorModel {
    private BlockFactory blockFactory;
    private GridXMLHandler xmlHandler;
    private GridWorld gridWorld;
    private Grid currentGrid;
    private Player player;

    public EditorModel() {
        blockFactory = new BlockFactory();
        xmlHandler = new GridXMLHandler();
        gridWorld = new GridWorld();
    }

    public void addGrid(int numRows, int numCols) {
        currentGrid = gridWorld.addGrid(numRows, numCols);
    }

    public void changeGrid(int index) {
        currentGrid = gridWorld.changeGrid(index);
    }

    public boolean changeGridSize(GridGrowthDirection direction, int amount) throws LargeGridException, DeletePlayerWarning {
        if (amount >= 0) {
            return growGrid(direction, amount);
        }
        return checkShrink(direction, amount);
    }

    /**
     * Determines if the grid can shrink without deleting the player. If not, the user receives a warning about deleting
     * the player.
     * @param direction - the direction in which to shrink the grid
     * @param amount - the amount by which the grid size should shrink
     * @return whether the grid can shrink without deleting the player
     * @throws DeletePlayerWarning
     */
    private boolean checkShrink(GridGrowthDirection direction, int amount) throws DeletePlayerWarning {
        switch (direction) {
            case NORTH:
                if (player.getRow() < amount) {
                    throw new DeletePlayerWarning();
                }
            case SOUTH:
                if(player.getRow() >= currentGrid.getNumRows() - amount) {
                    throw new DeletePlayerWarning();
                }
            case EAST:
                if(player.getCol() >= currentGrid.getNumCols() - amount) {
                    throw new DeletePlayerWarning();
                }
            case WEST:
                if(player.getCol() < amount) {
                    throw new DeletePlayerWarning();
                }
        }
        return shrinkGrid(direction, amount);
    }

    public boolean shrinkGrid(GridGrowthDirection direction, int amount) {
        int numRows, numCols, rowOffset, colOffset, rowStart, rowEnd, colStart, colEnd;
        numRows = rowEnd = currentGrid.getNumRows();
        numCols = colEnd = currentGrid.getNumCols();
        rowOffset = colOffset = rowStart = colStart = 0;
        switch (direction) {
            case NORTH:
                numRows -= amount;
                rowOffset = amount;
                player.setRow(player.getRow() - rowOffset);
                break;
            case SOUTH:
                numRows -= amount;
                break;
            case EAST:
                numCols -= amount;
                break;
            case WEST:
                numCols -= amount;
                colOffset = amount;
                player.setCol(player.getCol() - colOffset);
                break;
        }
        currentGrid.resize(numRows, numCols, rowStart, rowEnd, rowOffset, colStart, colEnd, colOffset);
        return true;
    }

    public boolean growGrid(GridGrowthDirection direction, int amount) throws LargeGridException{
        int numRows, numCols, rowOffset, colOffset, rowStart, rowEnd, colStart, colEnd;
        numRows = rowEnd = currentGrid.getNumRows();
        numCols = colEnd = currentGrid.getNumCols();
        rowOffset = colOffset = rowStart = colStart = 0;

        switch (direction) {
            case NORTH:
                rowOffset = -amount;
                numRows += amount;
                break;
            case SOUTH:
                rowEnd = numRows;
                numRows += amount;
                break;
            case EAST:
                colEnd = numCols;
                numCols += amount;
                break;
            case WEST:
                colOffset = -amount;
                numCols += amount;
                break;
        }

        if (numRows > 100 || numCols > 100) {
            throw new LargeGridException();
        }
        currentGrid.resize(numRows, numCols, rowStart, rowEnd, rowOffset, colStart, colEnd, colOffset);
        return true;
    }

    public void addBlock(String name, BlockType blockType, int row, int col) {
        Block block = blockFactory.createBlock(name, blockType, row, col);
        currentGrid.setBlock(row, col, block);
    }

    public boolean addMessage(String message, int row, int col) {
        Block block = currentGrid.getBlock(row, col);
        if(block instanceof CommunicatorBlock) {
            ((CommunicatorBlock) block).setMessage(message);
            return true;
        }
        return false;
    }

    public boolean linkBlocks(int row1, int col1, int index1, int row2, int col2, int index2) {
        Grid grid1 = gridWorld.getGrid(index1);
        Grid grid2 = gridWorld.getGrid(index2);
        Block block1 = grid1.getBlock(row1, col1);
        Block block2 = grid2.getBlock(row2, col2);
        return (block1.link(block2, index2) || block2.link(block1, index1));
    }

    public boolean unlinkBlocks(int row1, int col1, int index1, int row2, int col2, int index2) {
        Grid grid1 = gridWorld.getGrid(index1);
        Grid grid2 = gridWorld.getGrid(index2);
        Block block1 = grid1.getBlock(row1, col1);
        Block block2 = grid2.getBlock(row2, col2);
        return (block1.unlink(block2) || block2.unlink(block2));
    }

    public String getBlock(int row, int col) {
        return currentGrid.getBlock(row, col).getName();
    }

    public boolean addPlayer(String name, String playerName, int row, int col) throws BadPlayerPlacementException, DuplicatePlayerException {
        if(!(currentGrid.getBlock(row, col).isWalkable())) {
            throw new BadPlayerPlacementException(row, col);
        }
        if(player == null) {
            player = new Player(name, playerName, row, col, currentGrid.getIndex());
            return true;
        }
        else {
            throw new DuplicatePlayerException(player.getRow(), player.getCol());
        }
    }

    public boolean addPlayerAttribute(String name, double amount, double increment, double decrement) {
        PlayerAttribute playerAttribute = new PlayerAttribute(name, amount, increment, decrement);
        player.addAttribute(playerAttribute);
        return false;
    }

    public boolean movePlayer(int row, int col) {
        player.setRow(row);
        player.setCol(col);
        return false;
    }

    /***** DATA METHODS *****/

    public void saveEditor(String file) {
        xmlHandler.saveContents(file, gridWorld, player);
    }

    public void loadEditor(String file) {
        GridWorldAndPlayer gridWorldAndPlayer = xmlHandler.loadContents(file);
        player = gridWorldAndPlayer.getPlayer();
        gridWorld = gridWorldAndPlayer.getGridWorld();
        changeGrid(gridWorld.getCurrentIndex());
    }

    public void saveEngine(String file) throws NoPlayerException {
        if (player == null) {
            throw new NoPlayerException();
        }
        xmlHandler.saveContents(file, gridWorld, player);
    }

    public EngineController runEngine() {
        return (new EngineController(player, gridWorld));
    }

    /***** GETTERS *****/

    public Player getPlayer() {
        return player;
    }
}
