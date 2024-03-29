package it.polimi.ingsw.client.gui.controllers;

import javafx.scene.control.Label;
import javafx.scene.input.*;

import java.io.IOException;

/**
 * GUI Controller: choosing mode of play (single player or multi player)
 */
public class ChooseModeController extends Controller {

    /**
     * Method to choose singleplayer
     * @param mouseEvent click on singleplayer button
     * @throws IOException if the scene name is not correct
     */
    public void singleplayerEvent(MouseEvent mouseEvent) throws IOException {
        String message;
        getOut().println("1");
        Controller.setSingleormulti(0);

        loadScene("SingleChooseNick.fxml");

        System.out.println(getIn().readLine());
        message = getIn().readLine();
        System.out.println(message);
        message = message.replace("Choose a valid nickname (", "");
        message = message.substring(0, message.length()-1);
        Label choose = (Label) getPrimarystage().getScene().lookup("#choose");
        choose.setText(message);
    }

    /**
     * Method to choose multiplayer
     * @param mouseEvent click on multiplayer button
     * @throws IOException if the scene name is not correct
     */
    public void multiplayerEvent(MouseEvent mouseEvent) throws IOException {
        getOut().println("2");
        Controller.setSingleormulti(1);

        loadScene("MultiJoinOrCreate.fxml");

        System.out.println(getIn().readLine());
        System.out.println(getIn().readLine());
    }
}
