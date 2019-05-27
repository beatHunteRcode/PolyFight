package sample;


import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class Bullet extends Rectangle {

    private ImageView bulletView;
    public Point2D velocity = new Point2D(500, 0);

    public Bullet(ImageView bulletView) {
        this.bulletView = bulletView;
    }

    public ImageView getBulletView() {
        return bulletView;
    }

    public void move(double x, double y) {
        Platform.runLater(() -> bulletView.setX(bulletView.getX() + x));
        Platform.runLater(() -> bulletView.setY(bulletView.getY() + y));
    }


}
