package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertWindow {

    public void display(
                String title,
                String message,
                int windowMinWidth,
                int windowMinHeight,
                int windowMaxWidth,
                int windowMaxHeight
            ) {

        Stage window = new Stage();

        //с помощью этого мы даем окну свойства AlertBox
        //т.е когда откроется AlertWindow мы не сможем взаимодействовать со всеми
        //другими окнами, пока не разберемся с AlertWindow
        //Modality.APPLICATION_MODAL как раз таки наделяет окно c этим свойством
        window.initModality(Modality.APPLICATION_MODAL);

        window.setTitle(title);
        window.setMinWidth(windowMinWidth);
        window.setMinHeight(windowMinHeight);
        window.setMaxWidth(windowMaxWidth);
        window.setMaxHeight(windowMaxHeight);

        Button okButton = new Button("OK");
        Label textLabel = new Label(message);

        okButton.setOnAction(event -> window.close());
        StackPane.setAlignment(textLabel, Pos.TOP_CENTER);

        StackPane layout = new StackPane(textLabel, okButton);
        Scene scene = new Scene(layout, 300, 300);
        window.setScene(scene);
        window.showAndWait();
    }
}
