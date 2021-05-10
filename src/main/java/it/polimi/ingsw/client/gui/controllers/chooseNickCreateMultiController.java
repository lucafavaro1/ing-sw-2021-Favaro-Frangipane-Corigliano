package it.polimi.ingsw.client.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * GUI Controller: choosing nickname in case of creating a new multi player game
 */
public class chooseNickCreateMultiController extends Controller {
    @FXML
    private TextField text;

    public void enterEvent(MouseEvent mouseEvent) throws IOException {
        String message = text.getText();

        if (message.isBlank()) {
            FXMLLoader loader = new FXMLLoader((getClass().getResource("/Client/ChooseNickCreateMultiErr.fxml")));
            Parent root = (Parent) loader.load();

            Scene singleScene = new Scene(root);
            Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

            window.setScene(singleScene);
            window.show();
        } else {
            getOut().println(message);
            FXMLLoader loader = new FXMLLoader((getClass().getResource("/Client/CreateLobby.fxml")));
            Parent root = (Parent) loader.load();

            Scene joinscene = new Scene(root);
            Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

            window.setScene(joinscene);
            window.show();
        }
    }

    public void backEvent(MouseEvent mouseEvent) throws IOException{
        FXMLLoader loader = new FXMLLoader((getClass().getResource("/Client/MultiJoinOrCreate.fxml")));
        Parent root = (Parent) loader.load();

        Scene singleScene = new Scene(root);
        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

        window.setScene(singleScene);
        window.show();
    }
}
