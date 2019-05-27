package sample;

import com.sun.javafx.geom.BaseBounds;

import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class Player extends Rectangle {

    private ImageView playerView;
    private boolean canJump = true;
    public Point2D velocity = new Point2D(0, 5);
    private boolean canMove = true;
    public boolean isMovingRight = false;
    public boolean isMovingLeft = false;


    public Player (ImageView playerView){
        this.playerView = playerView;
    }


    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public boolean isCanMove() {
        return canMove;
    }

    public double getPlayerViewX() { return playerView.getX(); }

    public double getPlayerViewY() {
        return playerView.getY();
    }

    public ImageView getPlayerView() {
        return playerView;
    }

    public void setPlayerViewX(double value) { playerView.setX(value); }

    public void setPlayerViewY(double value) { playerView.setY(value); }


    public void setCanJump(boolean canJump) { this.canJump = canJump; }

    public void move(double x, double y) {
        Platform.runLater(() -> playerView.setX(playerView.getX() + x));
        Platform.runLater(() -> playerView.setY(playerView.getY() + y));
    }

    public void jump() {
        if (canJump) {
            velocity = velocity.add(0, -30);
            canJump = false;
        }
    }
     void fall() {
        if (velocity.getY() < 5) {
            velocity = velocity.add(0,1);
        }
     }


}
