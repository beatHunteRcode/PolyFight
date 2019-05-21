package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;


import java.io.FileInputStream;

public class Movement {

    private boolean isWPressed = false;
    private boolean isAPressed = false;
    private boolean isSPressed = false;
    private boolean isDPressed = false;
    private boolean isUpPressed = false;
    private boolean isLeftPressed = false;
    private boolean isDownPressed = false;
    private boolean isRightPressed = false;

    Scene playScene;
    ImageView redPlayerView;
    ImageView greenPlayerView;

    Main main = new Main();

    public Movement(Scene playScene, ImageView redPlayerView, ImageView greenPlayerView) {
        this.playScene = playScene;
        this.redPlayerView = redPlayerView;
        this.greenPlayerView = greenPlayerView;
    }

    public void start() {

        redPlayerView.setX(140);
        redPlayerView.setY(290);

        greenPlayerView.setX(1070);
        greenPlayerView.setY(80);

        playScene.setOnKeyPressed (key -> {
            KeyCode keyCode = key.getCode();
            if (keyCode.equals(KeyCode.W)) isWPressed = true;
            if (keyCode.equals(KeyCode.A)) isAPressed = true;
            if (keyCode.equals(KeyCode.S)) isSPressed = true;
            if (keyCode.equals(KeyCode.D)) isDPressed = true;

            if (keyCode.equals(KeyCode.UP)) isUpPressed = true;
            if (keyCode.equals(KeyCode.LEFT)) isLeftPressed = true;
            if (keyCode.equals(KeyCode.DOWN)) isDownPressed = true;
            if (keyCode.equals(KeyCode.RIGHT)) isRightPressed = true;
        });

        playScene.setOnKeyReleased (key -> {
            KeyCode keyCode = key.getCode();
            if (keyCode.equals(KeyCode.W)) isWPressed = false;
            if (keyCode.equals(KeyCode.A)) isAPressed = false;
            if (keyCode.equals(KeyCode.S)) isSPressed = false;
            if (keyCode.equals(KeyCode.D)) isDPressed = false;

            if (keyCode.equals(KeyCode.UP)) isUpPressed = false;
            if (keyCode.equals(KeyCode.LEFT)) isLeftPressed = false;
            if (keyCode.equals(KeyCode.DOWN)) isDownPressed = false;
            if (keyCode.equals(KeyCode.RIGHT)) isRightPressed = false;
        });

        Player redPlayer = new Player(redPlayerView);
        Player greenPlayer = new Player(greenPlayerView);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double velocity = 5;
                double a = 0.2;
                //время брать из периода таймера (period: 3)
                int time = 3;


                for(Box box : Main.PLATFORMS) {
                    if (isIntersect(redPlayer, box)) redPlayer.setCanMove(false);
                    else redPlayer.setCanMove(true);
                }

                if (isWPressed && redPlayerView.getY() > 0 && redPlayer.isCanMove()) redPlayer.move(0.0, -5.0);
                if (isAPressed && redPlayerView.getX() > 0 && redPlayer.isCanMove()) redPlayer.move(-5.0, 0.0);
                if (isSPressed && redPlayerView.getY() < 600 && redPlayer.isCanMove()) redPlayer.move(0.0, 5.0);
                if (isDPressed && redPlayerView.getX() < 1240 && redPlayer.isCanMove()) redPlayer.move(5.0, 0.0);

                for(Box box : Main.PLATFORMS) {
                    if (isIntersect(greenPlayer, box)) greenPlayer.setCanMove(false);
                    else greenPlayer.setCanMove(true);
                }

                if (isUpPressed && greenPlayerView.getY() > 0 && greenPlayer.isCanMove()) greenPlayer.move(0.0, -5.0);
                if (isLeftPressed && greenPlayerView.getX() > 0 && greenPlayer.isCanMove()) greenPlayer.move(-5.0, 0.0);
                if (isDownPressed && greenPlayerView.getY() < 600 && greenPlayer.isCanMove()) greenPlayer.move(0.0, 5.0);
                if (isRightPressed && greenPlayerView.getX() < 1240 && greenPlayer.isCanMove()) greenPlayer.move(5.0, 0.0);

                if (redPlayer.velocity.getY() < 10){
                    redPlayer.velocity = redPlayer.velocity.add(0, 2);
                }


                moveX(redPlayerView.getX(), redPlayerView);
                moveY(redPlayerView.getY(), redPlayerView);
                try {
                    ImageView playerView = new ImageView(new Image(new FileInputStream("./images/redPlayer.png")));
                    //System.out.println(box.isIntersect(playerView));
                }
                catch (Exception exception) {
                    System.err.println("Picture not found!");
                }


            }
        };
        timer.start();

        //volitile check
        //чекнуть многопотомчне программирование для решения проблемы с курсором
        //скачать scene builder с oracle
        //



    }

    public void moveX(double x, ImageView playerView) {
        for (int i = 0; i < x; i++) {
            for (Rectangle platform: main.PLATFORMS) {
                if (platform.getX() == platform.getX()) {
                    return;
                }
            }
        }
    }
    public void moveY(double y, ImageView playerView) {
        for (int i = 0; i < y; i++) {
            for (Rectangle platform: main.PLATFORMS) {
                if (playerView.getY() > platform.getY()) {
                    playerView.setX(0);
                }
            }
        }
    }

    public boolean isIntersect(Player player, Box box) {
        return  player.getPlayerViewX() == box.getBoxViewX() &&
                player.getPlayerViewY() == box.getBoxViewY();
    }
}
