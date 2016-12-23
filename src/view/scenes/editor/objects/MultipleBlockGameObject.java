package view.scenes.editor.objects;

import view.grid.GridPaneNode;
import model.block.blocktypes.BlockType;

/**
 * Created by Harshil Garg on 12/7/16.
 */
public class MultipleBlockGameObject extends GameObject {

    private String[][] myDisplayPaths;

    public MultipleBlockGameObject(String iconPath, BlockType blockType, int rows, int columns) {
        super(iconPath, blockType);
        myDisplayPaths = new String[rows][columns];
        populateList();
    }

    public void populateList() {
        int index = myIconPath.indexOf('.');

        for (int i = 0; i < myDisplayPaths.length; i++) {
            for (int j = 0; j < myDisplayPaths[0].length; j++) {
                int i_name = i + 1;
                int j_name = j + 1;
                myDisplayPaths[i][j] = myIconPath.substring(0, index) + "." + i_name + "_" + j_name + myIconPath.substring(index);
                GridPaneNode node = new GridPaneNode(i, j, myDisplayPaths[i][j]);
                myImageTiles.add(node);
            }
        }
    }


}
