package utilities.builder;


import view.scenes.engine.EngineSidePanel;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

public class DialogBubbleBuilder implements ComponentBuilder {
    private static final String imagePath = "/resources/images/dialog/";

    public DialogBubbleBuilder() {
    }


    public Node createComponent(ComponentProperties properties) {
        Group dialogBubble = new Group();
        Image rawImage = new Image(imagePath + "EmptyDialog.png");
        ImageView dialogImage = new ImageView(rawImage);
        dialogImage.setFitHeight(properties.height);
        dialogImage.setFitWidth(properties.width);
        Label textArea = setUpTextArea(properties);
        dialogBubble.getChildren().addAll(dialogImage, textArea);
        return dialogBubble;
    }

    private Label setUpTextArea(ComponentProperties properties) {
        Label textArea = new Label(properties.text);
        Font.loadFont(EngineSidePanel.class.getResource("/resources/fonts/PokemonGB.ttf").toExternalForm(), 30);
        textArea.setFont(new Font("Pokemon GB", 15));
        textArea.setWrapText(true);
        textArea.setMaxHeight(properties.height - (properties.height / 6));
        textArea.setMaxWidth(properties.width - (properties.width / 10));
        textArea.setLayoutX(properties.width / 20);
        textArea.setLayoutY(properties.height / 12);
        return textArea;
    }

}
