package sample;

import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

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
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (isWPressed) redPlayer.move(0.0, -1.0);
                if (isAPressed) redPlayer.move(-1.0, 0.0);
                if (isSPressed) redPlayer.move(0.0, 1.0);
                if (isDPressed) redPlayer.move(1.0, 0.0);

                if (isUpPressed) greenPlayer.move(0.0, -1.0);
                if (isLeftPressed) greenPlayer.move(-1.0,0.0);
                if (isDownPressed) greenPlayer.move(0.0, 1.0);
                if (isRightPressed) greenPlayer.move(1.0, 0.0);

            }
        };
        //volitile check
        //чекнуть многопотомчне программирование для решения проблемы с курсором
        //скачать scene builder с oracle
        //
        timer.schedule(timerTask, 0, 3);

    }


}
