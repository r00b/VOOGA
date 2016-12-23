package view.battle;

import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

/*
 * Pim Chuaylua
 * 
 * taking in starting point x1, y2 and length to throw weapons
 */
public class FireBall {
    private ImageView iView;
    private Group group;

    FireBall(Group group) {
        this.group = group;
    }

    public void throwFireBall(int x1, int y1, int length) {
        initBallImage(x1, y1);
        Path path = new Path();
        path.getElements().addAll(new MoveTo(50, 50), new HLineTo(length));
        PathTransition transition = new PathTransition(Duration.millis(1000), path, iView);
        transition.play();
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(1000),
                ae -> group.getChildren().remove(iView)));
        timeline.play();
    }

    private void initBallImage(int x1, int y1) {
//		File file = new File("src/resources/images/battles/weapons");
//		String[] images = file.list();
//		int maximum = images.length-1;
//		int randomNum = (int)(Math.random() * maximum); 

        Image image = new Image("resources/images/battles/ball-1.png");
        iView = new ImageView();
        iView.setFitWidth(50);
        iView.setFitHeight(50);
        iView.setLayoutX(x1);
        iView.setLayoutY(y1);
        iView.setImage(image);
        group.getChildren().add(iView);
    }
}
