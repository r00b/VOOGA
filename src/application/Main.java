package application;

import javafx.application.Application;
import javafx.stage.Stage;
import view.UILauncher;

/**
 * Initializes the application and launches the main menu
 */
public class Main extends Application {

    /**
     * Main call that launches the JavaFX program
     *
     * @param args are the arguments passed from the command line
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Launches the VOOGA menu
     *
     * @param stage is the current JavaFX stage
     */
    @Override
    public void start(Stage stage) {
        UILauncher launcher = new UILauncher(stage);
        launcher.init();
    }
}