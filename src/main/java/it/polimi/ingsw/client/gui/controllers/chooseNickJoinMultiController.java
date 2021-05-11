package it.polimi.ingsw.client.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * GUI Controller: choosing nickname in case of joining an existing lobby in multiplayer
 */
public class chooseNickJoinMultiController extends Controller {
    @FXML
    public TextField text;
    public Label invalidNick;
    private String nickname;
    private String returnmess;

    public void enterEvent(MouseEvent mouseEvent) throws IOException {
        nickname = text.getText();
        getOut().println(nickname);
        returnmess = getIn().readLine();

        if(returnmess.equals("Invalid nickname")) {
            FXMLLoader loader = new FXMLLoader((getClass().getResource("/Client/ChooseNickJoinMultiErr.fxml")));
            Parent root = (Parent) loader.load();

            Scene singleScene = new Scene(root);
            Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

            window.setScene(singleScene);
            window.show();
        }
        else {
            FXMLLoader loader = new FXMLLoader((getClass().getResource("/Client/WaitingForPlayersScene.fxml")));
            Parent root = (Parent) loader.load();

            Scene singleScene = new Scene(root);
            Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

            window.setScene(singleScene);
            window.show();
        }
    }
}
