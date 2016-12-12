package ui;

import javafx.scene.Group;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;

/**
 * @author Harshil Garg
 */
public abstract class Grid extends Observable {

    protected static int CELL_PIXELS;

    protected static int WRAP = 20;

    protected static String DEFAULT = "resources/images/tiles/ground/grass-";
    protected static String BORDER = "resources/images/tiles/obstacle/tree-4.png";

    protected int gridWidth;
    protected int gridHeight;
    protected int renderWidth;
    protected int renderHeight;

    protected Group group;

    protected GridObjectMap gridMap;

    protected List<GridPaneNode> blockList;
    protected List<GridPaneNode> clicked;

    protected GridPaneNode[][] grid;

    public Grid(int gridWidth, int gridHeight, int renderWidth, int renderHeight, int CELL_PIXELS) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.renderWidth = renderWidth;
        this.renderHeight = renderHeight;
        this.CELL_PIXELS = CELL_PIXELS;

        group = new Group();
        blockList = new ArrayList<GridPaneNode>();
        clicked = new ArrayList<GridPaneNode>();

        initializeGrid();
        setRenderMap();
    }

    protected void initializeGrid() {

        int rows = gridHeight + WRAP;
        int columns = gridWidth + WRAP;

        gridMap = new GridObjectMap(columns, rows);
        grid = new GridPaneNode[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                GridPaneNode node;
                if (!isWrapBlock(i, j)) {
                    node = new GridPaneNode(i, j, defaultText());
                } else {
                    node = new GridPaneNode(i, j, BORDER);

                }
                blockList.add(node);
                grid[i][j] = node;
            }
        }
    }

    public void setRenderMap() {
        group = new Group();
        for (int i = 0; i < blockList.size(); i++) {
            GridPaneNode node = blockList.get(i);
            double x = getXRender(node.getCol());
            double y = getYRender(node.getRow());
            node.setImageSize(CELL_PIXELS, CELL_PIXELS);
            node.setImageCoord(x, y);
            group.getChildren().add(node.getImage());
            grid[node.getRow()][node.getCol()] = node;
        }
    }

    public void loadReset(int height, int width) {
        this.gridWidth = width + WRAP;
        this.gridHeight = height + WRAP;
        this.group = new Group();
        this.blockList = new ArrayList<GridPaneNode>();
        this.clicked = new ArrayList<GridPaneNode>();
        grid = new GridPaneNode[gridHeight][gridWidth];
    }

    private boolean isWrapBlock(int i, int j) {
        return !(i >= WRAP / 2 && i < gridHeight + WRAP / 2 &&
                j >= WRAP / 2 && j < gridWidth + WRAP / 2);
    }

    protected String defaultText () {
        int suffix = randomNumber(1, 4);
        return DEFAULT + suffix + ".png";
    }

    private int randomNumber (int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    protected double getXRender (int column) {
        double offset = -0.5 * CELL_PIXELS * (gridWidth + WRAP  - renderWidth/CELL_PIXELS);
        return column * CELL_PIXELS + offset;
    }

    protected double getYRender (int row) {
        double offset = -0.5 * CELL_PIXELS * (gridHeight + WRAP  - renderHeight/CELL_PIXELS);
        return row * CELL_PIXELS + offset;
    }

}