package sample;

import com.sun.javafx.geom.BaseBounds;

import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class Player {

    private ImageView playerView;
    private boolean canJump = true;
    private boolean canMove = true;
    public boolean isMovingRight = false;
    public boolean isMovingLeft = false;
    public static double playerSpeedX = 5;
    public double playerSpeedY = 20;
    public int health = 6;
    private double jumpForce = -20;
    public boolean isHit = true;
    public boolean isDead = false;
    public boolean victory = false;

    private ImageView healthView;
    private ArrayList<Bullet> bullets = new ArrayList<>();

    public Player (ImageView playerView, ImageView healthView){
        this.playerView = playerView;
        this.healthView = healthView;

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

        //движение по ОХ
        boolean movingRight = x > 0;
        for (int i = 0; i < Math.abs(x); i++) {
            for (Box box : Main.OBSTACLES) {
                if (this.getPlayerView().getBoundsInParent().intersects(box.getBoundsInParent())) {         //проверка на столкновение игрока и препятсвия
                    if (movingRight) {  // проверка столкновения справа от игрока
                        if (this.getPlayerViewX() + this.getPlayerView().getFitWidth() == box.getX()) {     //если произошло столкновение
                            this.getPlayerView().setX(this.getPlayerView().getX() - 1);                     //персонаж перемещается на 1 пиксель влево
                            return;
                        }
                    }
                    else {              // проверка столкновения слева от игрока
                        if (this.getPlayerViewX() == box.getX() + box.getWidth()) {                         //если произошло столкновение
                            this.getPlayerView().setX(this.getPlayerView().getX() + 1);                     //персонаж перемещается на 1 пиксель вправо
                            return;
                        }
                    }
                }
            }
            this.getPlayerView().setX(this.getPlayerView().getX() + (movingRight ? 1 : -1));
        }

        //движение по ОY
        boolean movingDown = y > 0;
        for (int i = 0; i < Math.abs(y); i++) {
            for (Box box : Main.OBSTACLES) {
                if (this.getPlayerView().getBoundsInParent().intersects(box.getBoundsInParent())) {
                    if (movingDown) {   // проверка столкновения снизу от игрока
                        if (this.getPlayerViewY() + this.getPlayerView().getFitHeight() == box.getY()) {    //если произошло столкноение
                            this.getPlayerView().setY(this.getPlayerView().getY() - 1);  // сверху          //персонаж перемещается на 1 пиксель вверх
                            playerSpeedY = 1;            //скорость обнуляется чтобы падение было реалистичным
                            this.setCanJump(true);       //только когда персонаж приземлится на платформу он сможет прыгнуть
                            if (victory) playerSpeedY = -10;  //празднование победы
                            return;
                        }
                    }
                    else {          // проверка столкновения сверху от игрока
                        if (this.getPlayerViewY() == box.getY() + box.getHeight()) {                    //если произошло столкновение
                            playerSpeedY = 0;//персонаж перемещается на 1 пиксель вниз
                            return;
                        }
                    }
                }
            }
            //если коллизии не произошло, то персонаж просто падает вниз, а если уже находится на платформе, то поднимается вверх на 1 пиксель
            this.getPlayerView().setY(this.getPlayerView().getY() + (movingDown ? 1 : -1));
        }
    }

    public void jump() {
        if (canJump) {
            playerSpeedY = jumpForce;
            canJump = false;
        }
    }
     void fall() {
        this.playerSpeedY++;
     }

    public void shoot() {
        Bullet bullet = null;
        if (this.isMovingLeft) {
            bullet = new Bullet(playerView.getX() - 20, playerView.getY() + 20, false, true);
        }
        if (this.isMovingRight) {
            bullet = new Bullet(playerView.getX() + playerView.getFitWidth(), playerView.getY() + 20, true, false);
        }
        Main.playLayout.getChildren().add(1,bullet.getBulletView());
        bullet.getBulletView().setFitWidth(20);
        bullet.getBulletView().setFitHeight(8);
        while (bullets.size() < 1) {
            bullet.getBulletView().setVisible(true);
            bullets.add(bullet);
        }
    }

    public void checkDamage() {
        showHealthBar(health);
        if (health < 1) isDead = true;
        if(isHit) {
            health--;
            isHit = false;
        }
    }

    public void showHealthBar(int health) {
        switch (health) {
            case 5:
                healthView.setViewport(new Rectangle2D(0,0, 244, 34));
                break;
            case 4:
                healthView.setViewport(new Rectangle2D(0,34, 244, 34));
                break;
            case 3:
                healthView.setViewport(new Rectangle2D(0,68, 244, 34));
                break;
            case 2:
                healthView.setViewport(new Rectangle2D(0,102, 244, 34));
                break;
            case 1:
                healthView.setViewport(new Rectangle2D(0,136, 244, 34));
                break;
            case 0:
                healthView.setViewport(new Rectangle2D(0,170, 244, 34));
                break;
        }
    }
}
