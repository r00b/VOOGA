package view.battle;

import java.util.ArrayList;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class RandomMessage {

    private ArrayList<String> randomMessages;
    private Group group;
    private int x1;
    private int y1;
    private Scene scene;
    private Label label;

    public static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

    RandomMessage(Group group, int x1, int y1) {
        this.group = group;
        randomMessages = new ArrayList<String>();
        randomMessages.add("Keep it up!");
        randomMessages.add("You can do this!");
        randomMessages.add("It's okay!");
        this.x1 = x1;
        this.y1 = y1;

        initImageBox();
        initLabel();

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(500),
                ae -> changeText()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void initImageBox() {
        Image image = new Image("resources/images/battles/EmptyDialog.png");
        ImageView iView = new ImageView();
        iView.setFitWidth(500);
        iView.setFitHeight(100);
        iView.setLayoutX(x1);
        iView.setLayoutY(y1);
        iView.setImage(image);
        group.getChildren().add(iView);
    }

    private void initLabel() {
        int maximum = randomMessages.size() - 1;
        int randomNum = (int) (Math.random() * maximum);
        label = new Label(randomMessages.get(randomNum));
        label.setLayoutX(x1 + 30);
        label.setLayoutY(y1 + 30);
        label.setFont(new Font("Pokemon GB", 30));
        group.getChildren().add(label);
    }

    private void changeText() {
        int maximum = randomMessages.size() - 1;
        int randomNum = (int) (Math.random() * maximum);
        label.setText(randomMessages.get(randomNum));
        //label.setStyle("-fx-font: 30;");
    }

}
