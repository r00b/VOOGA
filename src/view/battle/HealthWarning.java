package view.battle;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Warning to be displayed when HP is low.
 *
 * @author Daniel Chai
 */
public class HealthWarning {
    private Alert alert;
    private WarningLevel level;
    private boolean alertShown = false;

    public enum WarningLevel {
        LOW(AlertType.WARNING, "LOW HP!", 25),
        CRITICAL(AlertType.ERROR, "CRITICALLY LOW HP!", 10);

        private AlertType type;
        private String text;
        private int threshold;

        private WarningLevel(AlertType type, String text, int threshold) {
            this.type = type;
            this.text = text;
            this.threshold = threshold;
        }
    }

    public HealthWarning(WarningLevel warningLevel) {
        level = warningLevel;
        alert = new Alert(level.type);
        alert.setContentText(level.text);
    }

    public void showAlertIfValid(double currHP) {
        if (currHP < level.threshold && !alertShown) {
            alert.show();
            alertShown = true;
        }
    }
}
