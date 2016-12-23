package controller.battle;

import api.Player;
import controller.battle.BattleController;
import model.battle.BattleModel;
import model.battle.Difficulty;
import view.battle.BattleView;
import model.block.blocktypes.EnemyBlock;
import javafx.stage.Stage;

/**
 * Created by Bill Xiong on 12/12/16.
 *
 * @author Bill Xiong
 */
public class BattleHandler {
    private Player player;
    private EnemyBlock enemyBlock;

    public BattleHandler(Player p, EnemyBlock e) {
        player = p;
        enemyBlock = e;
    }

    public void enterBattle(Difficulty diff) {
        Stage primaryStage = new Stage();
        BattleView view = new BattleView(diff, "resources/images/battles/background/background-1.jpg");
        BattleModel model = new BattleModel(player, enemyBlock);
        BattleController controller = new BattleController(view, model);
        primaryStage.setScene(controller.getView().getScene());
        primaryStage.show();
    }
}
