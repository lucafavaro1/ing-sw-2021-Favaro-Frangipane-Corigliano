package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.client.gui.GUIUserInterface;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * GUI Controller: choosing 2 of 4 leader cards at the beginning of the game
 */

public class ChooseLeaderController extends Controller{
    @FXML
    public ImageView leadercard1;
    public ImageView leadercard2;
    public ImageView leadercard3;
    public ImageView leadercard4;

    /**
     * Method to choose the first card
     * @param mouseEvent click on first card on the left
     */
    public void leader1chosen(MouseEvent mouseEvent) {
        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        ((GUIUserInterface)UserInterface.getInstance()).choose(1);
        window.close();
    }

    /**
     * Method to choose the second card
     * @param mouseEvent click on the second card starting from left
     */
    public void leader2chosen(MouseEvent mouseEvent) {
        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        ((GUIUserInterface)UserInterface.getInstance()).choose(2);
        window.close();
    }

    /**
     * Method to choose the third card
     * @param mouseEvent click on the third card start from left
     */
    public void leader3chosen(MouseEvent mouseEvent) {
        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        ((GUIUserInterface)UserInterface.getInstance()).choose(3);
        window.close();
    }

    /**
     * Method to choose the fourth card
     * @param mouseEvent click on the fourth card starting from left
     */
    public void leader4chosen(MouseEvent mouseEvent) {
        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        ImageView l4 = (ImageView) window.getScene().lookup("#leadercard4");
        if(l4.getImage()!=null) {
            ((GUIUserInterface) UserInterface.getInstance()).choose(4);
            window.close();
        }
    }
}
