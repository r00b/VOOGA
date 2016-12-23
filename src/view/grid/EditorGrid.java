package view.grid;

import java.util.*;

import model.block.blocktypes.BlockType;
import utilities.builder.UIBuilder;
import utilities.builder.ComponentProperties;
import utilities.builder.DialogBuilder;
import view.scenes.editor.objects.GameObject;
import view.scenes.editor.sidemenu.GameSideMenu;
import view.scenes.editor.sidemenu.ItemSideMenu;
import view.scenes.editor.sidemenu.PlayerSideMenu;
import javafx.scene.Group;
import javafx.scene.effect.ColorAdjust;
import controller.editor.EditorController;


/**
 * @author Teddy Franceschi, Harshil Garg
 */
public class EditorGrid extends Grid implements Observer {

    private static final int CELL_PIXELS = 30;

    private ColorAdjust hoverOpacity;

    private GridPaneNode def;

    private String clickType;

    public EditorGrid(int gridWidth, int gridHeight, int renderWidth, int renderHeight) {
        super(gridWidth, gridHeight, renderWidth, renderHeight, CELL_PIXELS);

        hoverOpacity = new ColorAdjust();
        hoverOpacity.setBrightness(-.1);

        this.clickType = "";
        def = new GridPaneNode(0, 0, defaultText());

        for (GridPaneNode node : blockList) {
            if (!isWrapBlock(node.getRow(), node.getCol())) {
                makeClickable(node);
            }
        }
    }

    public void click(GridPaneNode node) {
        if (clicked.contains(node)) {
            node.getImage().setEffect(null);
            clicked.remove(node);
        } else {
            clicked.add(node);
        }
    }

    public void nodeClick(GameObject obj,
                          EditorController control,
                          List<String> imagePaths) {
        setChanged();
        notifyObservers();
        if (clicked.size() == 1) {
            if (clickType.equals("SWAP")) {
                swap(obj, control);
            } else if (clickType.equals("PLAYER") && imagePaths.size() > 0) {
                String name = setDialogue("Name for Player", "Set the name for your player");
                buildPlayer(control, name, imagePaths);
            }
        } else if (clicked.size() == 2 && clickType.equals("LINK")) {
            if (buildLink(clicked.get(0), clicked.get(1), control)) {
                successMessage("Link forged!", "Successfully built a link between selected objects!");
            }
        }
        for (int i = 0; i < clicked.size(); i++) {
            clicked.get(i).getImage().setEffect(null);
        }
    }

    /**
     * Builds player
     *
     * @param control
     * @param name
     * @param imagePaths
     */
    public void buildPlayer(EditorController control, String name, List<String> imagePaths) {
        GridPaneNode node = clicked.get(0);
        int col = node.getCol();
        int row = node.getRow();
        int bCol = getBackendAssociatedColumn(node);
        int bRow = getBackendAssociatedRow(node);

        if (control.addPlayer(imagePaths, name, bRow, bCol)) {

            GridPaneNode temp = grid[col][row];

            // Allows adding on top of grid. Players should hover over the grid.
            GridPaneNode player = new GridPaneNode(row, col, imagePaths.get(0));
            player.setImageSize(CELL_PIXELS, CELL_PIXELS);
            player.setImageCoord(getXRender(col), getYRender(row));
            group.getChildren().add(player.getImage());

            ArrayList<GridPaneNode> list = new ArrayList<GridPaneNode>();
            list.add(temp);
            gridMap.storeObject(list);
            resetClicked();
        }

    }

