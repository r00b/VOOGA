
package view.scenes.engine;

import java.util.*;

//import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import model.block.BlockUpdate;
import controller.engine.EngineController;
import model.player.PlayerUpdate;
import controller.engine.UserInstruction;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import utilities.builder.UIBuilder;
import view.grid.EngineGrid;

/**
 * @author Harshil Garg, Nisakorn Valyasevi
 */
public class EngineAnimation implements Observer {

    private static final String ENGINE_RESOURCES = "resources/properties/game-engine";

    private EngineGrid grid;

    private Stack<UserInstruction> stack;

    private boolean finished;
    private double duration;
    private double pixelMovement;
    private int maxSteps;
    private int stepCount;

    private EngineCharacter player;
    private Parent root;
    private UIBuilder uiBuilder;
    private ResourceBundle myResources;

    private EngineController ec;

    private Timeline animation;

    private HashMap<KeyCode, UserInstruction> keyBindings;

    private Group gridLayout;
    private InteractionHandler interactionHandler;
    private Stage stage;


    public EngineAnimation(Parent root, EngineGrid grid, EngineCharacter player, UIBuilder uiBuilder, EngineController ec, Stage stage) {
        this.root = root;
        this.grid = grid;
        this.player = player;
        this.uiBuilder = uiBuilder;
        this.stage = stage;

        myResources = ResourceBundle.getBundle(ENGINE_RESOURCES);
        stack = new Stack<>();
        finished = true;
        duration = 225;
        maxSteps = 100;
        stepCount = 0;
        this.ec = ec;
        pixelMovement = grid.getBlockSize() / maxSteps;
        keyBindings = new HashMap<>();
        setDefaultKeyBindings();
        gridLayout = grid.getGroup();
        ec.addObserver(this);
        interactionHandler = new InteractionHandler(root, stage, uiBuilder, grid);
    }

    public InteractionHandler getInteractionHandler() {
        return interactionHandler;
    }

    private void setDefaultKeyBindings() {
        keyBindings.put(KeyCode.UP, UserInstruction.UP);
        keyBindings.put(KeyCode.DOWN, UserInstruction.DOWN);
        keyBindings.put(KeyCode.LEFT, UserInstruction.LEFT);
        keyBindings.put(KeyCode.RIGHT, UserInstruction.RIGHT);
        keyBindings.put(KeyCode.A, UserInstruction.TALK);
    }

    private UserInstruction convertKeyCode(KeyCode code) {
        return keyBindings.get(code);
    }


    public void handleKeyPress(KeyEvent e) {
        UserInstruction instruction = convertKeyCode(e.getCode());
        if (instruction == null)
            return;
        if (!stack.contains(instruction))
            stack.push(instruction);
        if (!stack.isEmpty() && finished) {
            ec.keyListener(instruction);
        }
    }

    public void handleKeyRelease(KeyEvent e) {
        UserInstruction instruction = convertKeyCode(e.getCode());
        if (instruction == null)
            return;
        if (stack.contains(instruction))
            stack.remove(instruction);
    }

    @Override
    public void update(Observable observable, Object value) {
        PlayerUpdate update = (PlayerUpdate) value;
        updatePlayer(update);
    }

    private void updatePlayer(PlayerUpdate update) {
        if (update == PlayerUpdate.ROW || update == PlayerUpdate.COLUMN) {
            processMove(stack.peek());
        }
        if (update == PlayerUpdate.DIRECTION) {
            processDirect(stack.peek());
        }
        handleInteractions();
    }

    private void handleInteractions() {
        for (BlockUpdate blockUpdate : ec.getInteractions()) {
            interactionHandler.handleUpdate(blockUpdate);
        }
    }

    private void processMove(UserInstruction key) {
        if (!keyBindings.values().contains(key)) {
            return;
        }
        finished = false;
        animateMove(key);
    }

    private void animateMove(UserInstruction instruction) {
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(new KeyFrame(Duration.millis(duration / maxSteps),
                e -> move(instruction)));
        animation.play();
    }

    private void move(UserInstruction instruction) {
        if (isAnimationOver())
            return;
        double locationX = gridLayout.getLayoutX();
        double locationY = gridLayout.getLayoutY();
        switch (instruction) {
            case UP:
                locationY += pixelMovement;
                break;
            case DOWN:
                locationY -= pixelMovement;
                break;
            case LEFT:
                locationX += pixelMovement;
                break;
            case RIGHT:
                locationX -= pixelMovement;
                break;
        }
        gridLayout.setLayoutX(locationX);
        gridLayout.setLayoutY(locationY);
        stepCount++;
    }

    private void processDirect(UserInstruction key) {
        if (!keyBindings.values().contains(key)) {
            return;
        }
        finished = false;
        animateDirect(key);
    }

    private void animateDirect(UserInstruction instruction) {
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(new KeyFrame(Duration.millis(duration / maxSteps),
                e -> direct(instruction)));
        animation.play();
    }

    private void direct(UserInstruction instruction) {
        if (isAnimationOver())
            return;
        player.setImage(instruction.name());
        stepCount = stepCount + 10;
    }

    private boolean isAnimationOver() {
        if (stepCount >= maxSteps) {
            stepCount = 0;
            animation.stop();
            finished = true;
            return true;
        }
        return false;
    }
}