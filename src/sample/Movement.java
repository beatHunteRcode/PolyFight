package sample;

import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Movement {

    private boolean isWPressed = false;
    private boolean isAPressed = false;
    private boolean isSpacePressed = false;
    private boolean isDPressed = false;
    private boolean isUpPressed = false;
    private boolean isLeftPressed = false;
    private boolean isEnterPressed = false;
    private boolean isRightPressed = false;

    private Scene playScene;
    private ImageView redPlayerView;
    private ImageView greenPlayerView;

    ArrayList<Bullet> bullets = new ArrayList<>();

    public Movement(Scene playScene, ImageView redPlayerView, ImageView greenPlayerView) {
        this.playScene = playScene;
        this.redPlayerView = redPlayerView;
        this.greenPlayerView = greenPlayerView;
    }

    public void start() {

        Player redPlayer = new Player(redPlayerView);
        Player greenPlayer = new Player(greenPlayerView);

        redPlayerView.setX(140);
        redPlayerView.setY(290);
        redPlayerView.setFitHeight(100);
        redPlayerView.setFitWidth(100);
        redPlayer.isMovingRight = true;

        greenPlayerView.setX(1070);
        greenPlayerView.setY(80);
        greenPlayerView.setFitHeight(100);
        greenPlayerView.setFitWidth(100);
        greenPlayer.isMovingLeft = true;

        playScene.setOnKeyPressed (key -> {
            KeyCode keyCode = key.getCode();
            if (keyCode.equals(KeyCode.W)) isWPressed = true;
            if (keyCode.equals(KeyCode.A)) isAPressed = true;
            if (keyCode.equals(KeyCode.SPACE)) isSpacePressed = true;
            if (keyCode.equals(KeyCode.D)) isDPressed = true;

            if (keyCode.equals(KeyCode.UP)) isUpPressed = true;
            if (keyCode.equals(KeyCode.LEFT)) isLeftPressed = true;
            if (keyCode.equals(KeyCode.ENTER)) isEnterPressed = true;
            if (keyCode.equals(KeyCode.RIGHT)) isRightPressed = true;
        });

        playScene.setOnKeyReleased (key -> {
            KeyCode keyCode = key.getCode();
            if (keyCode.equals(KeyCode.W)) isWPressed = false;
            if (keyCode.equals(KeyCode.A)) isAPressed = false;
            if (keyCode.equals(KeyCode.SPACE)) isSpacePressed = false;
            if (keyCode.equals(KeyCode.D)) isDPressed = false;

            if (keyCode.equals(KeyCode.UP)) isUpPressed = false;
            if (keyCode.equals(KeyCode.LEFT)) isLeftPressed = false;
            if (keyCode.equals(KeyCode.ENTER)) isEnterPressed = false;
            if (keyCode.equals(KeyCode.RIGHT)) isRightPressed = false;
        });


        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                collisionObstaclesCheck(redPlayer);
                collisionObstaclesCheck(greenPlayer);

                //movement for red player
                if (isSpacePressed) {
                    if (redPlayer.isMovingRight) redPlayerView.setViewport(new Rectangle2D(190,0, 190, 195));
                    if (redPlayer.isMovingLeft) redPlayerView.setViewport(new Rectangle2D(195,195, 185, 195));
                    playerShoot(redPlayer);
                }
                if (isWPressed && redPlayerView.getY() > 0) redPlayer.jump();
                if (isAPressed && redPlayerView.getX() > 0) {
                    redPlayer.move(-5.0, 0.0);
                    redPlayerView.setViewport(new Rectangle2D(0,195, 190, 195));
                    redPlayer.isMovingLeft = true;
                    redPlayer.isMovingRight = false;
                }
                if (isDPressed && redPlayerView.getX() < 1240) {
                    redPlayer.move(5.0, 0.0);
                    redPlayerView.setViewport(new Rectangle2D(0,0, 190, 195));
                    redPlayer.isMovingRight = true;
                    redPlayer.isMovingLeft = false;
                }

                //movement for green player
                if (isEnterPressed) {
                    if (greenPlayer.isMovingRight) greenPlayerView.setViewport(new Rectangle2D(180,0, 180, 195));
                    if (greenPlayer.isMovingLeft) greenPlayerView.setViewport(new Rectangle2D(185, 195, 180, 195));
                    playerShoot(greenPlayer);

                }
                if (isUpPressed && greenPlayerView.getY() > 0) greenPlayer.jump();
                if (isLeftPressed && greenPlayerView.getX() > 0) {
                    greenPlayer.move(-5.0, 0.0);
                    greenPlayerView.setViewport(new Rectangle2D(0,195, 180, 195));
                    greenPlayer.isMovingLeft = true;
                    greenPlayer.isMovingRight = false;
                }
                if (isRightPressed && greenPlayerView.getX() < 1240) {
                    greenPlayer.move(5.0, 0.0);
                    greenPlayerView.setViewport(new Rectangle2D(0,0, 180, 195));
                    greenPlayer.isMovingRight = true;
                    greenPlayer.isMovingLeft = false;
                }

                redPlayer.fall();
                greenPlayer.fall();

                redPlayer.move(0.0, redPlayer.velocity.getY());
                greenPlayer.move(0.0, greenPlayer.velocity.getY());

                //попробовать ходить по списку и рисовать пулю каждый кадр

//                moveX(redPlayerView.getX(), redPlayerView);
//                moveY(redPlayerView.getY(), redPlayerView);

//                try {
//                    ImageView playerView = new ImageView(new Image(new FileInputStream("./images/redPlayer.png")));
//                    System.out.println(box.isIntersect(playerView));
//                }
//                catch (Exception exception) {
//                    System.err.println("Picture not found!");
//                }


            }
        };
        timer.start();

        //volitile check
        //чекнуть многопотомчне программирование для решения проблемы с курсором
        //скачать scene builder с oracle
        //



    }

    private void collisionObstaclesCheck(Player player) {
        for(Box box : Main.OBSTACLES) {
            if (player.getPlayerView().getBoundsInParent().intersects(box.getBoundsInParent())) {
                if (player.getPlayerViewY() + player.getPlayerView().getFitHeight() == box.getY()) {
                    player.move(0.0, -5.0);  // сверху
                    player.setCanJump(true);
                    return;
                }
                if (player.getPlayerViewX() + player.getPlayerView().getFitWidth() == box.getX() ) {
                    player.move(-5.0, 0.0);  // справа
                    return;
                }
                if (player.getPlayerViewX() == box.getX() + box.getWidth()) {
                    player.move(5.0, 0.0);   // слева
                    return;
                }
                if (player.getPlayerViewY() == box.getY() + box.getHeight()) {
                    player.move(0.0, 5.0);   //снизу
                    return;
                }
            }
        }
    }

    private void playerShoot(Player player) {
        try {
            Bullet bullet = new Bullet(new ImageView(new Image(new FileInputStream("./images/bullet.png"))));
            bullets.add(bullet);

            ImageView bulletView = bullet.getBulletView();
            Main.playLayout.getChildren().add(bulletView);
            bulletView.setFitWidth(20);
            bulletView.setFitHeight(8);


            if (player.isMovingLeft) {
                bulletView.setViewport(new Rectangle2D(0, 0, 48, 20));
                bulletView.setX(player.getPlayerViewX() - 20);
                bulletView.setY(player.getPlayerViewY() + 35);
                bullet.move(-bullet.velocity.getX(), 0.0);
            }
            if (player.isMovingRight) {
                bulletView.setViewport(new Rectangle2D(0, 20, 48, 20));
                bulletView.setX(player.getPlayerViewX() + player.getPlayerView().getFitWidth());
                bulletView.setY(player.getPlayerViewY() + 35);
                bullet.move(bullet.velocity.getX(), 0.0);
            }
            bullets.remove(bullet);
        }
        catch (FileNotFoundException e) {
            System.err.println("File ./images/bullet.png didn't found");
        }

    }






//    public void moveX(double x, ImageView playerView) {
//        for (int i = 0; i < x; i++) {
//            for (Rectangle platform: Main.OBSTACLES) {
//                if (platform.getX() == platform.getX()) {
//                    return;
//                }
//            }
//        }
//    }
//    public void moveY(double y, ImageView playerView) {
//        for (int i = 0; i < y; i++) {
//            for (Rectangle platform: Main.OBSTACLES) {
//                if (playerView.getY() > platform.getY()) {
//                    playerView.setX(0);
//                }
//            }
//        }
//    }
}