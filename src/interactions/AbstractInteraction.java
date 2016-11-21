package interactions;

import boardObjects.Block;
import editor.backend.Interaction;
import editor.backend.Item;
import player.Player;

/**
 * All interactions are to be used as classes that are going to be composed with board objects.
 * These board objects will interact with the player as dictated by the interaction class(es)
 * with which they are composed.
 *
 * @author Filip Mazurek
 */
public abstract class AbstractInteraction implements Interaction{
    private Item item;
    private Block block;
    private Player player;
    public AbstractInteraction(Item item, Block block, Player player){
        this.item = item;
        this.block = block;
        this.player = player;
    }
    
    public abstract void act();
}
