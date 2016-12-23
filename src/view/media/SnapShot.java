package view.media;


import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;

import utilities.PropertiesUtilities;
import view.scenes.engine.EngineView;

public class SnapShot {

    private Group group;
    private EngineView gameEngine;
    private PropertiesUtilities util;
    private ResourceBundle resources;
    private static final String ENGINE_RESOURCES = "resources/properties/game-engine";

    public SnapShot(EngineView gameEngine) {
        group = new Group();
        this.gameEngine = gameEngine;
        resources = ResourceBundle.getBundle(ENGINE_RESOURCES);
        util = new PropertiesUtilities(resources);
        makeButton();

    }

    private void makeButton() {
        Button button = new Button();
        button.setFocusTraversable(false);
        setButtonImage(button, "resources/images/buttons/camera.png");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                takeSnapShot(gameEngine);
            }
        });
    }

    private void setButtonImage(Button button, String imageFilePath) {
        Image image = new Image(imageFilePath);
        ImageView itemView = new ImageView();
        itemView.setImage(image);
        itemView.setFitWidth(25);
        itemView.setFitHeight(25);
        button.setGraphic(itemView);
        group.getChildren().add(button);
    }

    public Group getGroup() {
        return group;
    }

    private void takeSnapShot(Scene scene) {

        int gridWidth = util.getIntProperty("gridWidth");
        int gridHeight = util.getIntProperty("gridHeight");

        WritableImage writableImage =
                new WritableImage(gridWidth, gridHeight);
        scene.snapshot(writableImage);


        String folderName = "example-gamefiles/screenshots";
        File file = new File(folderName);
        String[] photos = file.list();

        int i = photos.length;
        File snapShot = new File("example-gamefiles/screenshots/snapshot" + i + ".png");
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", snapShot);
        } catch (IOException ex) {
            Logger.getLogger(SnapShot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
