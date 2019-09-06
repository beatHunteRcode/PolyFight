package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import sample.Network.Client;
import sample.Network.Server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.BindException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;


public class Main extends Application {

    public static Stage mainWindow = new Stage();
    private Button ruleButton = new Button("Rules");
    private Button playButton = new Button("PLAY!!!");
    private Button exitButton = new Button("Exit");
    private Button backButton = new Button("Back");
    private Button createServerButton = new Button("Create Server");
    private Button connectToServerButton = new Button("Connect To Server");

    public static RadioButton flyingIslandRadioButton = new RadioButton("Flying Island");
    public static RadioButton caveRadioButton = new RadioButton("Cave");
    public static RadioButton moonRadioButton = new RadioButton("Moon");
    public static RadioButton selectRedPlayerRadioButton = new RadioButton("Red Player");
    public static RadioButton selectGreenPlayerRadioButton = new RadioButton("Green Player");
    private ToggleGroup chooseLevel = new ToggleGroup();
    private ToggleGroup choosePlayer = new ToggleGroup();

    private ImageView redPlayerView;
    private ImageView greenPlayerView;
    private ImageView redPlayerHealthView;
    private ImageView greenPlayerHealthView;
    private ImageView redPlayerDeathScreenView;
    private ImageView greenPlayerDeathScreenView;
    private ImageView skyView;
    private ImageView caveView;
    private ImageView earthView;

    private ImagePattern groundPattern;
    private ImagePattern boxPattern;
    private ImagePattern stonePattern;
    private ImagePattern moonGroundPattern;

    public static Scene mainMenu;
    public static Scene playScene;
    public static Scene playerSelectingScene;
    private Scene ruleScene;
    public static Pane playLayout = new Pane();

    public static final ArrayList<Box> OBSTACLES = new ArrayList<>();


