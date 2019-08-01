package sample;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;


public class ConfirmWindow {

    private static boolean answer;

    public static boolean display(String title, String message) {

        Stage window = new Stage();

        //с помощью этого мы даем окну свойства ConfirmBox
        //т.е когда откроется ConfirmBox, мы не сможем взаимодействовать со всеми
        //другими окнами, пока не разберемся с ConfirmBox
        //Modality.APPLICATION_MODAL как раз таки наделяет окно c этиv свойством
        window.initModality(Modality.APPLICATION_MODAL);

        window.setTitle(title);
        window.setMaxWidth(200);
        window.setMaxHeight(100);
        window.setMinWidth(200);
        window.setMinHeight(100);

        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");
        StackPane.setMargin(yesButton, new Insets(0,100,0,0));
        StackPane.setMargin(noButton, new Insets(0,0,0,100));
        yesButton.setOnAction(event -> {
            answer = true;
            window.close();
        });
        noButton.setOnAction(event -> {
            answer = false;
            window.close();
        });

        Label label = new Label(message);
        StackPane.setAlignment(label, Pos.TOP_CENTER);

        StackPane layout = new StackPane(label, yesButton, noButton);
        Scene scene = new Scene(layout, 300, 300);
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }
}