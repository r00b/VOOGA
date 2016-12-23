package utilities.builder;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Robert Steilberg
 *         <p>
 *         This class builds JavaFX buttons.
 */
public class ButtonBuilder implements ComponentBuilder {

    public ButtonBuilder() {
    }

    public Node createComponent(ComponentProperties properties) {
        Button button;
        if (properties.path != null) {
            ImageView buttonImage = new ImageView(new Image(properties.path));
            buttonImage.setPreserveRatio(true);
            buttonImage.setFitWidth(properties.height / 2);
            button = new Button(properties.text, buttonImage);
        } else {
            button = new Button(properties.text);
        }
        button.setId(properties.id);
        button.setLayoutX(properties.x);
        button.setLayoutY(properties.y);
        if (properties.width > 0) {
            button.setMinWidth(properties.width);
            button.setMaxWidth(properties.width);
        }
        if (properties.height > 0) {
            button.setMinHeight(properties.height);
            button.setMaxHeight(properties.height);
        }
        return button;
    }
}
