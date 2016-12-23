package view.grid;

import javafx.scene.Group;

/**
 * @author Teddy Franceschi, Harshil Garg
 */
public class EngineGrid extends Grid {

    private static final int CELL_PIXELS = 50;

    public EngineGrid(int gridWidth, int gridHeight, int renderWidth, int renderHeight) {
        super(gridWidth, gridHeight, renderWidth, renderHeight, CELL_PIXELS);
    }


    public void blockToGridPane(int row, int col, String name) {
        GridPaneNode temp = new GridPaneNode(row + WRAP / 2, col + WRAP / 2, name);
        blockList.add(temp);
    }

    public void populateBorder() {
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                if ((i < WRAP / 2 || j < WRAP / 2) || (i >= (gridWidth - WRAP / 2) || j >= (gridHeight - WRAP / 2))) {
                    GridPaneNode temp = new GridPaneNode(j, i, BORDER);
                    blockList.add(temp);
                }
            }
        }
    }

    public Group getGroup() {
        return group;
    }

    public double getBlockSize() {
        return CELL_PIXELS;
    }

    public double getWidth() {
        return gridWidth;
    }

    public double getHeight() {
        return gridHeight;
    }

    public boolean reRender(int row, int col, String newPath) {
        GridPaneNode newGPN = new GridPaneNode(row, col, newPath);
        GridPaneNode temp = grid[row + WRAP / 2][col + WRAP / 2];
        temp.swap(newGPN, 0);
        return true;
    }

}