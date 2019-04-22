package sample;

import javafx.animation.Animation;
import javafx.scene.paint.Material;
import javafx.scene.shape.Rectangle;
import org.w3c.dom.css.Rect;

import javax.swing.text.html.ImageView;

public class Player {

    Rectangle rectangle;
    Material material;

    public Player (Rectangle rectangle, Material material){
        this.rectangle = rectangle;
        this.material = material;
    }

    public void move(double x, double y) {

    }
}
