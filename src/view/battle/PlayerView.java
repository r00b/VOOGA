package view.battle;

/**
 * Created by Bill Xiong on 12/7/16.
 *
 * @author Bill Xiong
 */
public class PlayerView extends ItemView {
    private static final String PLAYER_NAME = "Player";

    public PlayerView(double hp, int x, int y, String filePath) {
        super(PLAYER_NAME, hp, x, y, filePath);
    }

}
