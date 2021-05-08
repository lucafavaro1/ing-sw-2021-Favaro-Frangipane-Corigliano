package it.polimi.ingsw.client.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class singleplayerController extends loginsceneController{

    @FXML
    private TextField text;

    public void enterEvent(MouseEvent mouseEvent) throws IOException {
        String message = text.getText();

        if (message.isBlank()) {
            FXMLLoader loader = new FXMLLoader((getClass().getResource("/Client/singleplayersceneerr.fxml")));
            Parent root = (Parent) loader.load();

            Scene singleScene = new Scene(root);
            Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

            window.setScene(singleScene);
            window.show();
        } else {
            FXMLLoader loader = new FXMLLoader((getClass().getResource("/Client/joiningGame.fxml")));
            Parent root = (Parent) loader.load();

            Scene joinscene = new Scene(root);
            Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

            window.setScene(joinscene);
            window.show();
        }
    }

    public void backevent(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader((getClass().getResource("/Client/firstscene.fxml")));
        Parent root = (Parent) loader.load();

        Scene firstscene = new Scene(root);
        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

        window.setScene(firstscene);
        window.show();
    }
}
