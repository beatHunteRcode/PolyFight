package sample;

import javafx.application.Platform;
import javafx.scene.image.ImageView;


public class Player {

    ImageView playerView;

    public Player (ImageView playerView){
        this.playerView = playerView;

    }

    public void move(double x, double y) {
        Platform.runLater(() -> playerView.setLayoutX(playerView.getLayoutX() + x));
        Platform.runLater(() -> playerView.setLayoutY(playerView.getLayoutY() + y));
    }

}
