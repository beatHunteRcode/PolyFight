package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;


public class Main extends Application {

    Stage window = new Stage();
    private Button ruleButton = new Button("Rules");
    private Button playButton = new Button("PLAY!!!");
    private Button exitButton = new Button("Exit");
    private Button backButton = new Button("Back");
    private Label ruleLabel = new Label("Kill your opponent!\n\n\n" +
            "Movement for 1st player:\n" +
                                    "W - jump\n" +
                                    "A - left\n" +
                                    "S - down\n" +
                                    "D - right\n\n" +
            "Movement for 2nd player:\n" +
                                    "Up Arrow - jump\n" +
                                    "Left Arrow - left\n" +
                                    "Down Arrow - down\n" +
                                    "Right Arrow - right\n\n\n" +
            "GL HF!!!");

    private Scene mainMenu;
    private Scene playScene;
    private Scene ruleWindow;



    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Pane root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        window = primaryStage;
        /* .setOnCloseRequest() обрабатывает случай, когда юзер закрывает
           приложение через красный виндоусовский крестик в правом перхнем углу окна
           перед тем как окно закроется. произойдет то, что будет в скобках
           !!!ОКНО ЗАКРОЕТСЯ В ЛЮБОМ СЛУЧАЕ. НЕВАЖНО КАКОЙ БУДЕТ РЕЗУЛЬТАТ!!!

           Эта проблема решается использованием метода consume()
           event.consume()
           Этот метод останавливает выполнение метода .setOnCloseRequest. Он как бы "поглощает" метод в котором он вызван
           Тем самым закроется окно или нет уже будет зависеть от другого метода*/
        window.setOnCloseRequest(event -> {
            event.consume();
            closeProgram();
        });

        StackPane.setMargin(ruleButton, new Insets(0,0,300,0));
        StackPane.setMargin(playButton, new Insets(0,0,0,0));
        StackPane.setMargin(exitButton, new Insets(300,0,0,0));
        StackPane.setMargin(backButton, new Insets(600,0,0,1150));
        StackPane.setAlignment(ruleLabel, Pos.CENTER);

        Image arenaImage = new Image(new FileInputStream("./images/arena.jpg"));
        Image redPlayerImage = new Image(new FileInputStream("./images/redPlayer.png"));
        Image greenPlayerImage = new Image(new FileInputStream("./images/greenPlayer.png"));

        ImageView arenaView = new ImageView(arenaImage);
        ImageView redPlayerView = new ImageView(redPlayerImage);
        ImageView greenPlayerView = new ImageView(greenPlayerImage);

        StackPane layout = new StackPane(ruleButton, playButton, exitButton);

        Pane playLayout = new Pane(backButton, arenaView, redPlayerView, greenPlayerView);
        StackPane ruleLayout = new StackPane(backButton, ruleLabel);

        mainMenu = new Scene(layout, 1280, 720);
        primaryStage.setScene(mainMenu);
        primaryStage.show();

        playScene = new Scene(playLayout, 1280, 720);
        ruleWindow = new Scene(ruleLayout, 1280, 720);

        ruleButton.setOnAction(event -> primaryStage.setScene(ruleWindow));
        playButton.setOnAction(event -> primaryStage.setScene(playScene));
        exitButton.setOnAction(event -> closeProgram());
        backButton.setOnAction(event -> primaryStage.setScene(mainMenu));

        Movement movement = new Movement(playScene, redPlayerView, greenPlayerView);
        movement.start();
    }

    private void closeProgram() {
        boolean answer = ConfirmBox.display("Closing", "Are you sure you want to quit?");
        if (answer) {
            window.close();
            System.exit(0);
        }
    }
}
