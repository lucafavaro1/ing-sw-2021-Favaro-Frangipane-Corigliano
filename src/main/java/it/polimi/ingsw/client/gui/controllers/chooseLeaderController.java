package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.client.gui.GUIUserInterface;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class chooseLeaderController extends Controller{
    @FXML
    public ImageView leadercard1;
    public ImageView leadercard2;
    public ImageView leadercard3;
    public ImageView leadercard4;

    public void leader1chosen(MouseEvent mouseEvent) {
        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        ((GUIUserInterface)UserInterface.getInstance()).choose(1);
        window.close();
    }

    public void leader2chosen(MouseEvent mouseEvent) {
        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        ((GUIUserInterface)UserInterface.getInstance()).choose(2);
        window.close();
    }

    public void leader3chosen(MouseEvent mouseEvent) {
        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        ((GUIUserInterface)UserInterface.getInstance()).choose(3);
        window.close();
    }

    public void leader4chosen(MouseEvent mouseEvent) {
        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        ((GUIUserInterface)UserInterface.getInstance()).choose(4);
        window.close();
    }
}
