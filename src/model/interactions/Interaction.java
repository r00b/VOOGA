package model.interactions;

import api.Player;
import model.block.BlockUpdate;

import java.io.Serializable;
import java.util.List;

/**
 * All model.interactions are to be used as classes that are going to be composed with board ui.scenes.controller.editor.objects.
 * These board ui.scenes.controller.editor.objects will interact with the model.player as dictated by the interaction class(es)
 * with which they are composed.
 *
 * @author Filip Mazurek, Aninda Manocha
 */

public interface Interaction extends Serializable {

    /**
     * Apply the interaction in the back end.
     *
     * @param player - the model.player object that interacts with a model.block
     * @return the list of changes which the front end needs to apply
     */
    List<BlockUpdate> act(Player player);
}
