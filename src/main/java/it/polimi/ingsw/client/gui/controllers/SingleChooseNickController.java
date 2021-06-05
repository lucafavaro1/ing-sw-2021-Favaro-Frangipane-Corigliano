package it.polimi.ingsw.client.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
/**
 * GUI Controller: choosing nickname in case of single player game
 */
public class SingleChooseNickController extends Controller{

    @FXML
    private TextField text;

    /**
     * Choosing nickname by inserting it in the textfield and than click enter
     * @param mouseEvent click on enter button
     * @throws IOException if the name of the scene is wrong
     */
    public void enterEvent(MouseEvent mouseEvent) throws IOException {
        String message = text.getText();

        if (message.isBlank()) {
            loadScene("SingleChooseNickErr.fxml");

        } else {
            getOut().println(message);
            setMynickname(message);
            System.out.println("Nickname choosen:"+message);
            message = getIn().readLine();
            System.out.println(message);

            if (message.equals("You reconnected to Masters of Renaissance")) {
                loadItems();
                procedure();

                Label myNickname = (Label) getPersonalpunchboard().lookup("#yourNickname");
                myNickname.setText("  "+getMynickname()+"  ");

                getPrimarystage().setScene(getPersonalpunchboard());
            }
            else {
                message = getIn().readLine();
                System.out.println(message);

                if (message.equals("Creating a new match...")) {
                    loadItems();
                    loadScene("JoiningGame.fxml");
                }
            }
        }
    }

}
