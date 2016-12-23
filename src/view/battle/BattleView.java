package view.battle;

import java.util.Observable;
import java.util.Observer;

import controller.battle.BattleModelInView;
import model.battle.Difficulty;
import model.block.blocktypes.EnemyBlock;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

/**
 * @author Daniel Chai, Bill Xiong
 */
public class BattleView implements Observer {
    private static final String CSS_FILE_PATH = "resources/styles/game-engine.css";
    private static final String ENEMY_IMAGE_PATH = "resources/images/battles/pokemon-1.gif";
    private static final String PLAYER_IMAGE_PATH = "resources/images/battles/pokemon-2.gif";

    private BattleModelInView model;

    private Difficulty gameDifficulty;

    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    public static final int DAMAGE = 10;
    public static final int DISPLAY_X = 500;
    public static final int DISPLAY_Y = 250;
    private final int PLAYER_X = 50;
    private final int PLAYER_Y = 250;
    private final int ENEMY_X = 250;
    private final int ENEMY_Y = 250;

    private Scene scene;
    private Group root;
    private ImageView backgroundView;
    private EnemyView enemy;
    private PlayerView player;
    private BattleButton reduceHP;
    private BattleButton shield;
    private HealthDisplay enemyHealth;
    private HealthDisplay playerHealth;
    private Label displayPokemon;
    private boolean usingShield;

    public BattleView(Difficulty diff, String backgroundFilePath) {
        usingShield = false;
        root = new Group();
        scene = new Scene(root, WIDTH, HEIGHT);
        root.getStylesheets().add(CSS_FILE_PATH);
        gameDifficulty = diff;
        setBackground(backgroundFilePath);
        displayPokemon = new Label();
        addButtons(DISPLAY_X, DISPLAY_Y, "Attack");
    }

    public void setModel(BattleModelInView modelInView) {
        this.model = modelInView;

        enemy = new EnemyView(model.getEnemyHP(), ENEMY_X, ENEMY_Y, ENEMY_IMAGE_PATH);
        player = new PlayerView(model.getPlayerHP(), PLAYER_X, PLAYER_Y, PLAYER_IMAGE_PATH);

        enemyHealth = new HealthDisplay(ENEMY_X + 30, ENEMY_Y - 100, (int) model.getEnemyHP());
        playerHealth = new HealthDisplay(PLAYER_X - 30, PLAYER_Y - 100, (int) model.getPlayerHP());

        RandomMessage rm = new RandomMessage(root, 0, 400);

        root.getChildren().addAll(enemyHealth.getGroup(), playerHealth.getGroup());
        enemy.addToGroup(root);
        player.addToGroup(root);

        displayBattleStats();
        displayNumPokemon();
    }

    private void setBackground(String filePath) {
        Image image = new Image(filePath);
        backgroundView = new ImageView();
        backgroundView.setFitWidth(WIDTH);
        backgroundView.setFitHeight(HEIGHT);
        backgroundView.setImage(image);
        backgroundView.setLayoutX(0);
        backgroundView.setLayoutY(0);
        root.getChildren().addAll(backgroundView);
    }

    public Scene getScene() {
        return scene;
    }

    private void addButtons(int x, int y, String text) {
        reduceHP = new BattleButton(text, x, y);
        reduceHP.addToGroup(root);
        shield = new BattleButton("Shield", x, y + 100);
        shield.addToGroup(root);
        addShieldHandler();
        addReduceHandler();
    }

    private void displayNumPokemon() {
        displayPokemon.setText("Number of Pokemon: " + model.getNumPokemon());
        displayPokemon.setLayoutX(DISPLAY_X);
        displayPokemon.setLayoutY(DISPLAY_Y);
        root.getChildren().add(displayPokemon);
    }

    private void displayBattleStats() {
        Label l = new Label("Battle Won: " + model.battlesWon() + "  Battles Lost: " + model.battlesLost());
        l.setLayoutX(DISPLAY_X);
        l.setLayoutY(10);
        l.setFont(new Font("Pokemon GB", 10));
        root.getChildren().add(l);
    }

    public void displayTextPokemon() {
        displayPokemon.setText("Number of Pokemon: " + model.getNumPokemon());
        displayPokemon.setFont(new Font("Pokemon GB", 10));
    }

    private void addShieldHandler() {
        EventHandler<ActionEvent> event = actionEvent -> {
            usingShield = true;
        };
        shield.addHandler(event);
    }

    private void addReduceHandler() {
        EventHandler<ActionEvent> event = actionEvent -> {
            FireBall f = new FireBall(root);
            f.throwFireBall(PLAYER_X, PLAYER_Y, ENEMY_X);
            if (!(model.checkPlayerLost() || model.checkPlayerWon())) {
                model.setEnemyHP(model.getEnemyHP()
                        - (Math.random() * 1.45) * EnemyBlock.DEFAULT_HEALTH / gameDifficulty.getValue());
                if (!usingShield) {
                    model.setPlayerHP(model.getPlayerHP() - (Math.random()) * gameDifficulty.getValue() / 3.3);
                }
                usingShield = false;
            }
        };
        reduceHP.addHandler(event);
    }

    @Override
    public void update(Observable o, Object arg) {
        player.setHP((int) model.getPlayerHP());
        enemy.setHP((int) model.getEnemyHP());

        if (model.checkPlayerLost()) {
            lose();
        }
        if (model.checkPlayerWon()) {
            win();
        }
        displayTextPokemon();
        enemyHealth.update(enemy);
        playerHealth.update(player);
    }

    private void win() {
        model.addBattleWon();
        WinConditionView won = new WinConditionView("You won", player);
        won.addToGroup(root);
    }

    private void lose() {
        model.reduceNumPokemon();
        if (model.getNumPokemon() <= 0) {
            model.addBattleLost();
            WinConditionView lost = new WinConditionView("You lost", enemy);
            lost.addToGroup(root);
        } else {
            model.resetPlayer();
        }
    }
}
