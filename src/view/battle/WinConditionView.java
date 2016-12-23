package view.battle;

import java.util.ResourceBundle;

import utilities.PropertiesUtilities;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

/**
 * Created by Bill Xiong on 12/8/16.
 *
 * @author Bill Xiong
 */
public class WinConditionView {
    private Label label;
    private ResourceBundle resources;
    private PropertiesUtilities utilities;
    private static final String BATTLE_RESOURCES = "resources/properties/game-engine-battle";
    private static final String CSS_FILE_PATH = "resources/styles/game-engine.css";

    private ItemView itemView;

    public WinConditionView(String condition, ItemView itemView) {
        label = new Label(condition);
        resources = ResourceBundle.getBundle(BATTLE_RESOURCES);
        utilities = new PropertiesUtilities(resources);

        this.itemView = itemView;
    }

    protected void addToGroup(Group group) {
        group.getChildren().clear();
        group.getStylesheets().add(CSS_FILE_PATH);
        setUpView(group);
    }

    private void setUpView(Group group) {

        int messageX = utilities.getIntProperty("messageX");
        int messageY = utilities.getIntProperty("messageY");
        int windowWidth = utilities.getIntProperty("windowWidth");
        int windowHeight = utilities.getIntProperty("windowHeight");
        int winningPlayerX = utilities.getIntProperty("winningPlayerX");
        int winningPlayerY = utilities.getIntProperty("winningPlayerY");
        int winningPlayerSize = utilities.getIntProperty("winningPlayerSize");
        int speechBubbleX = utilities.getIntProperty("speechBubbleX");
        int speechBubbleY = utilities.getIntProperty("speechBubbleY");
        int speechBubbleWidth = utilities.getIntProperty("speechBubbleWidth");
        int speechBubbleHeight = utilities.getIntProperty("speechBubbleHeight");

        ImageView p = itemView.getView();
        p.setFitWidth(winningPlayerSize);
        p.setFitHeight(winningPlayerSize);
        p.setLayoutX(winningPlayerX);
        p.setLayoutY(winningPlayerY);

        ImageView bg = new ImageView(new Image("resources/images/battles/background/background-1.jpg"));
        bg.setFitWidth(windowWidth);
        bg.setFitHeight(windowHeight);
        bg.setLayoutX(0);
        bg.setLayoutY(0);


//        ImageView sp = new ImageView(new Image("resources/images/battles/speech-bubble.png"));
//		sp.setFitWidth(speechBubbleWidth);
//        sp.setFitHeight(speechBubbleHeight);
//        sp.setLayoutX(speechBubbleX);
//        sp.setLayoutY(speechBubbleY);

        label.setLayoutX(messageX);
        label.setLayoutY(messageY);
        label.setFont(new Font("Pokemon GB", 30));

        group.getChildren().addAll(bg, p, label);
    }

}
