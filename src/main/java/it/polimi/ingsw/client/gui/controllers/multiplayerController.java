package it.polimi.ingsw.client.gui.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class multiplayerController {
    public void joinlobbyEvent(MouseEvent mouseEvent) {
        System.out.println("Join lobby clicked");
    }

    public void createlobbyEvent(MouseEvent mouseEvent) {
        System.out.println("Create lobby clicked");
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
