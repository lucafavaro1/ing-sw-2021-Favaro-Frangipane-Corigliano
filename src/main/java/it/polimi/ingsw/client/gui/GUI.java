package it.polimi.ingsw.client.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUI extends Application{
    private static final String prova = "/Client/prova.fxml";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(prova));
        Parent root = (Parent) loader.load();
        stage.setTitle("Prova dell'applicazione");
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }
}
