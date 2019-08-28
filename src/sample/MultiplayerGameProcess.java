package sample;

import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;

public class MultiplayerGameProcess {

    private boolean isWPressed = false;
    private boolean isAPressed = false;
    private boolean isSpacePressed = false;
    private boolean isDPressed = false;

    private Scene playScene;
    private ImageView playerView;
    private ImageView playerHealthView;
    private ImageView playerDeathScreenView;
    private long playerLastAttack = 0;
    private long playerCooldownTime = 1000; // milliseconds
    private boolean isPlayerDeathScreenAdded = false;
    private boolean isGameEnded = false;

    AnimationTimer animationTimer;

    public MultiplayerGameProcess(Scene playScene, ImageView playerView,
                       ImageView playerHealthView,
                       ImageView playerDeathScreenView) {
        this.playScene = playScene;
        this.playerView = playerView;
        this.playerHealthView = playerHealthView;
        this.playerDeathScreenView = playerDeathScreenView;
    }

    public void start() {

        Player player = new Player();
        Player enemyPlayer = new Player();
        ImageView enemyPlayerView = new ImageView();
        ArrayList<Bullet> playerBullets = player.getBullets();

        if(Main.selectRedPlayerRadioButton.isSelected()) {
            playerView.setX(140);
            playerView.setY(500);
            playerView.setFitWidth(Player.playerWidth);
            playerView.setFitHeight(Player.playerHeight);
            player.isMovingRight = true;
        }
        else if (Main.selectGreenPlayerRadioButton.isSelected()) {
            playerView.setX(1150);
            playerView.setY(400);
            playerView.setFitWidth(70);
            playerView.setFitHeight(70);
            player.isMovingLeft = true;
        }

        playScene.setOnKeyPressed (key -> {
            KeyCode keyCode = key.getCode();
            if (keyCode.equals(KeyCode.W)) isWPressed = true;
            if (keyCode.equals(KeyCode.A)) isAPressed = true;
            if (keyCode.equals(KeyCode.SPACE)) isSpacePressed = true;
            if (keyCode.equals(KeyCode.D)) isDPressed = true;

            //exit to main menu
            if (keyCode.equals(KeyCode.ESCAPE)) {
                Main.playLayout.getChildren().clear();
                Main.OBSTACLES.clear();
                Main.mainWindow.setScene(Main.mainMenu);
                animationTimer.stop();
                Main.playLayout.getChildren().removeAll(playerView, playerHealthView);
            }

            //restart
            if (player.isDead) {
                if (keyCode.equals(KeyCode.R)) {
                    Main.playLayout.getChildren().remove(playerDeathScreenView);
                    isPlayerDeathScreenAdded = false;
                    player.isDead = false;
                    player.health = 5;

                    if(Main.selectRedPlayerRadioButton.isSelected()) {
                        playerView.setX(140);
                        playerView.setY(500);
                        playerView.setFitWidth(Player.playerWidth);
                        playerView.setFitHeight(Player.playerHeight);
                        player.isMovingRight = true;
                    }
                    else if (Main.selectGreenPlayerRadioButton.isSelected()) {
                        playerView.setX(1150);
                        playerView.setY(400);
                        playerView.setFitWidth(70);
                        playerView.setFitHeight(70);
                        player.isMovingLeft = true;
                    }

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
        });

        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                if (player.isDead && !isPlayerDeathScreenAdded && !isGameEnded) {
                    Main.playLayout.getChildren().add(playerDeathScreenView);
                    isPlayerDeathScreenAdded = true;
                    isGameEnded = true;
                }

                //movement for player
                if (!player.isDead) {
                    if (isWPressed && playerView.getY() > 0) player.jump();
                    if (isAPressed && playerView.getX() > 0) {
                        player.move(-Player.playerSpeedX, 0, playerView);
                        playerView.setViewport(new Rectangle2D(0, 195, 190, 195));
                        player.isMovingLeft = true;
                        player.isMovingRight = false;
                    }
                    if (isDPressed && playerView.getX() < 1240) {
                        player.move(Player.playerSpeedX,0, playerView);
                        playerView.setViewport(new Rectangle2D(0, 0, 190, 195));
                        player.isMovingRight = true;
                        player.isMovingLeft = false;
                    }
                    if (isSpacePressed) {
                        if (player.isMovingRight) playerView.setViewport(new Rectangle2D(190, 0, 190, 195));
                        if (player.isMovingLeft) playerView.setViewport(new Rectangle2D(195, 195, 185, 195));

                        long time = System.currentTimeMillis();
                        if (time > playerLastAttack + playerCooldownTime) {
                            player.shoot(playerView);
                            playerLastAttack = time;
                        }
                    }
                }

                GameProcess.flyOfBullets(playerBullets, player);
                player.fall();
                player.move(0, player.playerSpeedY, playerView);
                GameProcess.showHealthBar(player, playerHealthView);
                player.checkDamage();
                if (!player.isDead && enemyPlayer.isDead) player.victory = true;
                GameProcess.checkBulletCollisionWithObstacles(playerBullets);
                GameProcess.checkBulletCollisionWithPlayer(playerBullets, player, playerView);
                GameProcess.checkBulletCollisionWithPlayer(playerBullets, enemyPlayer, enemyPlayerView);
            }
        };
        animationTimer.start();
    }


}
