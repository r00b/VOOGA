package utilities.builder;

import javafx.scene.Node;
import javafx.scene.control.Alert;

/**
 * This class creates JavaFX confirmation boxes that will display a warning to the user
 *
 * @author Aninda Manocha
 */

public class WarningBuilder implements ComponentBuilder {
    public WarningBuilder() {
    }

    public Node createComponent(ComponentProperties properties) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText(properties.header);
        alert.setContentText(properties.content);
        alert.showAndWait();
        return null;
    }
}
