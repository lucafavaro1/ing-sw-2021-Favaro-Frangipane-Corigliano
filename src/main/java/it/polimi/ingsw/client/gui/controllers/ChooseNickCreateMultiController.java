package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.client.gui.GUIUserInterface;
import it.polimi.ingsw.common.Events.EventBroker;
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
 * GUI Controller: choosing nickname in case of creating a new multi player game
 */
public class ChooseNickCreateMultiController extends Controller {
    @FXML
    private TextField text;

    /**
     * Insert nickname and press okay
     * @param mouseEvent click on okay button
     * @throws IOException if the scene name is not correct
     */
    public void enterEvent(MouseEvent mouseEvent) throws IOException {
        String nick = text.getText();
        String message;

        if (nick.isBlank()) {

            loadScene("ChooseNickCreateMultiErr.fxml");

        } else {
            getOut().println(nick);
            message = getIn().readLine();
            if(message.equals("Okay, nickname chosen:"+nick)) {
                setMynickname(nick);
                System.out.println(getIn().readLine());

                loadScene("CreateLobby.fxml");
            }
            else if(message.equals("You reconnected to Masters of Renaissance")) {
                setMynickname(nick);
                loadItems();

                procedure();

                Label myNickname = (Label) getPersonalpunchboard().lookup("#yourNickname");
                myNickname.setText("  "+getMynickname()+"  ");

                getPrimarystage().setScene(getPersonalpunchboard());
            }
        }
    }

}
