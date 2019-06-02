package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
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

    public static Stage window = new Stage();
    private Button ruleButton = new Button("Rules");
    private Button playButton = new Button("PLAY!!!");
    private Button exitButton = new Button("Exit");
    private Button backButton = new Button("Back");

    private RadioButton flyingIslandRadioButton = new RadioButton("Flying Island");
    private RadioButton caveRadioButton = new RadioButton("Cave");
    private RadioButton moonRadioButton = new RadioButton("Moon");
    private ToggleGroup chooseLevel = new ToggleGroup();


    public static Scene mainMenu;
    public static Scene playScene;
    private Scene ruleScene;
    public static Pane playLayout = new Pane();

    public static final ArrayList<Box> OBSTACLES = new ArrayList<>();


    private int levelPreviewWidth = 200;
    private int levelPreviewHeight = 107;
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


        //-------------------------------Images-----------------------------------------------
        Image redPlayerImage = new Image(new FileInputStream("./images/redPlayer.png"));
        Image greenPlayerImage = new Image(new FileInputStream("./images/greenPlayer.png"));

        Image groundImage = new Image(new FileInputStream("./images/ground.png"));
        Image boxImage = new Image(new FileInputStream("./images/box.jpg"));
        Image stoneImage = new Image(new FileInputStream("./images/stone.png"));
        Image moonGroundImage = new Image(new FileInputStream("./images/moon_ground.jpg"));

        Image skyImage = new Image(new FileInputStream("./images/sky.jpg"));
        Image caveImage = new Image(new FileInputStream("./images/cave.jpg"));
        Image earthImage = new Image(new FileInputStream("./images/earth.jpg"));

        Image redPlayerHealthImage = new Image(new FileInputStream("./images/redPlayerHealth.png"));
        Image greenPlayerHealthImage = new Image(new FileInputStream("./images/greenPlayerHealth.png"));

        Image redPlayerDeathScreenImage = new Image(new FileInputStream("./images/redPlayerDeathScreen.png"));
        Image greenPayerDeathScreenImage = new Image(new FileInputStream("./images/greenPlayerDeathScreen.png"));

        Image flyingIslandLevelImage = new Image(new FileInputStream("./images/flying_island_level.PNG"));
        Image caveLevelImage = new Image(new FileInputStream("./images/cave_level.PNG"));
        Image moonLevelImage = new Image(new FileInputStream("./images/moon_level.PNG"));

        Image rulesImage = new Image(new FileInputStream("./images/rules_image.png"));

        //------------------------------------------------------------------------------------

        //--------------------------------Image Views / Image Patterns------------------------
        ImageView redPlayerView = new ImageView(redPlayerImage);
        ImageView greenPlayerView = new ImageView(greenPlayerImage);

        ImageView skyView = new ImageView(skyImage);
        ImageView caveView = new ImageView(caveImage);
        ImageView earthView = new ImageView(earthImage);

        ImageView redPlayerHealthView = new ImageView(redPlayerHealthImage);
        ImageView greenPlayerHealthView = new ImageView(greenPlayerHealthImage);

        ImageView redPlayerDeathScreenView = new ImageView(redPlayerDeathScreenImage);
        ImageView greenPlayerDeathScreenView = new ImageView(greenPayerDeathScreenImage);

        ImageView flyingIslandLevelView = new ImageView(flyingIslandLevelImage);
        flyingIslandLevelView.setFitWidth(levelPreviewWidth);
        flyingIslandLevelView.setFitHeight(levelPreviewHeight);
        ImageView caveLevelView = new ImageView(caveLevelImage);
        caveLevelView.setFitWidth(levelPreviewWidth);
        caveLevelView.setFitHeight(levelPreviewHeight);
        ImageView moonLevelView = new ImageView(moonLevelImage);
        moonLevelView.setFitWidth(levelPreviewWidth);
        moonLevelView.setFitHeight(levelPreviewHeight);


        ImagePattern groundPattern = new ImagePattern(groundImage);
        ImagePattern boxPattern = new ImagePattern(boxImage);
        ImagePattern stonePattern = new ImagePattern(stoneImage);
        ImagePattern moonGroundPattern = new ImagePattern(moonGroundImage);

        ImageView rulesView = new ImageView(rulesImage);
        //------------------------------------------------------------------------------------
        redPlayerHealthView.setViewport(new Rectangle2D(0,0, 244, 34));
        greenPlayerHealthView.setViewport(new Rectangle2D(0,0, 244, 34));
        redPlayerHealthView.setX(10);
        redPlayerHealthView.setY(10);
        greenPlayerHealthView.setX(1280 - 244 - 30);
        greenPlayerHealthView.setY(10);
        redPlayerView.setViewport(new Rectangle2D(0,0, 190, 195));
        greenPlayerView.setViewport(new Rectangle2D(0,195, 180, 195));

        StackPane mainMenuLayout = new StackPane(
                ruleButton,
                playButton,
                exitButton,
                caveRadioButton,
                flyingIslandRadioButton,
                moonRadioButton,
                flyingIslandLevelView,
                caveLevelView,
                moonLevelView);

        StackPane.setMargin(ruleButton, new Insets(0,0,300,0));
        StackPane.setMargin(playButton, new Insets(0,0,0,0));
        StackPane.setMargin(exitButton, new Insets(300,0,0,0));
        StackPane.setMargin(backButton, new Insets(600,0,0,1150));
        StackPane.setMargin(flyingIslandRadioButton, new Insets(0,500,350, 0));
        StackPane.setMargin(flyingIslandLevelView, new Insets(0,500,500,0));
        StackPane.setMargin(caveRadioButton, new Insets(0,500,0,0));
        StackPane.setMargin(caveLevelView, new Insets(0,500,150,0));
        StackPane.setMargin(moonRadioButton, new Insets(350,500,0,0));
        StackPane.setMargin(moonLevelView, new Insets(200,500,0,0));
        StackPane.setAlignment(rulesView, Pos.CENTER);

        flyingIslandRadioButton.setToggleGroup(chooseLevel);
        caveRadioButton.setToggleGroup(chooseLevel);
        moonRadioButton.setToggleGroup(chooseLevel);
        flyingIslandRadioButton.setSelected(true);

        StackPane ruleLayout = new StackPane(backButton, rulesView);

        mainMenu = new Scene(mainMenuLayout, 1280, 720);
        primaryStage.setScene(mainMenu);
        primaryStage.show();

        playScene = new Scene(playLayout, 1280, 720);
        ruleScene = new Scene(ruleLayout, 1280, 720);

        ruleButton.setOnAction(event -> primaryStage.setScene(ruleScene));
        playButton.setOnAction(event -> {
            primaryStage.setScene(playScene);
            GameProcess gameProcess = new GameProcess(
                    playScene,
                    redPlayerView,
                    greenPlayerView,
                    redPlayerHealthView,
                    greenPlayerHealthView,
                    redPlayerDeathScreenView,
                    greenPlayerDeathScreenView
            );
            gameProcess.start();

            if (flyingIslandRadioButton.isSelected()) createLevel(skyView, groundPattern, boxPattern, Levels.LEVEL01_FlyingIsland);
            if (caveRadioButton.isSelected()) createLevel(caveView, stonePattern, stonePattern, Levels.LEVEL02_Cave);
            if (moonRadioButton.isSelected()) createLevel(earthView, moonGroundPattern, moonGroundPattern, Levels.LEVEL03_Moon);

            playLayout.getChildren().addAll(redPlayerView, greenPlayerView, redPlayerHealthView, greenPlayerHealthView);
        });
        exitButton.setOnAction(event -> closeProgram());
        backButton.setOnAction(event -> primaryStage.setScene(mainMenu));
    }

    private void closeProgram() {
        boolean answer = ConfirmBox.display("Closing", "Are you sure you want to quit?");
        if (answer) {
            window.close();
            System.exit(0);
        }
    }

    private void createLevel(ImageView background, ImagePattern groundPattern, ImagePattern boxPattern, String[] level) {
        playLayout.getChildren().add(background);
        for (int i = 0; i < level.length; i++) {
            String line = level[i];
            for (int j = 0; j < level[i].length(); j++) {
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