package view.scenes.engine;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

/**
 * This class handles the visualization of the Player
 *
 * @author Harshil Garg, Pim Chuaylua
 */

public class EngineCharacter {

    private ImageView myImageView;

    private List<String> myImagePaths;

    public EngineCharacter(List<String> imagePaths, String defaultPath) {
        myImagePaths = imagePaths;
        myImageView = new ImageView(defaultPath);
    }

    /**
     * Given the direction sprite, the list of image paths will be traversed
     * to find the appopriate image, and then the ImageView will be set.
     *
     * @param direction UP, DOWN, LEFT, RIGHT for example
     */
    public void setImage(String direction) {
        for (String path : myImagePaths) {
            if (path.contains(direction.toLowerCase())) {
                myImageView.setImage(new Image(path));
                break;
            }
        }
    }

    /**
     * @return ImageView associated with the EngineCharacter
     */
    public ImageView getImageView() {
        return myImageView;
    }

    public void setPosX(double posX) {
        myImageView.setLayoutX(posX);
    }

    public void setPosY(double posY) {
        myImageView.setLayoutY(posY);
    }

    public void setSize(double size) {
        myImageView.setFitWidth(size);
        myImageView.setFitHeight(size);
    }

    public double getSize() {
        return myImageView.getFitWidth();
    }
}
