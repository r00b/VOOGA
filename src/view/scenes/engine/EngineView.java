package view.scenes.engine;

import controller.engine.EngineController;
import view.FileBrowser;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.UILauncher;
import utilities.builder.UIBuilder;
import view.grid.EngineGrid;

import java.io.File;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

/**
 * @author Robert Steilberg
 *         <p>
 *         This class handles the view for game engine that is used to run games.
 */
public class EngineView extends Scene implements Observer {

    private static final String ENGINE_RESOURCES = "resources/properties/game-engine";
    private static final String CSS_FILE_NAME = "resources/styles/game-engine.css";

    private Stage myStage;
    private Parent myRoot;
    private UILauncher myLauncher;
    private UIBuilder myBuilder;
    private ResourceBundle myResources;

    private EngineGrid grid;
    private EngineController myController;
    private EngineAnimation myAnimation;
    private EngineCharacter myPlayer;
    private EngineSidePanel myEngineSidePanel;

    private File myGameFile;

    public EngineView(Stage stage, Parent root, UILauncher launcher) {
        this(stage, root);
        myLauncher = launcher;

        myStage.setOnCloseRequest(e -> {
            // Closing this
            myController = null;
            e.consume();
            if (myEngineSidePanel != null) {
                myEngineSidePanel.stopMusic();
            }
            myLauncher.launchMenu();
        });

    }

    public EngineView(Stage stage, Parent root) {
        super(root);
        myStage = stage;
        myRoot = root;
        myBuilder = new UIBuilder();
        myResources = ResourceBundle.getBundle(ENGINE_RESOURCES);
        myController = new EngineController();
        //engineSidePanel = new EngineSidePanel(myRoot,myBuilder,myResources,this, myController);

        myRoot.getStylesheets().add(CSS_FILE_NAME);
    }


    public void setController(EngineController controller) {
        myController = controller;
    }

    /**
     * Initializes the game controller.engine window and prompts the user to choose a game
     * file to open for playing
     *
     * @return true if initialization was successful and a valid game file was chosen
     */
    public boolean init(boolean replay) {
        if (!replay) {
            myGameFile = new FileBrowser().openGameFile(myStage, myResources.getString("engineFilePath"));
            if (myGameFile == null) {
                return false;
            }
        }
        myController.loadEngine(myGameFile.getAbsolutePath());
        runInstance();
        return true;
    }

    public void runInstance() {
        initGrid();
        loadGrid();
        setUpGrid();
        setUpSidePanel();
        myBuilder.initWindow(myStage, ENGINE_RESOURCES);
    }

    private void initGrid() {
        int gridCellsWidth = Integer.parseInt(myResources.getString("gridCellsWide"));
        int gridCellsHeight = Integer.parseInt(myResources.getString("gridCellsHeight"));
        int gridWidth = Integer.parseInt(myResources.getString("gridWidth"));
        int gridHeight = Integer.parseInt(myResources.getString("gridHeight"));
        grid = new EngineGrid(gridCellsWidth, gridCellsHeight, gridWidth, gridHeight);
    }

    private void loadGrid() {
        int colMax = myController.getNumCols();
        int rowMax = myController.getNumRows();
        grid.loadReset(rowMax, colMax);
        myBuilder.removeComponent(myRoot, grid.getGroup());
        for (int i = 0; i < rowMax; i++) {
            for (int j = 0; j < colMax; j++) {
                grid.blockToGridPane(i, j, myController.getBlock(i, j));
            }
        }
        grid.populateBorder();
        grid.setRenderMap();
        myBuilder.addComponent(myRoot, grid.getGroup());
    }

    private void setUpGrid() {
        setUpKeys();
        setUpPlayer();
        myAnimation = new EngineAnimation(myRoot, grid, myPlayer, myBuilder, myController, myStage);
        initObserver();
        myController.addObserver(myAnimation);
    }

    private void setUpKeys() {
        setOnKeyPressed(e -> myAnimation.handleKeyPress(e));
        setOnKeyReleased(e -> myAnimation.handleKeyRelease(e));
    }

    /**
     * Create the visualization of the Player, EngineCharacter. Place the model.player on the center of the screen.
     * Shift the layout of the Group such that the model.player appears at the designated row and column assigned
     * in the editing stage. May need some refactoring to clean up the math and getter calls.
     */
    private void setUpPlayer() {
        List<String> playerImagePaths = myController.getPlayerImages();
        String defaultPath = playerImagePaths.get(0);

        myPlayer = new EngineCharacter(playerImagePaths, defaultPath);
        myPlayer.setSize(grid.getBlockSize());

        int gridWidth = Integer.parseInt(myResources.getString("gridWidth"));
        int gridHeight = Integer.parseInt(myResources.getString("gridHeight"));

        myPlayer.setPosX(gridWidth / 2 - myPlayer.getSize() / 2);
        myPlayer.setPosY(gridHeight / 2 - myPlayer.getSize() / 2);

        myBuilder.addComponent(myRoot, myPlayer.getImageView());


        double a;
        double b;

        if (grid.getWidth() % 2 != 0) {
            a = ((grid.getWidth() - 1) / 2 - myController.getPlayerColumn()) * grid.getBlockSize();
        } else {
            a = (grid.getWidth() / 2 - 0.5 - myController.getPlayerColumn()) * grid.getBlockSize();
        }

        if (grid.getHeight() % 2 != 0) {
            b = ((grid.getHeight() - 1) / 2 - myController.getPlayerRow()) * grid.getBlockSize();
        } else {
            b = (grid.getHeight() / 2 - 0.5 - myController.getPlayerRow()) * grid.getBlockSize();
        }

        grid.getGroup().setLayoutX(grid.getGroup().getLayoutX() + a);
        grid.getGroup().setLayoutY(grid.getGroup().getLayoutY() + b);
    }

    private void initObserver() {
        InteractionHandler handler = myAnimation.getInteractionHandler();
        handler.addObserver(this);
    }

    private void setUpSidePanel() {
        myEngineSidePanel = new EngineSidePanel(myStage, myRoot, myBuilder, myResources, this, myController);
        myController.addObserver(myEngineSidePanel);
        myEngineSidePanel.update(myController, myController.getPlayerName());
    }


    public String getPath() {
        return ENGINE_RESOURCES;
    }

    private void winGame() {
        if (myLauncher != null) {
            myLauncher.launchWinScene();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        winGame();
    }
}