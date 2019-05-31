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
import java.util.ArrayList;


public class Player extends Rectangle {

    private ImageView playerView;
    private boolean canJump = true;
    public Point2D velocity = new Point2D(0, 5);
    private boolean canMove = true;
    public boolean isMovingRight = false;
    public boolean isMovingLeft = false;
    public static double playerSpeedX = 5;
    public double playerSpeedY = 1;
    private ArrayList<Bullet> bullets = new ArrayList<>();

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

    public ArrayList<Bullet> getBullets() { return  bullets; }
    public void setPlayerViewX(double value) { playerView.setX(value); }

    public void setPlayerViewY(double value) { playerView.setY(value); }

    public void setCanJump(boolean canJump) { this.canJump = canJump; }

    public void move(double x, double y) {
        Platform.runLater(() -> playerView.setX(playerView.getX() + x));
        Platform.runLater(() -> playerView.setY(playerView.getY() + y));
    }

    public void moveX(double x) {
        boolean movingRight = x > 0;

        for (int i = 0; i < Math.abs(x); i++) {
            for (Box box : Main.OBSTACLES) {
                if (this.getPlayerView().getBoundsInParent().intersects(box.getBoundsInParent())) {
                    if (movingRight) {
                        if (this.getPlayerViewX() + this.getPlayerView().getFitWidth() == box.getX()) {
                            this.getPlayerView().setX(this.getPlayerView().getX() - 1); // справа
                            return;
                        }
                    }
                    else {
                        if (this.getPlayerViewX() == box.getX() + box.getWidth()) {
                            this.getPlayerView().setX(this.getPlayerView().getX() + 1);  // слева
                            return;
                        }
                    }
                }
            }
            this.getPlayerView().setX(this.getPlayerView().getX() + (movingRight ? 1 : -1));
        }
    }

    public void moveY(double y) {
        boolean movingDown = y > 0;
        for (int i = 0; i < Math.abs(y); i++) {
            for (Box box : Main.OBSTACLES) {
                if (this.getPlayerView().getBoundsInParent().intersects(box.getBoundsInParent())) {
                    if (movingDown) {
                        if (this.getPlayerViewY() + this.getPlayerView().getFitHeight() == box.getY()) {
                            this.getPlayerView().setY(this.getPlayerView().getY() - 1);  // сверху
                            playerSpeedY = 1;
                            this.setCanJump(true);
                            return;
                        }
                        else if (this.getPlayerViewY() == box.getY() + box.getHeight()) {
                            this.getPlayerView().setY(this.getPlayerView().getY() + 1);  //снизу
                            return;
                        }
                    }
                }
            }
            this.getPlayerView().setY(this.getPlayerView().getY() + (movingDown ? 1 : -1));
        }
    }
    public void jump() {
        if (canJump) {
            playerSpeedY = -30;
            canJump = false;
        }
    }
     void fall() {
        this.playerSpeedY++;
     }

    public void shoot() {
        Bullet bullet = null;
        if (isMovingLeft) {
            bullet = new Bullet(playerView.getX(), playerView.getY() + 20);

        }
        if (isMovingRight) {
            bullet = new Bullet(playerView.getX() + 10, playerView.getY() + 20);
        }
        Main.playLayout.getChildren().add(1,bullet.getBulletView());
        bullet.getBulletView().setFitWidth(20);
        bullet.getBulletView().setFitHeight(8);
        while (bullets.size() <= 5) {
            bullet.getBulletView().setVisible(true);
            bullets.add(bullet);
        }
    }
}
