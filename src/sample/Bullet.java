package sample;


import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Bullet {

    private ImageView bulletView;
    public int speed = 15;
    public boolean isHit;
    public boolean moveRight;
    private boolean moveLeft;

    public Bullet(double bulletX, double bulletY, boolean moveRight, boolean moveLeft) {

        try {
            bulletView = new ImageView(new Image(new FileInputStream("src/images/bullet.png")));
        }
        catch (FileNotFoundException e) {
            System.err.println("File src/images/bullet.png didn't found");
        }
        bulletView.setVisible(false);
        bulletView.setX(bulletX);
        bulletView.setY(bulletY);
        this.moveRight = moveRight;
        this.moveLeft = moveLeft;
    }

    public ImageView getBulletView() { return bulletView; }

    public void move(Player player) {
        if (player.isMovingLeft && this.moveLeft) {
            bulletView.setViewport(new Rectangle2D(0, 0, 48, 20));
            bulletView.setX(bulletView.getX() - speed);
        }
        if (player.isMovingRight && this.moveRight) {
            bulletView.setViewport(new Rectangle2D(0, 20, 48, 20));
            bulletView.setX(bulletView.getX() + speed);
        }
        this.getBulletView().setX(this.getBulletView().getX() + (moveRight ? this.speed : -this.speed));
    }
}
