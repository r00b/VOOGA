package model.grid;

import api.Block;
import api.Grid;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.ResourceBundle;

import model.block.blocktypes.DecorationBlock;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * Instance of a grid.
 *
 * @author Filip Mazurek, Aninda Manocha, Daniel Chai
 */

@XStreamAlias("grid")
public class GridInstance extends Observable implements Grid {
    @XStreamOmitField
    private ResourceBundle myBlockPaths = ResourceBundle.getBundle("resources/properties/block-paths");

    private int myIndex;
    private int myNumRows;
    private int myNumCols;
    private Map<Integer, String> extensionMapper;
    private static final int NUM_DEFAULTS = 4;
    private static final ResourceBundle myImagePaths = ResourceBundle.getBundle("resources/properties/image-paths");
    private static final String BASE = myImagePaths.getString("DEFAULT_BASE");
    private static final String EXTENSION_1 = myImagePaths.getString("DEFAULT_EXT_1");
    private static final String EXTENSION_2 = myImagePaths.getString("DEFAULT_EXT_2");
    private static final String EXTENSION_3 = myImagePaths.getString("DEFAULT_EXT_3");
    private static final String EXTENSION_4 = myImagePaths.getString("DEFAULT_EXT_4");

    @XStreamImplicit
    private Block[][] myGrid;

    public GridInstance(int index, int numRows, int numColumns) {
        myIndex = index;
        myNumRows = numRows;
        myNumCols = numColumns;
        myGrid = new Block[numRows][numColumns];
        extensionMapper = new HashMap<>();
        extensionMapper.put(1, EXTENSION_1);
        extensionMapper.put(2, EXTENSION_2);
        extensionMapper.put(3, EXTENSION_3);
        extensionMapper.put(4, EXTENSION_4);
        initializeGrid();
    }

    /**
     * Prepare a blank model.grid for all blocks to be placed. The default is a DecorationBlock grass tile.
     */
    private void initializeGrid() {
        for (int i = 0; i < myNumRows; i++) {
            for (int j = 0; j < myNumCols; j++) {
                myGrid[i][j] = new DecorationBlock(getRandomDefaultImagePath(), i, j);
            }
        }
    }


    public void resize(int numRows, int numCols, int rowStart, int rowEnd,
                       int rowOffset, int colStart, int colEnd, int colOffset) {
        Block[][] newGrid = new Block[numRows][numCols];
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                if (row < rowStart || row >= rowEnd || col < colStart || col >= colEnd) {
                    newGrid[row][col] = new DecorationBlock(getRandomDefaultImagePath(), row, col);
                } else {
                    newGrid[row][col] = myGrid[row + rowOffset][col + colOffset];
                }
            }
        }
        myGrid = newGrid;
    }

    public int getIndex() {
        return myIndex;
    }

    public int getNumRows() {
        return myNumRows;
    }

    public int getNumCols() {
        return myNumCols;
    }

    public Block getBlock(int row, int col) {
        if (row < 0 || row >= myNumRows || col < 0 || col >= myNumCols) {
            return null;
        } else {
            return myGrid[row][col];
        }
    }

    public void setBlock(int row, int col, Block block) {
        myGrid[row][col] = block;
        setChanged();
        notifyObservers(block);
    }

    /**
     * Generate a random image path to put random default blocks
     *
     * @return the path as a String
     */
    public String getRandomDefaultImagePath() {
        int randomNum = (int) Math.floor((Math.random() * NUM_DEFAULTS) + 1);
        return BASE + extensionMapper.get(randomNum);
    }
}