    private int levelPreviewWidth = 200;
    private int levelPreviewHeight = 107;


    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Pane root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        //Запускаем главное меню и игровой мир
        mainMenuInitialize(primaryStage);
    }

    private void mainMenuInitialize(Stage primaryStage) throws IOException {
        mainWindow = primaryStage;
        /* .setOnCloseRequest() обрабатывает случай, когда юзер закрывает
           приложение через красный виндоусовский крестик в правом перхнем углу окна
           перед тем как окно закроется. произойдет то, что будет в скобках
           !!!ОКНО ЗАКРОЕТСЯ В ЛЮБОМ СЛУЧАЕ. НЕВАЖНО КАКОЙ БУДЕТ РЕЗУЛЬТАТ!!!
           Эта проблема решается использованием метода consume()
           event.consume()
           Этот метод останавливает выполнение метода .setOnCloseRequest. Он как бы "поглощает" метод в котором он вызван
           Тем самым закроется окно или нет уже будет зависеть от другого метода*/
        mainWindow.setOnCloseRequest(event -> {
            event.consume();
            closingProgram();
        });


        mainWindow.setMinWidth(1280);
        mainWindow.setMinHeight(720);
        mainWindow.setMaxWidth(1280);
        mainWindow.setMaxHeight(720);


        //-------------------------------Images-----------------------------------------------
        Image redPlayerImage = new Image(new FileInputStream("src/images/redPlayer.png"));
        Image greenPlayerImage = new Image(new FileInputStream("src/images/greenPlayer.png"));

        Image groundImage = new Image(new FileInputStream("src/images/ground.png"));
        Image boxImage = new Image(new FileInputStream("src/images/box.jpg"));
        Image stoneImage = new Image(new FileInputStream("src/images/stone.png"));
        Image moonGroundImage = new Image(new FileInputStream("src/images/moon_ground.jpg"));

        Image skyImage = new Image(new FileInputStream("src/images/sky.jpg"));
        Image caveImage = new Image(new FileInputStream("src/images/cave.jpg"));
        Image earthImage = new Image(new FileInputStream("src/images/earth.jpg"));

        Image redPlayerHealthImage = new Image(new FileInputStream("src/images/redPlayerHealth.png"));
        Image greenPlayerHealthImage = new Image(new FileInputStream("src/images/greenPlayerHealth.png"));

        Image redPlayerDeathScreenImage = new Image(new FileInputStream("src/images/redPlayerDeathScreen.png"));
        Image greenPayerDeathScreenImage = new Image(new FileInputStream("src/images/greenPlayerDeathScreen.png"));

        Image flyingIslandLevelImage = new Image(new FileInputStream("src/images/flying_island_level.PNG"));
        Image caveLevelImage = new Image(new FileInputStream("src/images/cave_level.PNG"));
        Image moonLevelImage = new Image(new FileInputStream("src/images/moon_level.PNG"));

        Image rulesImage = new Image(new FileInputStream("src/images/rules_image.png"));
        //------------------------------------------------------------------------------------

        //--------------------------------Image Views / Image Patterns------------------------
        redPlayerView = new ImageView(redPlayerImage);
        greenPlayerView = new ImageView(greenPlayerImage);

        skyView = new ImageView(skyImage);
        caveView = new ImageView(caveImage);
        earthView = new ImageView(earthImage);

        redPlayerHealthView = new ImageView(redPlayerHealthImage);
        greenPlayerHealthView = new ImageView(greenPlayerHealthImage);

        redPlayerDeathScreenView = new ImageView(redPlayerDeathScreenImage);
        greenPlayerDeathScreenView = new ImageView(greenPayerDeathScreenImage);

        ImageView flyingIslandLevelView = new ImageView(flyingIslandLevelImage);
        flyingIslandLevelView.setFitWidth(levelPreviewWidth);
        flyingIslandLevelView.setFitHeight(levelPreviewHeight);
        ImageView caveLevelView = new ImageView(caveLevelImage);
        caveLevelView.setFitWidth(levelPreviewWidth);
        caveLevelView.setFitHeight(levelPreviewHeight);
        ImageView moonLevelView = new ImageView(moonLevelImage);
        moonLevelView.setFitWidth(levelPreviewWidth);
        moonLevelView.setFitHeight(levelPreviewHeight);

        groundPattern = new ImagePattern(groundImage);
        boxPattern = new ImagePattern(boxImage);
        stonePattern = new ImagePattern(stoneImage);
        moonGroundPattern = new ImagePattern(moonGroundImage);

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
                createServerButton,
                connectToServerButton,
                flyingIslandRadioButton,
                moonRadioButton,
                flyingIslandLevelView,
                caveLevelView,
                moonLevelView
        );

        StackPane.setMargin(ruleButton, new Insets(0,0,300,0));
        StackPane.setMargin(playButton, new Insets(0,0,0,0));
        StackPane.setMargin(exitButton, new Insets(300,0,0,0));
        StackPane.setMargin(backButton, new Insets(600,0,0,1150));
        StackPane.setMargin(createServerButton, new Insets(0,0, 150, 500));
        StackPane.setMargin(connectToServerButton, new Insets(150, 0, 0, 500));
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
        mainWindow.setScene(mainMenu);
        mainWindow.show();

        playScene = new Scene(playLayout, 1280, 720);
        ruleScene = new Scene(ruleLayout, 1280, 720);

        ruleButton.setOnAction(event -> mainWindow.setScene(ruleScene));
        playButton.setOnAction(event -> {
            mainWindow.setScene(playScene);
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
        exitButton.setOnAction(event -> closingProgram());
        backButton.setOnAction(event -> primaryStage.setScene(mainMenu));
        connectToServerButton.setOnAction(event -> {
            connectingWindowShow();
            Thread clientConnectingThread = new Thread(this::clientConnecting);
            clientConnectingThread.start();
        });
        createServerButton.setOnAction(event -> {
            try {
                createSelectingPlayerScene();
            }
            catch (FileNotFoundException exception) {
                exception.printStackTrace();
            }
        });


    }

    private void closingProgram() {
        boolean answer = ConfirmWindow.display(
                "Closing",
                "Are you sure you want to quit?",
                300,
                200,
                300,
                200);
        if (answer) {
            mainWindow.close();
            System.exit(0);
        }
    }

    private void connectingWindowShow() {
        try {
            IPEnteringWindow ipEnteringWindow = new IPEnteringWindow();
            String[] ipAndPortArray = new String[2];
            ipAndPortArray = ipEnteringWindow.display("IP & Port Entering").split(":");
            if (ipAndPortArray.length < 2) return;
            Client.SERVER_IP = ipAndPortArray[0];
            Client.SERVER_PORT = Integer.parseInt(ipAndPortArray[1]);
        }
        catch (NumberFormatException exception) {
            AlertWindow alertWindow = new AlertWindow();
            alertWindow.display(
                    "Error",
                    "Incorrect IP or Port!",
                    200,
                    100,
                    200,
                    100);
        }
    }

    public void clientConnecting() {
        try {
            Client client = new Client();
            client.connect();
        }
        catch (SocketException | UnknownHostException exception) {
            Runnable alertWindowShow = () -> {
                AlertWindow alertWindow = new AlertWindow();
                alertWindow.display(
                        "Error",
                        "Server not found",
                        300,
                        200,
                        300,
                        200);
            };
            Platform.runLater(alertWindowShow);
        }

        catch (IOException | InterruptedException exception) {
            exception.printStackTrace();
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

    private void serverStart() {
        //запуск сервера
            try {
                Server server = new Server();
                server.run();
            }
            catch (BindException exception) {
                Runnable serverCreatedWindowShow = () -> {
                    AlertWindow alertWindow = new AlertWindow();
                    alertWindow.display(
                            "Error",
                            "Server has already created!",
                            300,
                            200,
                            300,
                            200);
                };
                Platform.runLater(serverCreatedWindowShow);
            }
            catch (IOException | InterruptedException exception) {
                exception.printStackTrace();
            }
    }

    public void createSelectingPlayerScene() throws FileNotFoundException {
        Button playButton = new Button("PLAY!!!");
        Button backButton = new Button("Back");
        ImageView selectRedPlayerImageView = new ImageView(new Image(new FileInputStream("src/images/redPlayer.png")));
        ImageView selectGreenPlayerImageView = new ImageView(new Image(new FileInputStream("src/images/greenPlayer.png")));
        selectRedPlayerImageView.setViewport(new Rectangle2D(0, 0, 190, 195));
        selectRedPlayerImageView.setFitWidth(Player.playerWidth + 50);
        selectRedPlayerImageView.setFitHeight(Player.playerHeight + 50);
        selectGreenPlayerImageView.setViewport(new Rectangle2D(0, 195, 180, 195));
        selectGreenPlayerImageView.setFitWidth(Player.playerWidth + 50);
        selectGreenPlayerImageView.setFitHeight(Player.playerHeight + 50);
        StackPane playerSelectingPane = new StackPane(
                selectRedPlayerRadioButton,
                selectGreenPlayerRadioButton,
                selectRedPlayerImageView,
                selectGreenPlayerImageView,
                playButton,
                backButton
        );

        StackPane.setMargin(selectRedPlayerImageView, new Insets(0,500,200,0));
        StackPane.setMargin(selectGreenPlayerImageView, new Insets(0,0,200,500));
        StackPane.setMargin(selectRedPlayerRadioButton, new Insets(0,500,0,0));
        StackPane.setMargin(selectGreenPlayerRadioButton, new Insets(0,0,0,500));
        StackPane.setMargin(playButton, new Insets(100,0,0,0));
        StackPane.setMargin(backButton, new Insets(400,0,0,0));
        selectRedPlayerRadioButton.setToggleGroup(choosePlayer);
        selectGreenPlayerRadioButton.setToggleGroup(choosePlayer);
        selectRedPlayerRadioButton.setSelected(true);
        Scene playerSelectingScene = new Scene(playerSelectingPane, 1280, 720);
        mainWindow.setScene(playerSelectingScene);

        playButton.setOnAction(event -> {
            //Выделяем отдельный поток для запуска сервера
            Thread serverRunningThread = new Thread(this::serverStart); //лямбда Runnable заменена на обращение к методу класса
            serverRunningThread.start();

            if (selectRedPlayerRadioButton.isSelected()) {
                playLayout.getChildren().addAll(redPlayerView, redPlayerHealthView);
                MultiplayerGameProcess multiplayerGameProcess = new MultiplayerGameProcess(
                        playScene,
                        redPlayerView,
                        redPlayerHealthView,
                        redPlayerDeathScreenView,
                        serverRunningThread
                );
                multiplayerGameProcess.start();
            }
            if (selectGreenPlayerRadioButton.isSelected()) {
                MultiplayerGameProcess multiplayerGameProcess = new MultiplayerGameProcess(
                        playScene,
                        greenPlayerView,
                        greenPlayerHealthView,
                        greenPlayerDeathScreenView,
                        serverRunningThread
                );
                multiplayerGameProcess.start();
                playLayout.getChildren().addAll(greenPlayerView, greenPlayerHealthView);
            }

            if (flyingIslandRadioButton.isSelected()) createLevel(skyView, groundPattern, boxPattern, Levels.LEVEL01_FlyingIsland);
            if (caveRadioButton.isSelected()) createLevel(caveView, stonePattern, stonePattern, Levels.LEVEL02_Cave);
            if (moonRadioButton.isSelected()) createLevel(earthView, moonGroundPattern, moonGroundPattern, Levels.LEVEL03_Moon);

            mainWindow.setScene(playScene);
        });
        backButton.setOnAction(event -> mainWindow.setScene(mainMenu));
    }
}