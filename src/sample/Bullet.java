package sample;


import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Bullet extends Rectangle {


    private ImageView bulletView;
    private int speed = 30;
    public boolean isHit;

    public Bullet(double bulletX, double bulletY) {

        try {
            bulletView = new ImageView(new Image(new FileInputStream("./images/bullet.png")));
        }
        catch (FileNotFoundException e) {
            System.err.println("File ./images/bullet.png didn't found");
        }
        bulletView.setVisible(false);
        bulletView.setX(bulletX);
        bulletView.setY(bulletY);
    }

    public ImageView getBulletView() { return bulletView; }

    public void move(Player player) {
        if (player.isMovingLeft) {
            bulletView.setViewport(new Rectangle2D(0, 0, 48, 20));
            bulletView.setX(bulletView.getX() - speed);
        }
        if (player.isMovingRight) {
            bulletView.setViewport(new Rectangle2D(0, 20, 48, 20));
            bulletView.setX(bulletView.getX() + speed);
        }
        if (    this.getBulletView().getX() > 1280 ||
                this.getBulletView().getX() < -20) bulletView.setVisible(false);
    }
}
