package view.scenes.editor.objects;

import java.util.*;

import model.block.blocktypes.BlockType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import view.grid.GridPaneNode;

/**
 * @author Nisa, Pim, Teddy, Harshil
 */
public abstract class GameObject {

    protected String myIconPath;
    protected List<GridPaneNode> myImageTiles;
    protected ImageView myImageView;
    protected BlockType myBlockType;

    public GameObject(String iconPath, BlockType blockType) {
        myIconPath = iconPath;
        myBlockType = blockType;
        myImageView = new ImageView(new Image(myIconPath));
        myImageTiles = new ArrayList<>();
    }

    public abstract void populateList();

    public List<GridPaneNode> getImageTiles() {
        return myImageTiles;
    }

    public void setIcon(ImageView imageView) {
        myImageView = imageView;
    }

    public ImageView getIcon() {
        return myImageView;
    }

    public BlockType getBlockType() {
        return myBlockType;
    }

    public String getIconPath() {
        return myIconPath;
    }
}