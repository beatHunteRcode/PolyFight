package sample;

import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

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



    public Movement(Scene playScene, ImageView redPlayerView, ImageView greenPlayerView) {
        this.playScene = playScene;
        this.redPlayerView = redPlayerView;
        this.greenPlayerView = greenPlayerView;
    }

    public void start() {
        Player redPlayer = new Player(redPlayerView);
        Player greenPlayer = new Player(greenPlayerView);

        ArrayList<Bullet> redPlayerBullets = redPlayer.getBullets();
        ArrayList<Bullet> greenPlayerBullets = greenPlayer.getBullets();

        redPlayerView.setX(140);
        redPlayerView.setY(290);
        redPlayerView.setFitHeight(70);
        redPlayerView.setFitWidth(70);
        redPlayer.isMovingRight = true;

        greenPlayerView.setX(1100);
        greenPlayerView.setY(80);
        greenPlayerView.setFitHeight(70);
        greenPlayerView.setFitWidth(70);
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

        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {


                collisionObstaclesCheckY(redPlayer);

                collisionObstaclesCheckX(greenPlayer);
                collisionObstaclesCheckY(greenPlayer);

                //movement for red player
                if (isWPressed && redPlayerView.getY() > 0) redPlayer.jump();
                if (isAPressed && redPlayerView.getX() > 0) {
                    redPlayer.move(-Player.playerSpeedX, 0.0);
                    redPlayerView.setViewport(new Rectangle2D(0,195, 190, 195));
                    redPlayer.isMovingLeft = true;
                    redPlayer.isMovingRight = false;
                }
                if (isDPressed && redPlayerView.getX() < 1240) {
                    redPlayer.move(Player.playerSpeedX, 0.0);
                    collisionObstaclesCheckX(redPlayer);
                    redPlayerView.setViewport(new Rectangle2D(0,0, 190, 195));
                    redPlayer.isMovingRight = true;
                    redPlayer.isMovingLeft = false;
                }
                if (isSpacePressed) {
                    if (redPlayer.isMovingRight) redPlayerView.setViewport(new Rectangle2D(190,0, 190, 195));
                    if (redPlayer.isMovingLeft) redPlayerView.setViewport(new Rectangle2D(195,195, 185, 195));
                    redPlayer.shoot();
                }

                //movement for green player
                if (isUpPressed && greenPlayerView.getY() > 0) greenPlayer.jump();
                if (isLeftPressed && greenPlayerView.getX() > 0) {
                    greenPlayer.move(-Player.playerSpeedX, 0.0);
                    greenPlayerView.setViewport(new Rectangle2D(0,195, 180, 195));
                    greenPlayer.isMovingLeft = true;
                    greenPlayer.isMovingRight = false;
                }
                if (isRightPressed && greenPlayerView.getX() < 1240) {
                    greenPlayer.move(Player.playerSpeedX, 0.0);
                    greenPlayerView.setViewport(new Rectangle2D(0,0, 180, 195));
                    greenPlayer.isMovingRight = true;
                    greenPlayer.isMovingLeft = false;
                }
                if (isEnterPressed) {
                    if (greenPlayer.isMovingRight) greenPlayerView.setViewport(new Rectangle2D(180,0, 180, 195));
                    if (greenPlayer.isMovingLeft) greenPlayerView.setViewport(new Rectangle2D(185, 195, 180, 195));
                    greenPlayer.shoot();
                }

                //--------------------Bullet Fly------------------------------
                flyOfBullets(redPlayerBullets, redPlayer);
                flyOfBullets(greenPlayerBullets, greenPlayer);
                //------------------------------------------------------------

                redPlayer.fall();
                greenPlayer.fall();

                redPlayer.move(0.0, redPlayer.velocity.getY());
                greenPlayer.move(0.0, greenPlayer.velocity.getY());


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
        animationTimer.start();


        //volitile check
        //чекнуть многопотомчне программирование для решения проблемы с курсором
        //скачать scene builder с oracle
        //



    }

//    private void collisionObstaclesCheckX(Player player, double x) {
//        boolean movingRight = x > 0;
//
//        for (int i = 0; i < Math.abs(x); i++) {
//            for (Box box : Main.OBSTACLES) {
//                if (player.getPlayerView().getBoundsInParent().intersects(box.getBoundsInParent())) {
//                    if (player.getPlayerViewX() + player.getPlayerView().getFitWidth() == box.getX()) {
//                        player.move(-5.0, 0.0); // справа
//                        return;
//                    }
//                    if (player.getPlayerViewX() == box.getX() + box.getWidth()) {
//                        player.move(5.0, 0.0);  // слева
//                        return;
//                    }
//                }
//            }
////            player.getPlayerView().setX(player.getPlayerView().getX() + (movingRight ? 1 : -1));
//        }
//    }
//    private void collisionObstaclesCheckY(Player player, double y) {
//        boolean movingDown = y > 0;
//        for (int i = 0; i < Math.abs(y); i++) {
//            for (Box box : Main.OBSTACLES) {
//                if (player.getPlayerView().getBoundsInParent().intersects(box.getBoundsInParent())) {
//                    if (movingDown) {
//                        if (player.getPlayerViewY() + player.getPlayerView().getFitHeight() == box.getY()) {
//                            player.move(0.0, -5.0);  // сверху
//                            player.setCanJump(true);
//                            return;
//                        }
//                        else if (player.getPlayerViewY() == box.getY() + box.getHeight()) {
//                            player.move(0.0, 5.0);  //снизу
//                            return;
//                        }
//                    }
//                }
//            }
////            player.getPlayerView().setY(player.getPlayerView().getY() + (movingDown ? 1 : -1));
//        }
//    }


    public void collisionObstaclesCheckX(Player player) {
        for (int i = 0; i < Player.playerSpeedX; i++) {
            for (Box box : Main.OBSTACLES) {
                if (player.getPlayerView().getBoundsInParent().intersects(box.getBoundsInParent())) {
                    if (player.getPlayerViewX() + player.getPlayerView().getFitWidth() == box.getX()) {
                        player.move(-Player.playerSpeedX, 0.0);  // справа
                        return;
                    }
                    if (player.getPlayerViewX() == box.getX() + box.getWidth()) {
                        player.move(Player.playerSpeedX, 0.0);   // слева
                        return;
                    }
                }
            }
        }
    }
    public void collisionObstaclesCheckY(Player player) {
        for (int i = 0; i < Player.playerSpeedY; i++) {
            for (Box box : Main.OBSTACLES) {
                if (player.getPlayerView().getBoundsInParent().intersects(box.getBoundsInParent())) {
                    if (player.getPlayerViewY() + player.getPlayerView().getFitHeight() == box.getY()) {
                        player.move(0.0, -Player.playerSpeedY);  // сверху
                        player.setCanJump(true);
                        return;
                    }
                    if (player.getPlayerViewY() == box.getY() + box.getHeight()) {
                        player.move(0.0, Player.playerSpeedY);   //снизу
                        return;
                    }
                }
            }
        }
    }


    public void flyOfBullets(ArrayList<Bullet> bullets, Player player) {
        if (!bullets.isEmpty()) {
            for (int i = 0; i < bullets.size(); i++) {
                Bullet bullet = bullets.get(i);
                if (bullet.getBulletView().isVisible()) {
                    bullet.move(player);
                }
                else bullets.remove(bullet);
            }
        }
    }
//    private void playerShoot(Player player) {
//            Bullet bullet = new Bullet(player.getPlayerViewX() - 20, player.getPlayerViewY() + 35);
//            bullets.add(bullet);
//
//            ImageView bulletView = bullet.getBulletView();
//            Main.playLayout.getChildren().add(bulletView);
//            bulletView.setFitWidth(20);
//            bulletView.setFitHeight(8);
//
//            if (player.isMovingLeft) {
//                bulletView.setViewport(new Rectangle2D(0, 0, 48, 20));
//                bulletView.setX(player.getPlayerViewX() - 20);
//                bulletView.setY(player.getPlayerViewY() + 35);
//                for (int i = 0; i < bullets.size(); i++) {
//                    bullet.move(player);
//                }
//            }
//            if (player.isMovingRight) {
//                bulletView.setViewport(new Rectangle2D(0, 20, 48, 20));
//                bulletView.setX(player.getPlayerViewX() + player.getPlayerView().getFitWidth());
//                bulletView.setY(player.getPlayerViewY() + 35);
//                for (int i = 0; i < bullets.size(); i++) {
//                    bullet.move(player);
//                }
//            }
//    }

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