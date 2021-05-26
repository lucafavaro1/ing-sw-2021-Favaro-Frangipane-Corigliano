package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.UserInterface;
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
        String nick = text.getText();
        String message;

        if (nick.isBlank()) {

            loadScene("ChooseNickCreateMultiErr.fxml");

        } else {
            getOut().println(nick);
            message = getIn().readLine();
            if(message.equals("Okay, chosen nickname:"+nick)) {
                setMynickname(message);
                System.out.println(getIn().readLine());

                loadScene("CreateLobby.fxml");
            }
        }
    }

}
