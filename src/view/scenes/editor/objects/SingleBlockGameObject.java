package view.scenes.editor.objects;

import model.block.blocktypes.BlockType;
import view.grid.GridPaneNode;

/**
 * Created by harshilgarg on 12/7/16.
 */
public class SingleBlockGameObject extends GameObject {

    private String myDisplayPath;

    public SingleBlockGameObject(String iconPath, BlockType blockType) {
        super(iconPath, blockType);
        myDisplayPath = myIconPath;
        populateList();
    }

    public void populateList() {
        GridPaneNode node = new GridPaneNode(0, 0, myDisplayPath);
        myImageTiles.add(node);
    }
}