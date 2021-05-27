package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.common.Events.EventBroker;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Graphical User Interface Class
 */
public class GUI extends Application{
    private static final String loginscene = "/Client/LoginScene.fxml";

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage stage) throws Exception {
        // NON SOSTITUIRE CON loadScene()
        FXMLLoader loader =  new FXMLLoader((getClass().getResource(loginscene)));
        Parent root = (Parent) loader.load();
        Scene startscene = new Scene(root);

        stage.setTitle("Master of Renaissance!");
        stage.setScene(startscene);
        stage.show();

    }
}
