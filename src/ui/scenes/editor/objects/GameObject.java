package ui.scenes.editor.objects;

import java.util.*;

import javafx.scene.image.ImageView;
import ui.GridPaneNode;
import block.*;

/**
 * @author Nisa, Pim, Teddy
 */
public abstract class GameObject {

    protected String myIconPath;
    protected List<GridPaneNode> myImageTiles;
    protected ImageView myImageView;
    protected BlockType myBlockType;

    public GameObject() {
        myImageTiles = new ArrayList<>();
        myImageView = new ImageView();
        populateList();
    }

    public abstract void populateList();

    public List<GridPaneNode> getImageTiles() {
        return myImageTiles;
    }

    String rename(String a, int count) {
        return a + "." + count + ".png";
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
        return myIconPath + ".png";
    }

}
