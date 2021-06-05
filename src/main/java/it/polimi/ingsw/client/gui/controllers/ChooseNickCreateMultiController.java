package it.polimi.ingsw.client.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

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
