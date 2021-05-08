package it.polimi.ingsw.client.gui.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.*;
import javafx.stage.Stage;

import java.io.IOException;

public class firstsceneController extends loginsceneController {

    public void singleplayerEvent(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader =  new FXMLLoader((getClass().getResource("/Client/singleplayerscene.fxml")));
        Parent root = (Parent) loader.load();

        Scene singleScene = new Scene(root);
        Stage window = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();

        window.setScene(singleScene);
        window.show();
    }

    public void multiplayerEvent(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader =  new FXMLLoader((getClass().getResource("/Client/multiplayerscene1.fxml")));
        Parent root = (Parent) loader.load();

        Scene multiscene = new Scene(root);
        Stage window = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();

        window.setScene(multiscene);
        window.show();    }
}
