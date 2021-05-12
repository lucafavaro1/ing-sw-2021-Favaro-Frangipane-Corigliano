package it.polimi.ingsw.client.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class leaderCardController extends Controller {
    @FXML
    public ImageView leadercard1;
    public ImageView leadercard2;
    public ProgressBar leader1activate; // si usa .setProgress(1) per dire che è attiva
    public ProgressBar leader2activate; // si usa .setProgress(1) per dire che è attiva

    public void toPersonalBoard(MouseEvent mouseEvent) {
        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Scene x = getPersonalpunchboard();
        window.setScene(x);
        window.show();
    }
}