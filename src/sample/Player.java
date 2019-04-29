package sample;

import javafx.scene.image.ImageView;


public class Player {

    ImageView playerView;

    public Player (ImageView playerView){
        this.playerView = playerView;

    }

    public void move(double x, double y) {
       playerView.setLayoutX(playerView.getLayoutX() + x);
       playerView.setLayoutY(playerView.getLayoutY() + y);
    }

}
