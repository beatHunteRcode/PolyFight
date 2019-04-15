package sample;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class AlertBox {

    public static void display(String title, String message) {

        Stage window = new Stage();

        //с помощью этого мы даем окну свойства AlertBox
        //т.е когда откроется AlertBox, мы не сможем взаимодействовать со всеми
        //другими окнами, пока не разберемся с AlertBox
        //Modality.APPLICATION_MODAL как раз таки наделяет окно эти свойством
        window.initModality(Modality.APPLICATION_MODAL);

        window.setTitle(title);
        window.setMaxWidth(250);
        window.setMaxHeight(250);
        window.setMinWidth(250);
        window.setMinHeight(250);

        Label label = new Label(message);
        StackPane.setAlignment(label, Pos.TOP_CENTER);

        Button closeButton = new Button("Close");
        StackPane.setAlignment(closeButton, Pos.CENTER);
        closeButton.setOnAction(event -> window.close());

        StackPane layout = new StackPane(label, closeButton);
        Scene scene = new Scene(layout, 300, 300);
        window.setScene(scene);
        window.showAndWait();
    }
}
