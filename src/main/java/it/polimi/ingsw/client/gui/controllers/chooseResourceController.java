package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.client.gui.GUIUserInterface;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class chooseResourceController extends Controller {
    public void coinchosen(MouseEvent mouseEvent) {
        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        ((GUIUserInterface) UserInterface.getInstance()).choose(1);
        window.close();
    }

    public void stonechosen(MouseEvent mouseEvent) {
        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        ((GUIUserInterface)UserInterface.getInstance()).choose(2);
        window.close();
    }

    public void shieldchosen(MouseEvent mouseEvent) {
        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        ((GUIUserInterface)UserInterface.getInstance()).choose(4);
        window.close();
    }

    public void servantchosen(MouseEvent mouseEvent) {
        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        ((GUIUserInterface)UserInterface.getInstance()).choose(3);
        window.close();
    }
}
