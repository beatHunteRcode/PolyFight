package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;


public class Main extends Application {

    Stage window = new Stage();
    private Button ruleButton = new Button("Rules");
    private Button playButton = new Button("PLAY!!!");
    private Button exitButton = new Button("Exit");
    private Button backButton = new Button("Back");
    private Label ruleLabel = new Label("Kill your opponent!\n\n\n" +
            "Keys for 1st player:\n" +
            "W - jump\n" +
            "A - left\n" +
//            "S - down\n" +
            "D - right\n" +
            "SPACE - shoot\n\n" +
            "Keys for 2nd player:\n" +
            "Up Arrow - jump\n" +
            "Left Arrow - left\n" +
//            "Down Arrow - down\n" +
            "Right Arrow - right\n" +
            "ENTER - shoot\n\n\n" +
            "GL HF!!!");

    private Scene mainMenu;
    private Scene playScene;
    private Scene ruleWindow;
    public static Pane playLayout = new Pane();

    public static final ArrayList<Box> OBSTACLES = new ArrayList<>();

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

        window.setMinWidth(1280);
        window.setMinHeight(720);
        window.setMaxWidth(1280);
        window.setMaxHeight(720);

        StackPane.setMargin(ruleButton, new Insets(0,0,300,0));
        StackPane.setMargin(playButton, new Insets(0,0,0,0));
        StackPane.setMargin(exitButton, new Insets(300,0,0,0));
        StackPane.setMargin(backButton, new Insets(600,0,0,1150));
        StackPane.setAlignment(ruleLabel, Pos.CENTER);

        Image redPlayerImage = new Image(new FileInputStream("./images/redPlayer.png"));
        Image greenPlayerImage = new Image(new FileInputStream("./images/greenPlayer.png"));
        Image groundImage = new Image(new FileInputStream("./images/ground.png"));
        Image skyImage = new Image(new FileInputStream("./images/sky.jpg"));
        Image boxImage = new Image(new FileInputStream("./images/box.jpg"));


        ImageView redPlayerView = new ImageView(redPlayerImage);
        ImageView greenPlayerView = new ImageView(greenPlayerImage);
        ImageView skyView = new ImageView(skyImage);
        ImagePattern groundPattern = new ImagePattern(groundImage);
        ImagePattern boxPattern = new ImagePattern(boxImage);

        redPlayerView.setViewport(new Rectangle2D(0,0, 190, 195));
        greenPlayerView.setViewport(new Rectangle2D(0,195, 180, 195));

        StackPane layout = new StackPane(ruleButton, playButton, exitButton);

        playLayout.getChildren().addAll(backButton, skyView, redPlayerView, greenPlayerView);
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

        createLevel(groundPattern, boxPattern);

    }

    private void closeProgram() {
        boolean answer = ConfirmBox.display("Closing", "Are you sure you want to quit?");
        if (answer) {
            window.close();
            System.exit(0);
        }
    }

    private void createLevel(ImagePattern groundPattern, ImagePattern boxPattern) {
        for (int i = 0; i < Levels.LEVEL01.length; i++) {
            String line = Levels.LEVEL01[i];
            for (int j = 0; j < Levels.LEVEL01[i].length(); j++) {
                switch (line.charAt(j)) {
                    case '0':
                        break;
                    case '1':
                        Box platform = createBox(j * 40, i * 40, 40, 40, groundPattern);
                        OBSTACLES.add(platform);
                        break;
                    case '2':
                        Box box = createBox(j * 40, i * 40, 40, 40, boxPattern);
                        OBSTACLES.add(box);
                        break;
                }
            }
        }
    }

    public Box createBox(int x, int y, int width, int height, ImagePattern texture) {
        Box box = new Box(width, height, texture);
        box.setX(x);
        box.setY(y);
        playLayout.getChildren().add(box);
        return box;
    }

}