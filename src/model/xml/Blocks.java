package model.xml;

import java.util.ArrayList;
import java.util.List;

import api.Block;

/**
 * Wrapper class on a list of Blocks used for XStream.
 * No longer used, but kept for reference.
 *
 * @author Daniel Chai
 * @deprecated
 */
public class Blocks {
    List<Block> blockList;

    public Blocks() {
        blockList = new ArrayList<Block>();
    }

    public List<Block> getBlocks() {
        return blockList;
    }

    public void addBlock(Block block) {
        blockList.add(block);
    }
}