    public List<GridPaneNode> swap(GameObject obj, EditorController control) {
        List<GridPaneNode> copy = new ArrayList<GridPaneNode>();
        if (obj == null) {
            return copy;
        }
        List<GridPaneNode> list = obj.getImageTiles();
        getObjectNeighbors(list);
        for (int i = 0; i < clicked.size(); i++) {
            if (addObjToMap(list, clicked.get(i))) {
                for (int j = 0; j < list.size(); j++) {
                    int xPos = clicked.get(i).getCol() + list.get(j).getCol();
                    int yPos = clicked.get(i).getRow() + list.get(j).getRow();
                    GridPaneNode temp = grid[yPos][xPos];
                    // TODO add dimension checker

                    if (obj.getBlockType().equals(BlockType.COMMUNICATOR)) {
                        String message = setDialogue("Set the dialogue for the communicator block.", "Dialog for the communicator block:");
                        if (!message.isEmpty()) {
                            temp.swap(list.get(j), list.get(j).getImageNum());

                            control.addBlock(temp.getName(), obj.getBlockType(), getBackendAssociatedRow(temp),
                                    getBackendAssociatedColumn(temp));
                            control.addMessage(message, getBackendAssociatedRow(temp), getBackendAssociatedColumn(temp));
                        }
                    } else if (obj.getBlockType().equals(BlockType.GATE)) {

                        temp.swap(list.get(j), list.get(j).getImageNum());
                        control.addBlock(temp.getName(), obj.getBlockType(), getBackendAssociatedRow(temp),
                                getBackendAssociatedColumn(temp));
                        gateTransition(temp, control);


                    } else if (obj.getBlockType().equals(BlockType.NPC)) {
                        String message = setDialogue("Set the dialogue for the NPC block", "Dialogue for the NPC block");
                        if (!message.isEmpty()) {
                            temp.swap(list.get(j), list.get(j).getImageNum());
                            control.addBlock(temp.getName(), obj.getBlockType(), getBackendAssociatedRow(temp),
                                    getBackendAssociatedColumn(temp));
                            control.addMessage(message, getBackendAssociatedRow(temp), getBackendAssociatedColumn(temp));
                        }

                    } else {
                        temp.swap(list.get(j), list.get(j).getImageNum());
                        control.addBlock(temp.getName(), obj.getBlockType(), getBackendAssociatedRow(temp),
                                getBackendAssociatedColumn(temp));
                    }
                }
            }
            clicked.get(i).getImage().setEffect(null);
            copy = clicked;
        }
        clicked = new ArrayList<GridPaneNode>();
        return copy;
    }

    private void gateTransition(GridPaneNode node, EditorController control) {
        String path = node.getName();
        if (path.indexOf("OPEN") < 0) {
            control.setGateStatus(getBackendAssociatedRow(node), getBackendAssociatedColumn(node), false);
        } else {
            control.setGateStatus(getBackendAssociatedRow(node), getBackendAssociatedColumn(node), true);
        }
    }

    private void successMessage(String header, String content) {
        new UIBuilder().addNewAlert(new ComponentProperties().header(header).content(content));
    }

    private String setDialogue(String header, String content) {
        DialogBuilder db = new DialogBuilder(new ComponentProperties()
                .header(header)
                .content(content));
        Optional<String> response = db.getResponse();
        return response.orElse(new String());
    }


    private void resetClicked() {
        clicked = new ArrayList<GridPaneNode>();
    }

    private boolean addObjToMap(List<GridPaneNode> list, GridPaneNode objRoot) {
        int xPos = objRoot.getCol();
        int yPos = objRoot.getRow();
        List<GridPaneNode> temp = new ArrayList<GridPaneNode>();
        for (int i = 0; i < list.size(); i++) {
            int xRef = xPos + list.get(i).getCol();
            int yRef = yPos + list.get(i).getRow();
            if (!gridMap.available(yRef, xRef)) {
                return false;
            } else if (yRef >= gridHeight + WRAP / 2 || yRef < 0 || xRef >= gridWidth + WRAP / 2 || xRef < 0) {
                return false;
            }
            temp.add(grid[yRef][xRef]);
        }
        gridMap.storeObject(temp);
        return true;
    }

