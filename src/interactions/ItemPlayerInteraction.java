package interactions;
/**
 * defines interactions between item and players
 * @author Bill Xiong
 */
import boardObjects.Block;
import editor.backend.Item;
import player.Player;

public class ItemPlayerInteraction extends AbstractInteraction{

    private Item item;
    private Player player;
    public ItemPlayerInteraction(Item item, Player player) {
        this.item = item;
        this.player = player;
    }

    public void act(){
        defaultItemPlayer(item, player);
    }
}