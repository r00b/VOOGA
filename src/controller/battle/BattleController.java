package controller.battle;

import model.battle.BattleModel;
import view.battle.BattleView;

/**
 * @author Daniel Chai
 */
public class BattleController {
    private BattleView myView;
    private BattleModel myModel;

    public BattleController(BattleView view, BattleModel model) {
        myView = view;
        myModel = model;
        myView.setModel(myModel);
        myModel.addObserver(myView);
    }

    public BattleView getView() {
        return myView;
    }
}
