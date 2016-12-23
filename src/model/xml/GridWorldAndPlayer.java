package model.xml;

import api.Player;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import model.grid.GridWorld;

/**
 * Wrapper class on a GridManager and Player used for XStream.
 *
 * @author Daniel Chai
 */

@XStreamAlias("root")
public class GridWorldAndPlayer {

    @XStreamAlias("gridWorld")
    private GridWorld myGridWorld;

    @XStreamAlias("player")
    private Player myPlayer;

    public GridWorldAndPlayer(GridWorld gridWorld, Player player) {
        myGridWorld = gridWorld;
        myPlayer = player;
    }

    public GridWorld getGridWorld() {
        return myGridWorld;
    }

    public Player getPlayer() {
        return myPlayer;
    }
}
