package model.xml;

import java.util.Enumeration;
import java.util.ResourceBundle;

import com.thoughtworks.xstream.XStream;

/**
 * Sets an XStream alias for all subclasses of Block.
 *
 * @author Daniel Chai
 */
public class BlockAliasFactory {
    private static final String BLOCK_PATHS = "resources/properties/block-paths";
    private static final String BLOCK_ALIAS = "block";

    private XStream xstream;

    public BlockAliasFactory(XStream xstream) {
        this.xstream = xstream;
    }

    public void setAlias() {
        ResourceBundle blockTypes = ResourceBundle.getBundle(BLOCK_PATHS);
        Enumeration<String> keys = blockTypes.getKeys();

        while (keys.hasMoreElements()) {
            String key = keys.nextElement();

            try {
                Class<?> blockClass = Class.forName(blockTypes.getString(key));
                xstream.alias(BLOCK_ALIAS, blockClass);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
