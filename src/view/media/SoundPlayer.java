package view.media;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class SoundPlayer {

    private MediaPlayer player;
    private String filePath;
    private Group group;
    private HBox hbox;
    private boolean playButtonClicked;

    public SoundPlayer(String filePath) {
        this.filePath = filePath;
        this.group = new Group();
        this.filePath = filePath;
        hbox = new HBox(10);
        group.getChildren().add(hbox);

        if (filePath != null) {
            player = new MediaPlayer(new Media(new File(filePath).toURI().toString()));
            setPlayinLoop(player);
            initPlayButton();
            initPauseButton();
        }
    }


    public Group getGroup() {
        return group;
    }

    private void setPlayinLoop(MediaPlayer mediaPlayer) {
        if (mediaPlayer != null) {
            mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
        }
    }

    private void initPlayButton() {
        Button button = new Button();
        button.setFocusTraversable(false);
        setButtonImage(button, "resources/images/buttons/play.png");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (playButtonClicked == false) {
                    player.play();

                }
            }
        });
        hbox.getChildren().add(button);
    }

    private void initPauseButton() {
        Button button = new Button();
        button.setFocusTraversable(false);
        setButtonImage(button, "resources/images/buttons/pause.png");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                player.stop();
            }
        });
        hbox.getChildren().add(button);
    }

    public void addNodeToControl(Node node) {
        hbox.getChildren().add(node);
    }

    private void setButtonImage(Button button, String imageFilePath) {
        Image image = new Image(imageFilePath);
        ImageView itemView = new ImageView();
        itemView.setImage(image);
        itemView.setFitWidth(25);
        itemView.setFitHeight(25);
        button.setGraphic(itemView);
    }

    public void stopMusic() {
        player.stop();
    }


}
