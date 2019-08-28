package sample;

import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class GameProcess {

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
    private ImageView redPlayerHealthView;
    private ImageView greenPlayerHealthView;
    private ImageView redPlayerDeathScreenView;
    private ImageView greenPlayerDeathScreenView;
    private long redPlayerLastAttack = 0;
    private long greenPlayerLastAttack = 0;
    private long redPlayerCooldownTime = 1000; // milliseconds
    private long greenPlayerCooldownTime = 1000; // milliseconds
    private boolean isRedPlayerDeathScreenAdded = false;
    private boolean isGreenPlayerDeathScreenAdded = false;
    private boolean isGameEnded = false;

    AnimationTimer animationTimer;

    public GameProcess(Scene playScene, ImageView redPlayerView,
                       ImageView greenPlayerView,
                       ImageView redPlayerHealthView,
                       ImageView greenPlayerHealthView,
                       ImageView redPlayerDeathScreenView,
                       ImageView greenPLayerDeathScreenView) {
        this.playScene = playScene;
        this.redPlayerView = redPlayerView;
        this.greenPlayerView = greenPlayerView;
        this.redPlayerHealthView = redPlayerHealthView;
        this.greenPlayerHealthView = greenPlayerHealthView;
        this.redPlayerDeathScreenView = redPlayerDeathScreenView;
        this.greenPlayerDeathScreenView = greenPLayerDeathScreenView;
    }

    public void start() {

        Player redPlayer = new Player();
        Player greenPlayer = new Player();

        try {
            FileOutputStream fileOutputStream = new FileOutputStream("RedPlayerProperties.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(redPlayer);
            objectOutputStream.flush();
            objectOutputStream.close();
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }

        ArrayList<Bullet> redPlayerBullets = redPlayer.getBullets();
        ArrayList<Bullet> greenPlayerBullets = greenPlayer.getBullets();

        redPlayerView.setX(140);
        redPlayerView.setY(500);
        redPlayerView.setFitWidth(Player.playerWidth);
        redPlayerView.setFitHeight(Player.playerHeight);
        redPlayer.isMovingRight = true;

        greenPlayerView.setX(1150);
        greenPlayerView.setY(400);
        greenPlayerView.setFitWidth(70);
        greenPlayerView.setFitHeight(70);
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

            //exit to main menu
            if (keyCode.equals(KeyCode.ESCAPE)) {
                Main.playLayout.getChildren().clear();
                Main.OBSTACLES.clear();
                Main.mainWindow.setScene(Main.mainMenu);
                animationTimer.stop();
            }

            //restart
            if (redPlayer.isDead || greenPlayer.isDead) {
                if (keyCode.equals(KeyCode.R)) {
                    Main.playLayout.getChildren().remove(redPlayerDeathScreenView);
                    Main.playLayout.getChildren().remove(greenPlayerDeathScreenView);

                    isRedPlayerDeathScreenAdded = false;
                    isGreenPlayerDeathScreenAdded = false;

                    redPlayer.isDead = false;
                    greenPlayer.isDead = false;
                    redPlayer.health = 5;
                    greenPlayer.health = 5;

                    redPlayerView.setX(140);
                    redPlayerView.setY(500);
                    redPlayer.isMovingRight = true;

                    greenPlayerView.setX(1150);
                    greenPlayerView.setY(400);
                    greenPlayer.isMovingLeft = true;

                    isGameEnded = false;
                }
            }
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

        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                if (redPlayer.isDead && !isRedPlayerDeathScreenAdded && !isGameEnded) {
                    Main.playLayout.getChildren().add(redPlayerDeathScreenView);
                    isRedPlayerDeathScreenAdded = true;
                    isGameEnded = true;
                }
                if (greenPlayer.isDead && !isGreenPlayerDeathScreenAdded && !isGameEnded) {
                    Main.playLayout.getChildren().add(greenPlayerDeathScreenView);
                    isGreenPlayerDeathScreenAdded = true;
                    isGameEnded = true;
                }

                //movement for red player
                if (!redPlayer.isDead) {
                    if (isWPressed && redPlayerView.getY() > 0) redPlayer.jump();
                    if (isAPressed && redPlayerView.getX() > 0) {
                        redPlayer.move(-Player.playerSpeedX, 0, redPlayerView);
                        redPlayerView.setViewport(new Rectangle2D(0, 195, 190, 195));
                        redPlayer.isMovingLeft = true;
                        redPlayer.isMovingRight = false;
                    }
                    if (isDPressed && redPlayerView.getX() < 1240) {
                        redPlayer.move(Player.playerSpeedX,0, redPlayerView);
                        redPlayerView.setViewport(new Rectangle2D(0, 0, 190, 195));
                        redPlayer.isMovingRight = true;
                        redPlayer.isMovingLeft = false;
                    }
                    if (isSpacePressed) {
                        if (redPlayer.isMovingRight) redPlayerView.setViewport(new Rectangle2D(190, 0, 190, 195));
                        if (redPlayer.isMovingLeft) redPlayerView.setViewport(new Rectangle2D(195, 195, 185, 195));

                        long time = System.currentTimeMillis();
                        if (time > redPlayerLastAttack + redPlayerCooldownTime) {
                            redPlayer.shoot(redPlayerView);
                            redPlayerLastAttack = time;
                        }
                    }
                }

                //movement for green player
                if (!greenPlayer.isDead) {
                    if (isUpPressed && greenPlayerView.getY() > 0) greenPlayer.jump();
                    if (isLeftPressed && greenPlayerView.getX() > 0) {
                        greenPlayer.move(-Player.playerSpeedX, 0, greenPlayerView);
                        greenPlayerView.setViewport(new Rectangle2D(0, 195, 180, 195));
                        greenPlayer.isMovingLeft = true;
                        greenPlayer.isMovingRight = false;
                    }
                    if (isRightPressed && greenPlayerView.getX() < 1240) {
                        greenPlayer.move(Player.playerSpeedX, 0, greenPlayerView);
                        greenPlayerView.setViewport(new Rectangle2D(0, 0, 180, 195));
                        greenPlayer.isMovingRight = true;
                        greenPlayer.isMovingLeft = false;
                    }

                    if (isEnterPressed) {
                        if (greenPlayer.isMovingRight) greenPlayerView.setViewport(new Rectangle2D(180, 0, 180, 195));
                        if (greenPlayer.isMovingLeft) greenPlayerView.setViewport(new Rectangle2D(185, 195, 180, 195));

                        long time = System.currentTimeMillis();
                        if (time > greenPlayerLastAttack + greenPlayerCooldownTime) {
                            greenPlayer.shoot(greenPlayerView);
                            greenPlayerLastAttack = time;
                        }
                    }
                }

                //--------------------Bullet Fly------------------------------
                flyOfBullets(redPlayerBullets, redPlayer);
                flyOfBullets(greenPlayerBullets, greenPlayer);
                //------------------------------------------------------------

                redPlayer.fall();
                greenPlayer.fall();

                redPlayer.move(0, redPlayer.playerSpeedY, redPlayerView);
                greenPlayer.move(0, greenPlayer.playerSpeedY, greenPlayerView);

                showHealthBar(redPlayer, redPlayerHealthView);
                showHealthBar(greenPlayer, greenPlayerHealthView);

                redPlayer.checkDamage();
                greenPlayer.checkDamage();

                if (redPlayer.isDead && !greenPlayer.isDead)greenPlayer.victory = true;
                else greenPlayer.victory = false;
                if (greenPlayer.isDead && !redPlayer.isDead) redPlayer.victory = true;
                else redPlayer.victory = false;

                checkBulletCollisionWithObstacles(redPlayerBullets);
                checkBulletCollisionWithObstacles(greenPlayerBullets);

                checkBulletCollisionWithPlayer(redPlayerBullets, redPlayer, redPlayerView);
                checkBulletCollisionWithPlayer(redPlayerBullets, greenPlayer, greenPlayerView);
                checkBulletCollisionWithPlayer(greenPlayerBullets, greenPlayer, greenPlayerView);
                checkBulletCollisionWithPlayer(greenPlayerBullets, redPlayer, redPlayerView);

            }
        };
        animationTimer.start();
    }


    public static void flyOfBullets(ArrayList<Bullet> bullets, Player player) {
        if (!bullets.isEmpty()) {
            for (int i = 0; i < bullets.size(); i++) {
                Bullet bullet = bullets.get(i);
                    if (bullet.getBulletView().isVisible()) {
                        bullet.move(player);
                    } else bullets.remove(bullet);

                    if (bullet.getBulletView().getX() > 1280 ||
                            bullet.getBulletView().getX() < -20) bullet.getBulletView().setVisible(false);
            }
        }
    }


    public static void checkBulletCollisionWithObstacles(ArrayList<Bullet> bullets) {
        if (!bullets.isEmpty()) {
            for (int i = 0; i < bullets.size(); i++) {
                Bullet bullet = bullets.get(i);
                for (Box box : Main.OBSTACLES) {
                    if (bullet.getBulletView().isVisible()) {
                        if (bullet.getBulletView().getBoundsInParent().intersects(box.getBoundsInParent())) {
                            bullet.getBulletView().setVisible(false);
                        }
                    }
                }
            }
        }
    }

    public static void checkBulletCollisionWithPlayer(ArrayList<Bullet> bullets, Player player, ImageView playerView) {
        for (Bullet bullet : bullets) {
            for (int i = 0; i < Math.abs(bullet.speed); i++) {
                if (!bullet.isHit) {
                    if (bullet.getBulletView().getBoundsInParent().intersects(playerView.getBoundsInParent())) {
                        player.isHit = true;
                        bullet.isHit = true;
                        bullet.getBulletView().setVisible(false);
                    }
                }
            }
        }
    }

    public static void showHealthBar(Player player, ImageView playerHealthView) {
        switch (player.health) {
            case 5:
                playerHealthView.setViewport(new Rectangle2D(0,0, 244, 34));
                break;
            case 4:
                playerHealthView.setViewport(new Rectangle2D(0,34, 244, 34));
                break;
            case 3:
                playerHealthView.setViewport(new Rectangle2D(0,68, 244, 34));
                break;
            case 2:
                playerHealthView.setViewport(new Rectangle2D(0,102, 244, 34));
                break;
            case 1:
                playerHealthView.setViewport(new Rectangle2D(0,136, 244, 34));
                break;
            case 0:
                playerHealthView.setViewport(new Rectangle2D(0,170, 244, 34));
                break;
        }
    }
}