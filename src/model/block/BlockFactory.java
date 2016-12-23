package model.block;

import api.Block;
import model.block.blocktypes.BlockType;
import model.exceptions.BlockCreationException;

import java.lang.reflect.Constructor;
import java.util.ResourceBundle;


/**
 * This class generates block objects
 *
 * @author Aninda Manocha, Filip Mazurek
 */
public class BlockFactory {

    private ResourceBundle myBlockPaths = ResourceBundle.getBundle("resources/properties/block-paths");

    /**
     * Use reflection to create the model.block requested by the front end class. Uses the model.block-paths.properties file to
     * decide which model.block to make based on which blockType is selected.
     *
     * @param name:      the string file path which the front end uses to render the model.block
     * @param blockType: the class of model.block which to create.
     * @param row:       row property to give the model.block
     * @param col:       column property to give the model.block
     * @return the created model.block
     */
    public Block createBlock(String name, BlockType blockType, int row, int col) throws BlockCreationException {
        try {
            Class<?> blockClass = Class.forName(myBlockPaths.getString(blockType.toString()));
            Constructor<?> constructor = blockClass.getDeclaredConstructor(String.class, int.class, int.class);
            Object block = constructor.newInstance(name, row, col);
            return (Block) block;
        } catch (Exception e) {
            throw new BlockCreationException();
        }
    }
}
