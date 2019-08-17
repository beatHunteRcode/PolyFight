package sample;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class IPEnteringWindow {

    TextField userIPData = new TextField();
    TextField userPortData = new TextField();
    Label IPLabel = new Label("IP: ");
    Label portLabel = new Label("Port: ");
    Button connectButton = new Button("Connect");
    public String display(String title) {
        Stage window = new Stage();

        //с помощью этого мы даем окну свойства AlertBox
        //т.е когда откроется IPEnteringWindow, мы не сможем взаимодействовать со всеми
        //другими окнами, пока не разберемся с IPEnteringWindow
        //Modality.APPLICATION_MODAL как раз таки наделяет окно c этим свойством
        window.initModality(Modality.APPLICATION_MODAL);

        window.setTitle(title);
        window.setMaxWidth(300);
        window.setMaxHeight(200);
        window.setMinWidth(300);
        window.setMinHeight(200);

        //задаём длину TextField'ов
        userIPData.setMaxWidth(105);
        userPortData.setMaxWidth(50);

        StackPane elementsLayout = new StackPane(
                userIPData,
                userPortData,
                IPLabel,
                portLabel,
                connectButton
        );

        //размещаем элементы на сцене
        StackPane.setMargin(userIPData, new Insets(0,0,100,0));
        StackPane.setMargin(userPortData, new Insets(0,0,0,0));
        StackPane.setMargin(IPLabel, new Insets(0,125,100,0));
        StackPane.setMargin(portLabel, new Insets(0,80,0,0));
        StackPane.setMargin(connectButton, new Insets(100,0,0,0));

        StringBuilder IPAndPortData = new StringBuilder();
        connectButton.setOnAction(event -> {
            IPAndPortData.append(userIPData.getText()).append(":").append(userPortData.getText());
            window.close();
        });

        Scene scene = new Scene(elementsLayout, 100, 100);
        window.setScene(scene);
        window.showAndWait();

        return IPAndPortData.toString();
    }
}
