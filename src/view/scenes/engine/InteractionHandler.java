package view.scenes.engine;

import model.block.BlockUpdate;
import javafx.scene.Parent;
import javafx.stage.Stage;
import utilities.builder.UIBuilder;
import view.grid.EngineGrid;

import java.util.Observable;

/**
 * This class handles model.interactions on the front end.
 *
 * @author Aninda Manocha, Filip Mazurek
 */

public class InteractionHandler extends Observable {
    private Parent myRoot;
    private UIBuilder myUIBuilder;
    private EngineGrid myGridForEngine;
    private Stage myStage;

    public InteractionHandler(Parent root, Stage stage, UIBuilder uiBuilder, EngineGrid grid) {
        myRoot = root;
        myStage = stage;
        myUIBuilder = uiBuilder;
        myGridForEngine = grid;
    }

    private void displayMessage(String message) {
        myUIBuilder.addNewDialogBubble(myRoot, myStage, message);
    }

    private void renderTeleportation(int rowdiff, int columndiff) {
        myGridForEngine.getGroup().setLayoutX(myGridForEngine.getGroup().getLayoutX() - columndiff * 50);
        myGridForEngine.getGroup().setLayoutY(myGridForEngine.getGroup().getLayoutY() - rowdiff * 50);
    }

    private void winGame() {
        setChanged();
        notifyObservers();
    }

    private void reRenderBlock(int row, int col, String imagePath) {
        myGridForEngine.reRender(row, col, imagePath);
    }

    public void handleUpdate(BlockUpdate blockUpdate) {
        switch (blockUpdate.getType()) {
            case BATTLE:
                break;
            case DISPLAY_MESSAGE:
                displayMessage(blockUpdate.getContent());
                break;
            case RE_RENDER:
                reRenderBlock(blockUpdate.getRow(), blockUpdate.getColumn(), blockUpdate.getContent());
                break;
            case TELEPORT:
                renderTeleportation(blockUpdate.getRow(), blockUpdate.getColumn());
                break;
            case WIN_GAME:
                winGame();
                break;
            default:
                break;
        }
    }

}
