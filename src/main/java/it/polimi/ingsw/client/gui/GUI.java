package it.polimi.ingsw.client.gui;

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

    /**
     * Method that loads the first scene of the GUI
     * @param stage is the stage to be shown
     * @throws Exception if fxml file name to load is wrong
     */
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
