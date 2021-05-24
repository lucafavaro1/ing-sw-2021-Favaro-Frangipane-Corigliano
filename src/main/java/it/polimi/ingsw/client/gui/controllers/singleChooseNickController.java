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
 * GUI Controller: choosing nickname in case of single player game
 */
public class singleChooseNickController extends Controller{
    String message = "";

    @FXML
    private TextField text;

    public void enterEvent(MouseEvent mouseEvent) throws IOException {
        String message = text.getText();

        if (message.isBlank()) {
            loadScene("SingleChooseNickErr.fxml");

        } else {
            getOut().println(message);

            message = getIn().readLine();
            System.out.println(message);
            message = getIn().readLine();
            System.out.println(message);

            if(message.equals("Creazione di una nuova partita in corso...")) {
                loadItems();
                loadScene("JoiningGame.fxml");
            }
        }
    }

}
