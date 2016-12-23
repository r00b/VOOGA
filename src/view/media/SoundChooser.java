package view.media;

import java.io.File;
import java.util.HashMap;

import controller.editor.EditorController;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;


public class SoundChooser {
    private MediaPlayer player;
    private HashMap<String, String> playlist;
    private Group group;
    private ComboBox comboBox;
    private HBox hbox;
    private boolean playButtonClicked;
    private String chosenSongPath = "src/resources/sounds/Aquacorde.mp3";
    private EditorController editorController;

    public SoundChooser(EditorController editorController) {
        group = new Group();
        playlist = new HashMap<>();
        hbox = new HBox(10);
        group.getChildren().add(hbox);
        player = new MediaPlayer(new Media(new File(chosenSongPath).toURI().toString()));
        playButtonClicked = true;

        this.editorController = editorController;
        initComboBox();
        initPlayButton();
        initPauseButton();
    }

    private void initComboBox() {
        comboBox = new ComboBox();
        comboBox.setPromptText("Choose song");
        hbox.getChildren().add(comboBox);

        String folderName = "src/resources/sounds/";
        File file = new File(folderName);
        String[] sounds = file.list();

        for (String sound : sounds) {
            addSong(sound.split("\\.")[0], folderName + sound);
        }
    }

    private void addSong(String songName, String filePath) {
        playlist.put(songName, filePath);
        comboBox.getItems().add(songName);
    }

    public Group getGroup() {
        return group;
    }

    private void initPlayButton() {
        Button button = new Button();
        button.setFocusTraversable(false);
        setButtonImage(button, "resources/images/buttons/play.png");
        button.setOnAction(event -> {
            if (playButtonClicked) {
                player.stop();
            }
            String chosenSong = (String) comboBox.getValue();
            playSong(chosenSong);
        });
        hbox.getChildren().add(button);
    }

    private void playSong(String songName) {
        if (playButtonClicked == true) {
            player.stop();
        }

        chosenSongPath = playlist.get(songName);
        editorController.addMusic(chosenSongPath);
        player = new MediaPlayer(new Media(new File(chosenSongPath).toURI().toString()));
        setPlayinLoop(player);
        player.play();
        playButtonClicked = true;
    }

    private void initPauseButton() {
        Button button = new Button();
        button.setFocusTraversable(false);
        setButtonImage(button, "resources/images/buttons/pause.png");
        button.setOnAction(event -> player.stop());
        hbox.getChildren().add(button);
    }

    private void setButtonImage(Button button, String imageFilePath) {
        Image image = new Image(imageFilePath);
        ImageView itemView = new ImageView();
        itemView.setImage(image);
        itemView.setFitWidth(25);
        itemView.setFitHeight(25);
        button.setGraphic(itemView);
    }

    private void setPlayinLoop(MediaPlayer mediaPlayer) {
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
    }

    public void stop() {
        player.stop();
    }
}
