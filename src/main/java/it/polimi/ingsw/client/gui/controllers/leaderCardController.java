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

    private static leaderCardController instance;

    public static leaderCardController getInstance() {
        if(instance == null)
            instance = new leaderCardController();
        return instance;
    }

    public void toPersonalBoard(MouseEvent mouseEvent) {
        getPrimarystage().setScene(getPersonalpunchboard());
        getPrimarystage().show();
    }
}
