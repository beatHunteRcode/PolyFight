package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;




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
    private Scene playWindow;
    private Scene ruleWindow;

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setOnCloseRequest(event -> closeProgram());

        StackPane.setAlignment(ruleButton, Pos.TOP_CENTER);
        StackPane.setAlignment(playButton, Pos.CENTER);
        StackPane.setAlignment(exitButton, Pos.BOTTOM_CENTER);
        StackPane.setAlignment(backButton, Pos.BOTTOM_RIGHT);
        StackPane.setAlignment(ruleLabel, Pos.CENTER);
        StackPane layout = new StackPane(ruleButton, playButton, exitButton);
        StackPane playLayout = new StackPane(backButton);
        StackPane ruleLayout = new StackPane(backButton, ruleLabel);

        mainMenu = new Scene(layout, 1280, 720);
        primaryStage.setScene(mainMenu);
        primaryStage.show();

        playWindow = new Scene(playLayout, 1280, 720);
        ruleWindow = new Scene(ruleLayout, 1280, 720);

        ruleButton.setOnAction(event -> primaryStage.setScene(ruleWindow));
        playButton.setOnAction(event -> {
            boolean result = ConfirmBox.display("oaoaoa", "Флекксим?");
            if (!result) primaryStage.close();
        });
        exitButton.setOnAction(event -> closeProgram());
        backButton.setOnAction(event -> primaryStage.setScene(mainMenu));
    }

    private void closeProgram() {
        System.out.println("File saved!");
        window.close();
    }


}
