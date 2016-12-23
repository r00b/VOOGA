package model.block.blocktypes;

import model.interactions.DisappearInteraction;
import model.interactions.ReceivePokemonInteraction;

/**
 * @author Filip Mazurek
 */
public class PokemonGiverBlock extends CommunicatorBlock {
    private boolean hasPokemon = true;

    public PokemonGiverBlock(String name, int row, int col) {
        super(name, row, col);

        addTalkInteraction(new ReceivePokemonInteraction());
        addTalkInteraction(new DisappearInteraction(this));
    }

    public boolean getHasPokemon() {
        return hasPokemon;
    }

    public void setHasPokemon(boolean val) {
        hasPokemon = val;
    }
}