    /**
     * Gets neighbors if object is placed
     *
     * @param list
     */
    private void getObjectNeighbors(List<GridPaneNode> list) {
        ArrayList<Integer> xPos = new ArrayList<Integer>();
        ArrayList<Integer> yPos = new ArrayList<Integer>();
        for (int i = 0; i < clicked.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                xPos.add(clicked.get(i).getCol() + list.get(j).getCol());
                yPos.add(clicked.get(i).getRow() + list.get(j).getRow());
            }
            checkNeighbors(xPos, yPos, list.size());
        }
    }

    public void delete(EditorController control) {
        ArrayList<Integer> deleted = new ArrayList<Integer>();
        for (int i = 0; i < clicked.size(); i++) {
            GridPaneNode temp = clicked.get(i);
            deleted.addAll(gridMap.sharesObjWith(temp.getRow(), temp.getCol()));
            gridMap.collisionRemoval(temp.getRow(), temp.getCol());
        }
        if (!deleted.isEmpty()) {
            for (int i = 0; i < deleted.size(); i += 2) {

                GridPaneNode node = grid[deleted.get(i)][deleted.get(i + 1)];
                control.addBlock(defaultText(), BlockType.GROUND, getBackendAssociatedRow(node), getBackendAssociatedColumn(node));
                node.swap(def, node.getImageNum());

            }
        }
        resetClicked();
    }

    private boolean buildLink(GridPaneNode node1, GridPaneNode node2, EditorController controller) {
        clicked.clear();
        return controller.linkBlocks(getBackendAssociatedRow(node1), getBackendAssociatedColumn(node1), 0,
                getBackendAssociatedRow(node2), getBackendAssociatedColumn(node2), 0);
    }

    /**
     * Removes neighbors in clicked if object would contain both
     *
     * @param xCoords
     * @param yCoords
     * @param objSize
     */
    private void checkNeighbors(List<Integer> xCoords, List<Integer> yCoords, int objSize) {
        for (int i = 0; i < clicked.size(); i++) {
            GridPaneNode temp = clicked.get(i);
            for (int j = 0; j < xCoords.size(); j++) {
                if (temp.getCol() == xCoords.get(j) && temp.getRow() == yCoords.get(j) &&
                        j % objSize != 0) {
                    clicked.remove(i);
                }
            }
        }
    }

    public void shiftAll() {
        for (int i = 0; i < blockList.size(); i++) {
            GridPaneNode temp = blockList.get(i);
            //temp.setImageCoord(getXRender(temp.getCol()) + 355, getYRender(temp.getRow()) + 655);
            temp.setImageCoord(temp.getImage().getLayoutX() + WRAP, temp.getImage().getLayoutY() + WRAP);
        }
    }

    public void blockToGridPane(int row, int col, String name) {
        GridPaneNode temp = new GridPaneNode(row, col, name);
        makeClickable(temp);
        blockList.add(temp);
    }

    public List<GridPaneNode> getNodeList() {
        return blockList;
    }


    public Group getGroup() {
        return group;
    }

    public double getWidth() {
        return gridWidth;
    }

    public double getHeight() {
        return gridHeight;
    }

    public void makeClickable(GridPaneNode node) {
        node.getImage().setOnMouseExited(e -> {
            if (!clicked.contains(node))
                node.getImage().setEffect(null);
        });
        node.getImage().setOnMouseEntered(e -> {
            node.getImage().setEffect(hoverOpacity);
        });
        node.getImage().setOnMouseClicked(e -> {
            click(node);
        });
    }

    public double getXMin() {
        return -0.5 * CELL_PIXELS * (gridWidth + WRAP - renderWidth / CELL_PIXELS);
    }

    public double getYMin() {
        return -0.5 * CELL_PIXELS * (gridHeight + WRAP - renderHeight / CELL_PIXELS);
    }


    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof PlayerSideMenu) {
            clickType = "PLAYER";
            resetClicked();
        } else if (o instanceof GameSideMenu) {
            clickType = "LINK";

        } else if (o instanceof ItemSideMenu) {
            clickType = "SWAP";
            resetClicked();
        }
    }

    private int getBackendAssociatedRow(GridPaneNode node) {
        return node.getRow() - WRAP / 2;
    }

    private int getBackendAssociatedColumn(GridPaneNode node) {
        return node.getCol() - WRAP / 2;
    }

    public int getWrap() {
        return WRAP;
    }
}