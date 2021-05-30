package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.client.gui.GUIUserInterface;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * GUI Controller: choosing the bonus resources at the beginning of the game
 */

public class chooseResourceController extends Controller {
    /**
     * Get a bonus Coin
     * @param mouseEvent click on the coin image
     */
    public void coinchosen(MouseEvent mouseEvent) {
        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        ((GUIUserInterface) UserInterface.getInstance()).choose(1);
        window.close();
    }
    /**
     * Get a bonus Stone
     * @param mouseEvent click on the stone image
     */
    public void stonechosen(MouseEvent mouseEvent) {
        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        ((GUIUserInterface)UserInterface.getInstance()).choose(2);
        window.close();
    }

    /**
     * Get a bonus Shield
     * @param mouseEvent click on the shield image
     */
    public void shieldchosen(MouseEvent mouseEvent) {
        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        ((GUIUserInterface)UserInterface.getInstance()).choose(4);
        window.close();
    }

    /**
     * Get a bonus Servant
     * @param mouseEvent click on the servant image
     */
    public void servantchosen(MouseEvent mouseEvent) {
        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        ((GUIUserInterface)UserInterface.getInstance()).choose(3);
        window.close();
    }
}
