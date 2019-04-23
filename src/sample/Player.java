package sample;

import javafx.animation.Animation;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Material;
import javafx.scene.shape.Rectangle;
import org.w3c.dom.css.Rect;


public class Player {

    ImageView image;
    double vX = 0.0, vY = 0.0;

    public Player (ImageView image){
        this.image = image;
    }

    public void move(double x, double y) {
       vX += x;
       vY += y;
       image.setX(this.vX);
       image.setY(this.vY);
    }
}
